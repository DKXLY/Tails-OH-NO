package net.dkxly.tails_oh_no.item;

import net.dkxly.tails_oh_no.TailsOhNo;
import net.dkxly.tails_oh_no.item.custom.CheckmarkItem;
import net.dkxly.tails_oh_no.item.custom.CheckmarkedShoesItem;
import net.dkxly.tails_oh_no.item.custom.CoinItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item COIN = Registry.register(Registries.ITEM,
            new Identifier(TailsOhNo.MOD_ID, "coin"),
            new CoinItem(new FabricItemSettings()));
    public static final Item CHECKMARK = Registry.register(Registries.ITEM,
            new Identifier(TailsOhNo.MOD_ID, "checkmark"),
            new CheckmarkItem(new FabricItemSettings()));
    public static final Item CHECKMARKED_SHOES = Registry.register(Registries.ITEM,
            new Identifier(TailsOhNo.MOD_ID, "checkmarked_shoes"),
            new CheckmarkedShoesItem(ModArmorMaterials.CHECKMARKED_SHOES, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static void registerModItems() {
        TailsOhNo.LOGGER.debug("Registering mod items for " + TailsOhNo.MOD_ID);
    }
}
