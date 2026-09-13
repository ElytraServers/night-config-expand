package net.neoforged.fml.config;

import cn.elytra.nightconfig.Configuration;
import com.electronwill.nightconfig.core.CommentedConfig;

import java.nio.file.Path;

public record LoadedConfig(CommentedConfig config, Path path) implements IConfigSpec.ILoadedConfig {
    @Override
    public void save() {
        Configuration.writeConfig(path, config);
    }
}
