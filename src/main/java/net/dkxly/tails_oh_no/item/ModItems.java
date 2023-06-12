package net.dkxly.tails_oh_no.item;

import net.dkxly.tails_oh_no.TailsOhNo;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final CoinItem COIN = Registry.register(Registries.ITEM,
            new Identifier(TailsOhNo.MOD_ID, "coin"),
            new CoinItem(new FabricItemSettings()));

    public static void registerModItems() {
        TailsOhNo.LOGGER.debug("Registering mod items for " + TailsOhNo.MOD_ID);

    }
}
