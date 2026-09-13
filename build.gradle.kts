plugins {
    alias(libs.plugins.gitVersion)
}

@Suppress("UNCHECKED_CAST")
val gitVersion = extra["gitVersion"] as groovy.lang.Closure<String>
val theVersion =
    runCatching {
        gitVersion()
    }.getOrElse {
        println("Failed to get the Git version: ${it.message}")
        "99.99.99"
    }

allprojects {
    group = "cn.elytra.nightconfig"
    version = theVersion
}

repositories {
    mavenCentral()
}
