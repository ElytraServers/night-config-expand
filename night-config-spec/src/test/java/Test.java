import net.neoforged.fml.config.ConfigTracker;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.List;

public class Test {

    private static final Logger log = LoggerFactory.getLogger(Test.class);

    static void main() {
        var b = new ModConfigSpec.Builder();
        ModConfigSpec.BooleanValue foo = b
                .comment("The FOO flag")
                .translation("config.foo.key")
                .define("foo", true);
        ModConfigSpec.ConfigValue<Integer> bar = b
                .comment("The BAR magic code")
                .define("bar", 42);
        ModConfigSpec.ConfigValue<List<? extends String>> baz = b
                .comment("The BAZ list of String")
                .defineList("baz", List.of("Hello", "Bonjour"), String::new, String.class::isInstance);
        ModConfigSpec spec = b.build();

        ConfigTracker.INSTANCE.registerConfig(ModConfig.Type.COMMON, spec, "1");

        FMLPaths.loadAbsolutePaths(Path.of("."));
        ConfigTracker.INSTANCE.loadConfigs(ModConfig.Type.COMMON, FMLPaths.CONFIGDIR.get());

        log.info("FOO {} BAR {} BAZ {}", foo.get(), bar.get(), String.join(",", baz.get()));
    }

}
