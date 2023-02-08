package isensehostility.improved_slimes;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraftforge.fml.common.Mod;

@Mod(ImprovedSlimes.MODID)
public class ImprovedSlimes {

    public static final String MODID = "improved_slimes";

    public ImprovedSlimes() {

    }

    public static float setMaxHealth(LivingEntity entity, float health) {
        entity.getAttribute(Attributes.MAX_HEALTH).setBaseValue(health);

        float maxHealth = entity.getMaxHealth();
        entity.heal(maxHealth);
        return maxHealth;
    }
}
