plugins {
    java
    `maven-publish`
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

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "cn.elytra.nightconfig"
            artifactId = "night-config-spec"
            version = project.version.toString()
            from(components["java"])
        }
    }
}
