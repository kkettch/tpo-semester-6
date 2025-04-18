plugins {
    kotlin("jvm") version "2.0.21"
}

kotlin {
    jvmToolchain(22)
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.seleniumhq.selenium:selenium-java:4.31.0")
    implementation("org.slf4j:slf4j-simple:2.0.9")
    implementation("org.seleniumhq.selenium:selenium-chrome-driver:4.31.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
}

tasks.test {
    useJUnitPlatform()
}