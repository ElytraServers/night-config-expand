package cn.elytra.nightconfig.internal;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.InMemoryCommentedFormat;
import com.electronwill.nightconfig.core.UnmodifiableCommentedConfig;
import com.electronwill.nightconfig.core.concurrent.ConcurrentCommentedConfig;
import com.electronwill.nightconfig.core.concurrent.SynchronizedConfig;
import com.electronwill.nightconfig.core.file.FileWatcher;
import com.electronwill.nightconfig.core.io.ParsingException;
import com.electronwill.nightconfig.core.io.ParsingMode;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.electronwill.nightconfig.toml.TomlFormat;
import com.electronwill.nightconfig.toml.TomlParser;
import com.electronwill.nightconfig.toml.TomlWriter;
import net.neoforged.fml.config.IConfigSpec;
import net.neoforged.fml.config.LoadedConfig;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.LinkedHashMap;

@ApiStatus.Internal
public final class ConfigurationImpl {
    private static final Logger log = LoggerFactory.getLogger(ConfigurationImpl.class);

    private ConfigurationImpl() {
    }

    public static void watch(Path path, Runnable callback) {
        FileWatcher.defaultInstance().addWatch(path, () -> {
            log.debug("Config file {} changed, re-loading;", path);
            callback.run();
        });
    }

    public static void unwatch(@Nullable Path path) {
        if (path == null) return;
        log.trace("Closing and unloading config file {}", path);
        try {
            FileWatcher.defaultInstance().removeWatch(path);
        } catch (RuntimeException e) {
            log.error("Failed to remove config {} from tracker!", path, e);
        }
    }

    public static CommentedConfig loadConfig(IConfigSpec spec, Path path) {
        CommentedConfig config;

        try {
            config = readConfig(path);

            if (!spec.isCorrect(config)) {
                log.warn("Configuration file {} is not correct. Correcting", path);
                backUpConfig(path);
                spec.correct(config);
                writeConfig(path, config);
            }
        } catch (NoSuchFileException ignored) {
            try {
                setupConfigFile(spec, path);
                config = readConfig(path);
            } catch (IOException | ParsingException e) {
                throw new RuntimeException("Failed to create default config file " + path.getFileName(), e);
            }
        } catch (IOException | ParsingException e) {
            log.warn("Failed to load config {}: {}. Attempting to recreate", path, e.toString());
            try {
                backUpConfig(path);
                Files.delete(path);

                setupConfigFile(spec, path);
                config = readConfig(path);
            } catch (Throwable t) {
                e.addSuppressed(t);
                throw new RuntimeException("Failed to recreate config file " + path, e);
            }
        }

        spec.acceptConfig(new LoadedConfig(config, path));
        return config;
    }

    private static ConcurrentCommentedConfig readConfig(Path path) throws IOException, ParsingException {
        try (java.io.BufferedReader reader = Files.newBufferedReader(path)) {
            SynchronizedConfig config = new SynchronizedConfig(TomlFormat.instance(), LinkedHashMap::new);
            config.bulkCommentedUpdate(view -> {
                new TomlParser().parse(reader, view, ParsingMode.REPLACE);
            });
            return config;
        }
    }

    public static void writeConfig(Path file, UnmodifiableCommentedConfig config) {
        new TomlWriter().write(config, file, WritingMode.REPLACE_ATOMIC);
    }

    private static void setupConfigFile(IConfigSpec spec, Path file) throws IOException {
        Files.createDirectories(file.getParent());
        writeConfig(file, createDefaultConfig(spec));
    }

    private static CommentedConfig createDefaultConfig(IConfigSpec spec) {
        SynchronizedConfig config = new SynchronizedConfig(InMemoryCommentedFormat.defaultInstance(), LinkedHashMap::new);
        config.bulkCommentedUpdate(spec::correct);
        return config;
    }

    private static void backUpConfig(Path path) {
        Path bakFileLocation = path.getParent();
        String[] s = Utils.getFileNameAndExtension(path);
        String bakFileName = s[0];
        String bakFileExtension = s[1] + ".bak";
        Path bakFile = bakFileLocation.resolve(bakFileName + "-1" + "." + bakFileExtension);
        int maxBackups = 5; // const
        try {
            for (int i = maxBackups; i > 0; i--) {
                Path oldBak = bakFileLocation.resolve(bakFileName + "-" + i + "." + bakFileExtension);
                if (Files.exists(oldBak)) {
                    if (i >= maxBackups)
                        Files.delete(oldBak);
                    else
                        Files.move(oldBak, bakFileLocation.resolve(bakFileName + "-" + (i + 1) + "." + bakFileExtension));
                }
            }
            Files.copy(path, bakFile);
        } catch (IOException exception) {
            log.warn("Failed to back up config file {}", path, exception);
        }
    }

}
