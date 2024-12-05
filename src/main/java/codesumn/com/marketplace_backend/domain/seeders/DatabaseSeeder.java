package codesumn.com.marketplace_backend.domain.seeders;

import codesumn.com.marketplace_backend.domain.models.ProductModel;
import codesumn.com.marketplace_backend.domain.models.UserModel;
import codesumn.com.marketplace_backend.application.config.EnvironConfig;
import codesumn.com.marketplace_backend.infrastructure.adapters.persistence.repository.ProductRepository;
import codesumn.com.marketplace_backend.infrastructure.adapters.persistence.repository.UserRepository;
import net.datafaker.Faker;
import net.datafaker.providers.base.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static net.datafaker.providers.base.Text.DIGITS;
import static net.datafaker.providers.base.Text.EN_UPPERCASE;

@Component
public class DatabaseSeeder {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseSeeder.class);
    private final JdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final Faker faker;
    private final Boolean enableSeeder;

    @Autowired
    public DatabaseSeeder(
            JdbcTemplate jdbcTemplate,
            UserRepository userRepository,
            ProductRepository productRepository
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.faker = new Faker();
        this.enableSeeder = Boolean.valueOf(EnvironConfig.SEED_DB);
    }

    @EventListener
    public void seed(ContextRefreshedEvent ignoredEvent) {
        seedUsersTable();
        seedProductsTable();
    }

    private void seedUsersTable() {
        String sql = "SELECT COUNT(*) FROM tb_users";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        if ((count == null || count == 0) && enableSeeder) {
            Set<String> emails = new HashSet<>();
            while (emails.size() < 100) {
                emails.add(faker.internet().emailAddress());
            }
            for (String email : emails) {
                UserModel user = new UserModel();
                user.setFirstName(faker.name().firstName());
                user.setLastName(faker.name().lastName());
                user.setEmail(email);
                user.setPassword(new BCryptPasswordEncoder().encode(
                        faker.text().text(Text.TextSymbolsBuilder.builder()
                                .len(100)
                                .with(EN_UPPERCASE, 2)
                                .with(DIGITS, 3)
                                .build()
                        )
                ));
                user.setRole(faker.options().option("USER", "ADMIN"));
                userRepository.save(user);
            }
            logger.info("100 Users Seeded");
        } else {
            logger.info("Users Seeding Not Required");
        }
    }

    private void seedProductsTable() {
        String sql = "SELECT COUNT(*) FROM tb_products";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        if ((count == null || count == 0) && enableSeeder) {
            Set<String> productNames = new HashSet<>();
            while (productNames.size() < 100) {
                productNames.add(faker.commerce().productName());
            }
            for (String productName : productNames) {
                ProductModel product = new ProductModel();
                product.setName(productName);
                product.setDescription(faker.lorem().sentence());
                product.setPrice(BigDecimal.valueOf(faker.number()
                        .randomDouble(2, 10, 1000)));
                product.setStock(faker.number().numberBetween(1, 100));
                product.setCategory(faker.commerce().department());
                productRepository.save(product);
            }
            logger.info("100 Products Seeded");
        } else {
            logger.info("Products Seeding Not Required");
        }
    }
}

