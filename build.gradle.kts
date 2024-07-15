plugins {
    java
    id("org.springframework.boot") version "3.3.1"
    id("io.spring.dependency-management") version "1.1.5"
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
    google()
    maven { url = uri("https://jitpack.io") }
}

extra["javaJwtVersion"] = "4.4.0"
extra["javaDotenvVersion"] = "5.2.2"
extra["datafakerVersion"] = "2.3.0"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    runtimeOnly("org.postgresql:postgresql")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("com.auth0:java-jwt:${property("javaJwtVersion")}")
    implementation("io.github.cdimascio:java-dotenv:${property("javaDotenvVersion")}")
    implementation("net.datafaker:datafaker:${property("datafakerVersion")}")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
