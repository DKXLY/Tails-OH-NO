package net.dkxly.tails_oh_no;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class TailsOhNoClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(net.dkxly.tails_oh_no.packet.CloseClientPacket.getId(), (client, handler, buf, responseSender) -> {
            client.scheduleStop();
        });
    }
}
