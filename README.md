# NightConfig Expand

See also [night-config/README.md](https://github.com/TheElectronWill/night-config).

## Usage

```kotlin
repositories {
    maven("https://jitpack.io")
}

dependencies {
    implementation("com.github.ElytraServers.night-config-expand:night-config-spec:${VERSION}")
    implementation("com.github.ElytraServers.night-config-expand:night-config-kotlin:${VERSION}")
}
```

## Spec Module

Spec module is ripped from NeoForge under LGPLv2.1 ([NeoForge/LICENSE.txt][nf-license]
and [FancyModLoader/LICENSE.txt][fml-license]).

It provides a convenient ModConfigSpec, where you can define the properties and ask the library to help you with
converting.
The NeoForge-specific code is removed for generic purpose.

See the example [TestSimple.java](/night-config-spec/src/test/java/TestSimple.java).

[nf-license]: https://github.com/neoforged/NeoForge/blob/1ad7d233fc1ff8c3cb5c8b159f1701aabf4b7b96/LICENSE.txt
[fml-license]: https://github.com/neoforged/FancyModLoader/blob/666fa649f1cb8edd7d21665f60423ecd8081620c/LICENSE.txt
