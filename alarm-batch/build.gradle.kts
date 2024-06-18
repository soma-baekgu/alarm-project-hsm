dependencies {
    implementation(project(":alarm-core"))
    implementation("org.springframework.boot:spring-boot-starter-batch")
}

tasks.getByName<Jar>("bootJar") {
    enabled = false
}

tasks.getByName<Jar>("jar") {
    enabled = true
}
