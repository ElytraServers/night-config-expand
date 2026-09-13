import cn.elytra.nightconfig.Configuration;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.List;

public class TestSimple {

    private static final Logger log = LoggerFactory.getLogger(TestSimple.class);

    static void main() throws InterruptedException {
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

        Configuration.openConfig(spec, Path.of("config", "1.toml"));

        log.info("FOO {} BAR {} BAZ {}", foo.get(), bar.get(), String.join(",", baz.get()));

        while (true) {
            Thread.sleep(1000);
            log.info("BAR now: {}", bar.get());
            if (bar.get() == 1024) {
                break;
            }
        }

        Configuration.unloadConfig(spec);
    }

}
