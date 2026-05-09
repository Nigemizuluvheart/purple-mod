package com.NLH.envy.item;

import com.NLH.envy.LeanMod;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {

    // 1. 独自のクリエイティブタブを定義
    public static final ItemGroup ENVY_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(LeanMod.MOD_ID, "envy_group"),
            FabricItemGroup.builder()
                    .displayName(Text.translatable("itemgroup.envy_group")) // タブの表示名
                    .icon(() -> new ItemStack(ModItems.LEAN)) // タブのアイコン（リーンの画像になります）
                    .entries((displayContext, entries) -> {
                        // ここにタブに入れたいアイテムを追加していく
                        entries.add(ModItems.LEAN);
                    }).build());

    public static void registerItemGroups() {
        LeanMod.LOGGER.info("Registering Item Groups for " + LeanMod.MOD_ID);
    }
}