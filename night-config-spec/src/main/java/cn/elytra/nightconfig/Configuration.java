package cn.elytra.nightconfig;

import cn.elytra.nightconfig.internal.ConfigurationImpl;
import com.electronwill.nightconfig.core.CommentedConfig;
import net.neoforged.fml.config.IConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.nio.file.Path;
import java.util.Objects;

/// Configuration APIs
///
/// - [#openConfig(IConfigSpec, Path)]: load and watch a configuration, the value is updated real-time with the file.
/// - [#loadConfig(IConfigSpec, Path)]: load a configuration.
/// - [#unloadConfig(IConfigSpec)]: unload a configuration.
public final class Configuration {

    public static void openConfig(IConfigSpec spec, Path path) {
        loadConfig(spec, path);
        ConfigurationImpl.watch(path, () -> loadConfig(spec, path));
    }

    public static void unloadConfig(IConfigSpec spec) {
        if (spec instanceof ModConfigSpec) {
            ModConfigSpec modSpec = (ModConfigSpec) spec;
            IConfigSpec.ILoadedConfig loaded = modSpec.getLoadedConfig();
            if (loaded != null) {
                ConfigurationImpl.unwatch(loaded.path());
                modSpec.setLoadedConfig(null);
            }
        }
    }

    public static CommentedConfig loadConfig(IConfigSpec spec, Path path) {
        Objects.requireNonNull(spec, "Spec must not be null");
        Objects.requireNonNull(path, "Path must not be null");
        return ConfigurationImpl.loadConfig(spec, path.toAbsolutePath());
    }

}
