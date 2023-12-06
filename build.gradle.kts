plugins {
    kotlin("jvm") version "1.9.21"
    application
}

group = "com.vlmaier"
version = "1.0.0"

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(17)
}
