package net.dkxly.tails_oh_no.item.custom;

import net.dkxly.tails_oh_no.item.ModItemGroups;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.dkxly.tails_oh_no.item.ModItems.CHECKMARK;

public class CheckmarkItem extends Item {
    public CheckmarkItem(Settings settings) {
        super(settings);
        ItemGroupEvents.modifyEntriesEvent(ModItemGroups.TAILS_OH_NO).register(entries -> entries.add(CHECKMARK));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.of(""));
        tooltip.add(Text.literal("Looks familiar...").formatted(Formatting.DARK_GRAY, Formatting.ITALIC));
    }
}
