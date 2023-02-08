package isensehostility.improved_slimes.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;

import java.io.File;

public class ISConfig {
    private static ForgeConfigSpec.DoubleValue DAMAGE_RESISTANCE;
    private static ForgeConfigSpec.DoubleValue KNOCKBACK_RESISTANCE;
    private static ForgeConfigSpec.IntValue RARE_SLIME_SIZE;
    private static ForgeConfigSpec.DoubleValue RARE_SLIME_CHANCE;
    private static ForgeConfigSpec.DoubleValue HEALTH_BOOST_LARGE_SLIMES;
    private static ForgeConfigSpec.DoubleValue HEALTH_BOOST_SMALL_SLIMES;
    private static ForgeConfigSpec.DoubleValue HEALTH_BOOST_RARE_SLIMES;
    private static ForgeConfigSpec.DoubleValue ATTACK_DAMAGE_INCREASE;
    private static ForgeConfigSpec.DoubleValue WEAKNESS_DAMAGE_INCREASE;

    private static final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec config;

    static {
        initialize(builder);
        config = builder.build();
    }

    public static void loadConfig(ForgeConfigSpec config, String path) {
        final CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).sync().autosave().writingMode(WritingMode.REPLACE).build();
        file.load();
        config.setConfig(file);
    }

    public static void initialize(ForgeConfigSpec.Builder config) {
        DAMAGE_RESISTANCE = config
                .comment("""
                        Amount the damage of physical attacks to slimes is divided by.
                        """)
                .defineInRange("resistance.damage", 1.5D, 0.0D, Double.MAX_VALUE);
        KNOCKBACK_RESISTANCE = config
                .comment("""
                        Amount the knockback of any attacks to slimes is divided by.
                        """)
                .defineInRange("resistance.knockback", 2.0D, 0.0D, Double.MAX_VALUE);

        HEALTH_BOOST_SMALL_SLIMES = config
                .comment("""
                        Amount the health of small slimes is multiplied by.
                        """)
                .defineInRange("health.small_slimes", 2.0D, 0.0D, Double.MAX_VALUE);
        HEALTH_BOOST_LARGE_SLIMES = config
                .comment("""
                        Amount the health of large slimes is multiplied by.
                        """)
                .defineInRange("health.small_slimes", 1.5D, 0.0D, Double.MAX_VALUE);
        HEALTH_BOOST_RARE_SLIMES = config
                .comment("""
                        Amount the health of rare slimes is multiplied by.
                        """)
                .defineInRange("health.rare_slimes", 1.25D, 0.0D, Double.MAX_VALUE);

        RARE_SLIME_SIZE = config
                .comment("""
                        Size of rare slimes.
                        """)
                .defineInRange("rare_slime.size", 6, 0, Integer.MAX_VALUE);
        RARE_SLIME_CHANCE = config
                .comment("""
                        Chance of a rare slime spawning instead of a normal slime.
                        """)
                .defineInRange("rare_slime.chance", 0.5D, 0.0D, Double.MAX_VALUE);

        ATTACK_DAMAGE_INCREASE = config
                .comment("""
                        Amount the attack damage of slimes is multiplied by.
                        """)
                .defineInRange("boost.strength", 1.35D, 0.0D, Double.MAX_VALUE);
        WEAKNESS_DAMAGE_INCREASE = config
                .comment("""
                        Amount the damage taken by weaknesses of slimes is multiplied by.
                        """)
                .defineInRange("boost.weakness", 1.3D, 0.0D, Double.MAX_VALUE);
    }

    public static float getDamageResistance() {
        return DAMAGE_RESISTANCE.get().floatValue();
    }

    public static float getKnockbackResistance() {
        return KNOCKBACK_RESISTANCE.get().floatValue();
    }

    public static int getRareSlimeSize() {
        return RARE_SLIME_SIZE.get();
    }

    public static float getRareSlimeChance() {
        return RARE_SLIME_CHANCE.get().floatValue();
    }

    public static float getHealthBoostLargeSlimes() {
        return HEALTH_BOOST_LARGE_SLIMES.get().floatValue();
    }

    public static float getHealthBoostSmallSlimes() {
        return HEALTH_BOOST_SMALL_SLIMES.get().floatValue();
    }

    public static float getHealthBoostRareSlimes() {
        return HEALTH_BOOST_RARE_SLIMES.get().floatValue();
    }

    public static float getAttackDamageIncrease() {
        return ATTACK_DAMAGE_INCREASE.get().floatValue();
    }

    public static float getWeaknessDamageIncrease() {
        return WEAKNESS_DAMAGE_INCREASE.get().floatValue();
    }
}
