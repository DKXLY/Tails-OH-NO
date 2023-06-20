package net.dkxly.tails_oh_no.item.custom;


import net.dkxly.tails_oh_no.item.ModItemGroups;
import net.dkxly.tails_oh_no.packet.CloseClientPacket;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static net.dkxly.tails_oh_no.TailsOhNo.allowMovement;
import static net.dkxly.tails_oh_no.item.ModItems.COIN;

public class CoinItem extends Item {
    public static int result;

    public static ServerPlayerEntity serverPlayer;

    public static GameMode previousGameMode;

    public CoinItem(Settings settings) {
        super(settings);
        ItemGroupEvents.modifyEntriesEvent(ModItemGroups.TAILS_OH_NO).register(entries -> entries.add(COIN));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.of(""));
        tooltip.add(Text.translatable("A very interesting coin!").formatted(Formatting.DARK_GRAY, Formatting.ITALIC));
        tooltip.add(Text.translatable("I sure hope it does something useful...").formatted(Formatting.DARK_GRAY, Formatting.ITALIC));

        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {

        // Getting the currently held ItemStack to return at the end of the method
        ItemStack heldItemStack = playerEntity.getStackInHand(hand);

        // Casting the playerEntity to ServerPlayerEntity, to be able to modify the server-side stuff about the player
        if (!world.isClient) {
            // An additional check if the code is running on the server to be extra safe
            if (playerEntity instanceof ServerPlayerEntity) {
                serverPlayer = (ServerPlayerEntity) playerEntity;
            }
        }

        // Create a new ScheduledExecutorService for delaying certain parts of this code
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        // Play a sound and make the player invincible
        if (!world.isClient) {
            // Play the "suspense" sound when flipping the coin
            for(int i = 0; i < 9; i++) {
                serverPlayer.playSound(SoundEvents.BLOCK_BEACON_ACTIVATE, 2f, 0.1f);
            }

            // Add resistance so the player doesn't die from something, when stuck in place
            serverPlayer.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 110, 9999, false, false, false));
        }

        // Play the "suspense" sound when flipping the coin
        for(int i = 0; i < 9; i++) {
            playerEntity.playSound(SoundEvents.BLOCK_BEACON_ACTIVATE, 2f, 0.1f);
        }

        // Changing the boolean allowMovement, on which the mixin PlayerMovementMixin depends on to function
        allowMovement = false;

        // Setting the player velocity to zero so the stopping in place doesn't feel buggy
        playerEntity.setVelocity(Vec3d.ZERO);

        if (!world.isClient) {

            // Setting the serverPlayer velocity to zero so the stopping in place doesn't feel buggy
            serverPlayer.setVelocity(Vec3d.ZERO);

            // Getting the current gamemode to change it back to
            previousGameMode = serverPlayer.interactionManager.getGameMode();

            // Changing the gamemode to adventure mode so the player isn't able to interact with anything
            serverPlayer.interactionManager.changeGameMode(GameMode.ADVENTURE);
            // Letting the client know about the gamemode change
            serverPlayer.networkHandler.sendPacket(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.GAME_MODE_CHANGED, GameMode.ADVENTURE.getId()));

            // Adding the status effects to make a nice suspense effect combined with the sound played
            serverPlayer.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 100, 9999, false, false, false));
            serverPlayer.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 100, 9999, false, false, false));
        }

        // Getting the result of the coin flip (might have to change the random behaviour to be packet S2C based, so it doesn't have inconsistencies with the client?)
        Random random = new Random();
        result = random.nextInt(2);

        // Changing the model data to display the animated texture for when the coin is flipping
        heldItemStack.getOrCreateNbt().putInt("CustomModelData", 1);

        // Using the ScheduledExecutorService executorService to add some time it takes for the coin flip result to be revealed
        executorService.schedule(() -> {

            // Modify the model data to the not-animated coin texture
            heldItemStack.getOrCreateNbt().putInt("CustomModelData", 0);

            // Allowing the player to move again
            allowMovement = true;

            // Coin flip result logic
            if (result == 0) {

                // Play the sounds for the result
                playerEntity.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
                playerEntity.playSound(SoundEvents.ENTITY_TNT_PRIMED, 1.0f, 1.0f);

                if (!world.isClient) {
                    // Changing the gamemode to the previous one
                    serverPlayer.interactionManager.changeGameMode(previousGameMode);
                    serverPlayer.networkHandler.sendPacket(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.GAME_MODE_CHANGED, previousGameMode.getId()));

                    // Send a title packet to the client, displaying the coin flip result
                    serverPlayer.networkHandler.sendPacket(new TitleS2CPacket(
                            Text.literal("Tails! Goodbye!").formatted(Formatting.GOLD)
                    ));


                    serverPlayer.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
                    serverPlayer.playSound(SoundEvents.ENTITY_TNT_PRIMED, 1.0f, 1.0f);

                    // Using the executorService once again to schedule the packet send to crash the client
                    executorService.schedule(() -> {

                        // Saving the server right before closing the client just in case
                        //noinspection DataFlowIssue
                        world.getServer().save(false, false, true);

                        // Send the packet to close the client
                        ServerPlayNetworking.send(serverPlayer, CloseClientPacket.getId(), PacketByteBufs.create());
                        return TypedActionResult.success(heldItemStack);
                    }, 2, TimeUnit.SECONDS);
                }
            } else {
                // Play the sound for a nice effect
                playerEntity.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);


                if (!world.isClient) {
                    // Changing the gamemode to the previous one
                    serverPlayer.interactionManager.changeGameMode(previousGameMode);
                    serverPlayer.networkHandler.sendPacket(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.GAME_MODE_CHANGED, previousGameMode.getId()));

                    // Send a title packet to the client, displaying the coin flip result
                    serverPlayer.networkHandler.sendPacket(new TitleS2CPacket(
                            Text.literal("Heads!").formatted(Formatting.GOLD)
                    ));

                    // Play the sound for a nice effect
                    serverPlayer.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);

                    // Sending the packet to clear the title
                    //noinspection CodeBlock2Expr
                    executorService.schedule(() -> {
                        serverPlayer.networkHandler.sendPacket(new TitleS2CPacket(
                                Text.literal("")
                        ));
                    }, 2, TimeUnit.SECONDS);
                }
            }
        }, 5, TimeUnit.SECONDS);

        return TypedActionResult.success(heldItemStack);
    }
}
