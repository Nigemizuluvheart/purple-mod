package com.NLH.envy.item;

import com.NLH.envy.EnvyMod;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.UseAction;

public class ModItems {

    // 1. リーンの性能（飲み物設定）を定義
    public static final FoodComponent LEAN_FOOD = new FoodComponent.Builder()
            .nutrition(2) // 満腹度の回復量
            .saturationModifier(0.2f) // 腹持ち
            .alwaysEdible() // お腹がいっぱいでも飲める
            // 飲んだ時のエフェクト（例：吐き気 15秒、移動速度上昇 20秒）
            .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 300, 0), 1.0f)
            .statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 400, 1), 1.0f)
            .build();

    public static final Item LEAN = registerItem("lean",
            new Item(new Item.Settings()
                    .food(LEAN_FOOD) // 食べ物設定を適用
                    .maxCount(64)    // 64個まで重ねられる
            ) {
                // 飲んでいる時のアニメーションを「飲み物を飲む動作」にする
                @Override
                public UseAction getUseAction(ItemStack stack) {
                    return UseAction.DRINK;
                }
            }
    );

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(EnvyMod.MOD_ID, name), item);
    }

    public static void initialize() {
        EnvyMod.LOGGER.info("Registering Mod Items for " + EnvyMod.MOD_ID);

        //ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK)
                //.register(entries -> {
                //    entries.add(LEAN);
                //});
        //上はenvyタブを追加したのでいりません
    }
}