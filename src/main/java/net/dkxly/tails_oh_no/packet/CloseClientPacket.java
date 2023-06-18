package net.dkxly.tails_oh_no.packet;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public abstract class CloseClientPacket implements Packet {

    public void write(PacketByteBuf buf) {
    }

    public void read(PacketByteBuf buf) {
    }

    public static Identifier getId() {
        return new Identifier("tails_oh_no", "close_client");
    }
}
