package net.dkxly.tails_oh_no.item.client;

import net.dkxly.tails_oh_no.item.custom.CheckmarkedShoesItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class CheckmarkedShoesRenderer extends GeoArmorRenderer<CheckmarkedShoesItem> {
    public CheckmarkedShoesRenderer() {
        super(new CheckmarkedShoesModel());
    }
}
