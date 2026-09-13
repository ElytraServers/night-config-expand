plugins {
    kotlin("jvm") version "2.4.20"
    `maven-publish`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":night-config-spec"))
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "cn.elytra.nightconfig"
            artifactId = "night-config-kotlin"
            version = project.version.toString()
            from(components["java"])
        }
    }
}
