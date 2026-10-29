import cn.elytra.nightconfig.Configuration;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.nio.file.Paths;

public class Test2 {
    public static final int FLAG_WORLD = 1; // 0b01
    public static final int FLAG_SERVER = 2; // 0b10

    public static void main(String[] args) throws InterruptedException {
        ModConfigSpec.Builder builder = ModConfigSpec.builder();
        ModConfigSpec.ConfigValue<Integer> s1 = builder.withUpdateFlags(FLAG_WORLD).define("s1", 1);
        ModConfigSpec.ConfigValue<Integer> s2 = builder.withUpdateFlags(FLAG_SERVER).define("s2", 1);
        ModConfigSpec.ConfigValue<Integer> s3 = builder.withUpdateFlags(FLAG_WORLD | FLAG_SERVER).define("s3", 1);
        ModConfigSpec.BooleanValue f1 = builder.define("f1", false);
        ModConfigSpec.BooleanValue f2 = builder.define("f2", false);
        ModConfigSpec spec = builder.build();

        Configuration.openConfig(spec, Paths.get("config", "2.toml"));

        boolean lastF1 = f1.getAsBoolean();
        boolean lastF2 = f2.getAsBoolean();
        int count = 0;
        while (true) {
            if (lastF1 != f1.getAsBoolean()) {
                lastF1 = f1.getAsBoolean();
                spec.resetCaches(FLAG_WORLD);
                System.out.println("FLAG_WORLD");
            }
            if (lastF2 != f2.getAsBoolean()) {
                lastF2 = f2.getAsBoolean();
                spec.resetCaches(FLAG_SERVER);
                System.out.println("FLAG_SERVER");
            }

            System.out.println((count++) + " | S1: " + s1.get() + " S2: " + s2.get() + " S3: " + s3.get());

            Thread.sleep(1000);
        }
    }

}
