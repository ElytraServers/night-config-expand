/*
 * Copyright (c) NeoForged and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package net.neoforged.fml.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

class ConfigWatcher implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigWatcher.class);

    private final ModConfig modConfig;
    private final Path path;
    private final ClassLoader realClassLoader;

    ConfigWatcher(ModConfig modConfig, Path path, ClassLoader classLoader) {
        this.modConfig = modConfig;
        this.path = path;
        this.realClassLoader = classLoader;
    }

    @Override
    public void run() {
        // Force the regular classloader onto the special thread
        var previousLoader = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(realClassLoader);
        try {
            modConfig.lock.lock();
            try {
                LOGGER.debug(ConfigTracker.CONFIG, "Config file {} changed, re-loading", modConfig.getFileName());
                ConfigTracker.loadConfig(this.modConfig, this.path);
            } finally {
                modConfig.lock.unlock();
            }
        } finally {
            Thread.currentThread().setContextClassLoader(previousLoader);
        }
    }
}