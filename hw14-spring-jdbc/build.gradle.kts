plugins {
    id("java")
}

group = "ru.otus"

repositories {
    mavenCentral()
}

dependencies {

       implementation("org.projectlombok:lombok")
       annotationProcessor("org.projectlombok:lombok")


       implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
       implementation("org.postgresql:postgresql")
       implementation("org.flywaydb:flyway-core")
       runtimeOnly("org.flywaydb:flyway-database-postgresql")


       implementation("org.springframework.boot:spring-boot-starter-web")
       implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
       implementation("org.freemarker:freemarker")


       implementation("ch.qos.logback:logback-classic")


       implementation("org.hibernate.orm:hibernate-core")
}

tasks.test {
    useJUnitPlatform()
}