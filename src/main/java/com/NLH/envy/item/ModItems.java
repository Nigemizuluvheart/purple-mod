package com.NLH.envy.item;

import com.NLH.envy.LeanMod;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ModItems {

    // タイマーを実行するためのサービス
    private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public static final FoodComponent LEAN_FOOD = new FoodComponent.Builder()
            .nutrition(2)
            .saturationModifier(0.2f)
            .alwaysEdible()
            .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 400, 0), 1.0f) // 20秒間吐き気
            .build();

    public static final Item LEAN = registerItem("lean",
            new Item(new Item.Settings().food(LEAN_FOOD).maxCount(64)) {
                @Override
                public UseAction getUseAction(ItemStack stack) {
                    return UseAction.DRINK;
                }

                @Override
                public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
                    if (!world.isClient && user instanceof PlayerEntity player) {
                        // 1. 飛行を許可
                        player.getAbilities().allowFlying = true;
                        player.sendAbilitiesUpdate();
                        LeanMod.LOGGER.info("Flying enabled for 20 seconds!");

                        // 2. 20秒後に実行するタスクを予約
                        scheduler.schedule(() -> {
                            // クリエイティブモードの人は無効化しないようにチェック
                            if (!player.isCreative() && !player.isSpectator()) {
                                player.getAbilities().allowFlying = false;
                                player.getAbilities().flying = false; // 飛行中なら落とす
                                player.sendAbilitiesUpdate();
                                LeanMod.LOGGER.info("Flying disabled!");
                            }
                        }, 20, TimeUnit.SECONDS); // 20秒後に実行
                    }
                    return super.finishUsing(stack, world, user);
                }
            }
    );

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(LeanMod.MOD_ID, name), item);
    }

    public static void initialize() {
        LeanMod.LOGGER.info("Registering Mod Items for " + LeanMod.MOD_ID);
    }
}