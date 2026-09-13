import cn.elytra.nightconfig.Configuration;
import com.electronwill.nightconfig.core.EnumGetMethod;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.time.format.TextStyle;
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
        b.comment("This is a NESTED section?!").push("nest");
        ModConfigSpec.EnumValue<TextStyle> nestedEnum = b
                .comment("Some random enum?")
                .defineEnum("enum", TextStyle.FULL, EnumGetMethod.ORDINAL_OR_NAME);
        ModConfigSpec.IntValue nestedInt = b
                .comment("Yet another Integer")
                .defineInRange("int", 1, -1, 114514);
        b.pop();
        ModConfigSpec spec = b.build();

        Configuration.openConfig(spec, Path.of("config", "1.toml"));

        log.info("FOO {} BAR {} BAZ {} N_ENUM {} N_INT {}", foo.get(), bar.get(), String.join(",", baz.get()), nestedEnum.get().name(), nestedInt.get());

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
