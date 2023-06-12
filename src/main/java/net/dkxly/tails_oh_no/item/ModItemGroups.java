package net.dkxly.tails_oh_no.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static net.dkxly.tails_oh_no.item.ModItems.COIN;

public class ModItemGroups {
    public static ItemGroup TAILS_OH_NO = FabricItemGroup.builder(new Identifier("tails_oh_no", "tails_oh_no"))
            .icon(() -> new ItemStack(COIN))
            .displayName(Text.literal("Tails!?? OH NO"))
            .build();
}
