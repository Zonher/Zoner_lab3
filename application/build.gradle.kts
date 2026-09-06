plugins {
    id("java")
    id("org.springframework.boot") version "3.3.5"
    id("io.spring.dependency-management") version "1.1.6"
 }

dependencies {
    implementation(project(":common"))
    implementation(project(":service"))
    implementation(project(":dataaccess"))
    implementation("com.google.code.gson:gson:2.11.0")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")
}

tasks.test{
    useJUnitPlatform()
}
