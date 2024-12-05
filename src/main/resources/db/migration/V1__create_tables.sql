CREATE TABLE tb_users
(
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    role       VARCHAR(50)  NOT NULL,
    CONSTRAINT chk_role CHECK (role IN ('ADMIN', 'USER'))
);

CREATE TABLE tb_products
(
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name        VARCHAR(255)   NOT NULL,
    description TEXT           NOT NULL,
    price       DECIMAL(10, 2) NOT NULL,
    image       VARCHAR(512),
    stock       INT            NOT NULL,
    category    VARCHAR(255)   NOT NULL
);
