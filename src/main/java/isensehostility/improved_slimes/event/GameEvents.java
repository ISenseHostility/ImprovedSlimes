package isensehostility.improved_slimes.event;

import isensehostility.improved_slimes.ImprovedSlimes;
import isensehostility.improved_slimes.config.ISConfig;
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

    @SubscribeEvent
    public static void onEntitySpawn(EntityJoinWorldEvent event) {
        World level = event.getWorld();

        if (!level.isClientSide) {
            Entity entity = event.getEntity();

            if (entity instanceof SlimeEntity slime) {
                if (slime.getSize() >= 3) {
                    ImprovedSlimes.setMaxHealth(slime, (slime.getMaxHealth() * ISConfig.getHealthBoostLargeSlimes()));
                } else {
                    ImprovedSlimes.setMaxHealth(slime, (slime.getMaxHealth() * ISConfig.getHealthBoostSmallSlimes()));
                }
                if (slime.getRandom().nextInt(100) < ISConfig.getRareSlimeChance()) {
                    slime.setSize(ISConfig.getRareSlimeSize(), false);

                    slime.setCustomName(new TranslationTextComponent("improved_slimes.rare_slime.name"));

                    ImprovedSlimes.setMaxHealth(slime, (slime.getMaxHealth() * ISConfig.getHealthBoostRareSlimes()));
                }

                slime.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(slime.getAttributeValue(Attributes.ATTACK_DAMAGE) * ISConfig.getAttackDamageIncrease());
            }

        }
    }

    @SubscribeEvent
    public static void onEntityDamaged(LivingDamageEvent event) {
        LivingEntity entity = event.getEntityLiving();
        World level = event.getEntity().level;

        if (!level.isClientSide) {

            if (entity instanceof SlimeEntity) {
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
        World level = event.getEntity().level;

        if (!level.isClientSide) {

            if (entity instanceof SlimeEntity slime) {
                if (!ImprovedSlimes.isSourceWeakness(slime.getLastDamageSource())) {
                    event.setStrength(event.getOriginalStrength() / ISConfig.getKnockbackResistance());
                }
            }

        }
    }
}
