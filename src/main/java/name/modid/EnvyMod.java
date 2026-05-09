package com.NLH.envy; // ここを修正！

import com.NLH.envy.item.ModItemGroups;
import com.NLH.envy.item.ModItems; // これを追加
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnvyMod implements ModInitializer {
	public static final String MOD_ID = "envy-mod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups(); //アイテムの登録より先に
		ModItems.initialize(); // アイテムの登録
		LOGGER.info("Lean Mod Loaded!");
	}
}