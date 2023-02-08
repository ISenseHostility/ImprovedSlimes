package isensehostility.improved_slimes;

import isensehostility.improved_slimes.config.ISConfig;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod(ImprovedSlimes.MODID)
public class ImprovedSlimes {

    public static final String MODID = "improved_slimes";

    public ImprovedSlimes() {
        ISConfig.loadConfig(ISConfig.config, FMLPaths.CONFIGDIR.get().resolve(MODID + "-config.toml").toString());
    }

    public static float setMaxHealth(LivingEntity entity, float health) {
        entity.getAttribute(Attributes.MAX_HEALTH).setBaseValue(health);

        float maxHealth = entity.getMaxHealth();
        entity.heal(maxHealth);
        return maxHealth;
    }

    public static boolean isSourceWeakness(DamageSource source) {
        return source == DamageSource.MAGIC || source == DamageSource.ON_FIRE || source == DamageSource.LAVA || source == DamageSource.IN_FIRE;
    }
}
