plugins {
}

dependencies {
    runtimeOnly("com.mysql:mysql-connector-j")
}

tasks.getByName<Jar>("bootJar") {
    enabled = false
}

tasks.getByName<Jar>("jar") {
    enabled = true
}
