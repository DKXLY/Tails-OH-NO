package net.dkxly.tails_oh_no.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.network.ClientPlayerEntity;

import static net.dkxly.tails_oh_no.TailsOhNo.allowMovement;

@Mixin(ClientPlayerEntity.class)
public class PlayerMovementMixin {

	@Inject(method = "tickMovement", at = @At("HEAD"), cancellable = true)
	private void tickMovement(CallbackInfo info) {
		if (!allowMovement) {
			info.cancel();
		}
	}
}
