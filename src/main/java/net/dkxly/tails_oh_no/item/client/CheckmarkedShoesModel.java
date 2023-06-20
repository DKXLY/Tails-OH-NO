package net.dkxly.tails_oh_no.item.client;

import net.dkxly.tails_oh_no.TailsOhNo;
import net.dkxly.tails_oh_no.item.custom.CheckmarkedShoesItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class CheckmarkedShoesModel extends GeoModel<CheckmarkedShoesItem> {
    @Override
    public Identifier getModelResource(CheckmarkedShoesItem animatable) {
        return new Identifier(TailsOhNo.MOD_ID, "geo/checkmarked_shoes.geo.json");
    }

    @Override
    public Identifier getTextureResource(CheckmarkedShoesItem animatable) {
        return new Identifier(TailsOhNo.MOD_ID, "textures/armor/checkmarked_shoes.png");
    }

    @Override
    public Identifier getAnimationResource(CheckmarkedShoesItem animatable) {
        return new Identifier(TailsOhNo.MOD_ID, "animations/checkmarked_shoes.animation.json");
    }
}
