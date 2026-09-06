dependencies {
    implementation(project(":common"))
    implementation("org.hibernate.orm:hibernate-envers:6.5.3.Final")
    api("org.springframework.boot:spring-boot-starter-data-jpa:3.3.5")
    runtimeOnly("org.postgresql:postgresql:42.7.13")
}