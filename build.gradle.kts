extra["javaJwtVersion"] = "4.4.0"
extra["javaDotenvVersion"] = "3.1.0"
extra["datafakerVersion"] = "2.4.2"
extra["flywayCoreVersion"] = "11.1.0"
extra["springdocVersion"] = "2.7.0"

plugins {
    java
    id("org.springframework.boot") version "3.4.1"
    id("io.spring.dependency-management") version "1.1.6"
    id("org.flywaydb.flyway") version "11.1.0"
}

group = "codesumn.com"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("com.auth0:java-jwt:${property("javaJwtVersion")}")
    implementation("io.github.cdimascio:dotenv-java:${property("javaDotenvVersion")}")
    implementation("net.datafaker:datafaker:${property("datafakerVersion")}")
    implementation("org.flywaydb:flyway-core:${property("flywayCoreVersion")}")
    implementation("org.flywaydb:flyway-database-postgresql:${property("flywayCoreVersion")}")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${property("springdocVersion")}")

    runtimeOnly("org.postgresql:postgresql")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
