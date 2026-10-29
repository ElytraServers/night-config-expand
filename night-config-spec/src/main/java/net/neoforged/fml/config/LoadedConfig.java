package net.neoforged.fml.config;

import cn.elytra.nightconfig.internal.ConfigurationImpl;
import com.electronwill.nightconfig.core.CommentedConfig;

import java.nio.file.Path;

public class LoadedConfig implements IConfigSpec.ILoadedConfig {
    private final CommentedConfig config;
    private final Path path;

    public LoadedConfig(CommentedConfig config, Path path) {
        this.config = config;
        this.path = path;
    }

    @Override
    public CommentedConfig config() {
        return config;
    }

    @Override
    public Path path() {
        return path;
    }

    @Override
    public void save() {
        ConfigurationImpl.writeConfig(path, config);
    }
}
