package isensehostility.improved_slimes.event;

import isensehostility.improved_slimes.ImprovedSlimes;
import isensehostility.improved_slimes.config.ISConfig;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ImprovedSlimes.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GameEvents {

    @SubscribeEvent
    public static void onEntitySpawn(EntityJoinWorldEvent event) {
        Level level = event.getWorld();

        if (!level.isClientSide) {
            Entity entity = event.getEntity();

            if (entity instanceof Slime slime) {
                if (slime.getSize() >= 3) {
                    ImprovedSlimes.setMaxHealth(slime, (slime.getMaxHealth() * ISConfig.getHealthBoostLargeSlimes()));
                } else {
                    ImprovedSlimes.setMaxHealth(slime, (slime.getMaxHealth() * ISConfig.getHealthBoostSmallSlimes()));
                }
                if (slime.getRandom().nextInt(100) < ISConfig.getRareSlimeChance()) {
                    slime.setSize(ISConfig.getRareSlimeSize(), false);

                    slime.setCustomName(new TranslatableComponent("improved_slimes.rare_slime.name"));

                    ImprovedSlimes.setMaxHealth(slime, (slime.getMaxHealth() * ISConfig.getHealthBoostRareSlimes()));
                }

                slime.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(slime.getAttributeValue(Attributes.ATTACK_DAMAGE) * ISConfig.getAttackDamageIncrease());
            }

        }
    }

    @SubscribeEvent
    public static void onEntityDamaged(LivingDamageEvent event) {
        LivingEntity entity = event.getEntityLiving();
        Level level = event.getEntity().getLevel();

        if (!level.isClientSide) {

            if (entity instanceof Slime) {
                if (ImprovedSlimes.isSourceWeakness(event.getSource())) {
                    event.setAmount(event.getAmount() * ISConfig.getWeaknessDamageIncrease());
                } else {
                    event.setAmount(event.getAmount() / ISConfig.getDamageResistance());
                }
            }

        }
    }

    @SubscribeEvent
    public static void onEntityKnocked(LivingKnockBackEvent event) {
        LivingEntity entity = event.getEntityLiving();
        Level level = event.getEntity().getLevel();

        if (!level.isClientSide) {

            if (entity instanceof Slime slime) {
                if (!ImprovedSlimes.isSourceWeakness(slime.getLastDamageSource())) {
                    event.setStrength(event.getOriginalStrength() / ISConfig.getKnockbackResistance());
                }
            }

        }
    }
}
