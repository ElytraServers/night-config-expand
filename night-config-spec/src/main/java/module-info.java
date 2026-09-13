module cn.elytra.nightconfig.spec {
    requires com.electronwill.nightconfig.core;
    requires com.electronwill.nightconfig.toml;
    requires com.google.common;
    requires org.apache.commons.io;
    requires org.apache.commons.lang3;
    requires org.jetbrains.annotations;
    requires org.jspecify;
    requires org.slf4j;

    exports cn.elytra.nightconfig;
    exports net.neoforged.fml.config;
    exports net.neoforged.neoforge.common;
}