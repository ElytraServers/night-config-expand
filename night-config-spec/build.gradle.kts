plugins {
    `java-library`
    `maven-publish`
}

java.toolchain.languageVersion = JavaLanguageVersion.of(8)

repositories {
    mavenCentral()
}

dependencies {
    api(libs.nightconfig.core)
    api(libs.nightconfig.toml)
    implementation(libs.jspecify)
    implementation(libs.slf4j.api)
    testImplementation(libs.slf4j.simple)
    compileOnly(libs.jetbrains.annotations)
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

tasks.test {
    // we don't have unit tests yet,
    // but the example test code will cause error on build task.
    failOnNoDiscoveredTests = false
}

tasks.jar {
    manifest {
        attributes["Automatic-Module-Name"] = "cn.elytra.nightconfig.spec"
    }
}
