package isensehostility.improved_slimes.event;

import isensehostility.improved_slimes.ImprovedSlimes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
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
    public static void onEntitySpawn(EntityJoinWorldEvent event) {
        World level = event.getWorld();

        if (!level.isClientSide) {
            Entity entity = event.getEntity();

            if (entity instanceof SlimeEntity slime) {
                if (slime.getSize() >= 3) {
                    ImprovedSlimes.setMaxHealth(slime, (slime.getMaxHealth() * HEALTH_BOOST_LARGE_SLIMES));
                } else {
                    ImprovedSlimes.setMaxHealth(slime, (slime.getMaxHealth() * HEALTH_BOOST_SMALL_SLIMES));
                }
                if (slime.getRandom().nextInt(100) < RARE_SLIME_CHANCE) {
                    slime.setSize(RARE_SLIME_SIZE, false);

                    slime.setCustomName(new TranslationTextComponent("improved_slimes.rare_slime.name"));

                    ImprovedSlimes.setMaxHealth(slime, (slime.getMaxHealth() * HEALTH_BOOST_RARE_SLIMES));
                }

                slime.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(slime.getAttributeValue(Attributes.ATTACK_DAMAGE) * ATTACK_DAMAGE_INCREASE);
            }

        }
    }

    @SubscribeEvent
    public static void onEntityDamaged(LivingDamageEvent event) {
        LivingEntity entity = event.getEntityLiving();
        World level = event.getEntity().level;

        if (!level.isClientSide) {

            if (entity instanceof SlimeEntity) {
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
        LivingEntity entity = event.getEntityLiving();
        World level = event.getEntity().level;

        if (!level.isClientSide) {

            if (entity instanceof SlimeEntity slime) {
                if (slime.getLastDamageSource() != DamageSource.MAGIC) {
                    event.setStrength(event.getOriginalStrength() / KNOCKBACK_RESISTANCE);
                }
            }

        }
    }
}
