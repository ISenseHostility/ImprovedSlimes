package isensehostility.improved_slimes.event;

import isensehostility.improved_slimes.ImprovedSlimes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ImprovedSlimes.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GameEvents {

    private static final float DAMAGE_RESISTANCE = 1.5F;
    private static final float KNOCKBACK_RESISTANCE = 2.0F;
    private static final int RARE_SLIME_SIZE = 6;
    private static final float RARE_SLIME_CHANCE = 0.5F;
    private static final float HEALTH_BOOST_LARGE_SLIMES = 1.5F;
    private static final float HEALTH_BOOST_SMALL_SLIMES = 2.0F;
    private static final float HEALTH_BOOST_RARE_SLIMES = 1.25F;
    private static final float ATTACK_DAMAGE_INCREASE = 1.35F;
    private static final float WEAKNESS_DAMAGE_INCREASE = 1.3F;

    @SubscribeEvent
    public static void onEntitySpawn(EntityJoinLevelEvent event) {
        Level level = event.getLevel();

        if (!level.isClientSide) {
            Entity entity = event.getEntity();

            if (entity instanceof Slime slime) {
                if (slime.getSize() >= 3) {
                    ImprovedSlimes.setMaxHealth(slime, (slime.getMaxHealth() * HEALTH_BOOST_LARGE_SLIMES));
                } else {
                    ImprovedSlimes.setMaxHealth(slime, (slime.getMaxHealth() * HEALTH_BOOST_SMALL_SLIMES));
                }
                if (slime.getRandom().nextInt(100) < RARE_SLIME_CHANCE) {
                    slime.setSize(RARE_SLIME_SIZE, false);

                    slime.setCustomName(Component.translatable("improved_slimes.rare_slime.name"));

                    ImprovedSlimes.setMaxHealth(slime, (slime.getMaxHealth() * HEALTH_BOOST_RARE_SLIMES));
                }

                slime.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(slime.getAttributeValue(Attributes.ATTACK_DAMAGE) * ATTACK_DAMAGE_INCREASE);
            }

        }
    }

    @SubscribeEvent
    public static void onEntityDamaged(LivingDamageEvent event) {
        LivingEntity entity = event.getEntity();
        Level level = event.getEntity().getLevel();

        if (!level.isClientSide) {

            if (entity instanceof Slime) {
                if (event.getSource() != DamageSource.MAGIC || event.getSource() != DamageSource.ON_FIRE || event.getSource() != DamageSource.LAVA || event.getSource() != DamageSource.IN_FIRE) {
                    event.setAmount(event.getAmount() / DAMAGE_RESISTANCE);
                } else {
                    event.setAmount(event.getAmount() * WEAKNESS_DAMAGE_INCREASE);
                }
            }

        }
    }

    @SubscribeEvent
    public static void onEntityKnocked(LivingKnockBackEvent event) {
        LivingEntity entity = event.getEntity();
        Level level = event.getEntity().getLevel();

        if (!level.isClientSide) {

            if (entity instanceof Slime slime) {
                if (slime.getLastDamageSource() != DamageSource.MAGIC) {
                    event.setStrength(event.getOriginalStrength() / KNOCKBACK_RESISTANCE);
                }
            }

        }
    }
}
