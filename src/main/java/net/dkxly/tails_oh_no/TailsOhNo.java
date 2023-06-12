package net.dkxly.tails_oh_no;

import net.dkxly.tails_oh_no.item.ModItems;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TailsOhNo implements ModInitializer {
    public static boolean allowMovement = true;
    public static final String MOD_ID = "tails_oh_no";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModItems.registerModItems();
    }
}