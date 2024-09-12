plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("org.postgresql:postgresql:42.7.4")
}


tasks.test {
    useJUnitPlatform()
}