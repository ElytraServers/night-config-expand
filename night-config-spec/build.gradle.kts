plugins {
    java
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.nightconfig.core)
    implementation(libs.nightconfig.toml)
    implementation(libs.jspecify)
    implementation(libs.slf4j.api)
    testImplementation(libs.slf4j.simple)
    implementation(libs.jetbrains.annotations)
    implementation(libs.guava)
    implementation(libs.commons.lang3)
    implementation(libs.commons.io)
}
