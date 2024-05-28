
plugins {
    java
    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management") version "1.1.4"
    id ("java")
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
        constraints {
            implementation("com.fasterxml.jackson.core:jackson-databind:2.15.4")
        }



    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("com.rabbitmq:amqp-client:5.21.0")


     implementation ("com.fasterxml.jackson.core:jackson-databind:2.15.4")
     implementation ("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.15.4")
     implementation ("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.15.4")


    implementation("com.google.code.gson:gson:2.10.1")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("com.mysql:mysql-connector-j")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation ("org.junit.jupiter:junit-jupiter:5.7.2")
    testImplementation("com.h2database:h2")


    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect")
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")



}

tasks.withType<Test> {
    useJUnitPlatform()
}

val integrationTestTask = tasks.register<Test>("integrationTest") {
    group = "verification"
    filter {
        includeTestsMatching("*IntegrationTest")
    }
}

tasks.test{
    filter{
        includeTestsMatching("*Tests")

    }
}

tasks.check {
    dependsOn(integrationTestTask)
}
