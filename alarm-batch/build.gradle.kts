dependencies {
    implementation(project(":alarm-core"))
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.1")
    implementation("org.springframework.kafka:spring-kafka")
    implementation("org.springframework.boot:spring-boot-starter-batch")
    testImplementation("org.springframework.kafka:spring-kafka-test")
}

tasks.getByName<Jar>("bootJar") {
    enabled = false
}

tasks.getByName<Jar>("jar") {
    enabled = true
}
