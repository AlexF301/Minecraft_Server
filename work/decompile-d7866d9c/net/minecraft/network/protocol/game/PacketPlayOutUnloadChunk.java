package net.minecraft.network.protocol.game;

import java.io.IOException;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public class PacketPlayOutUnloadChunk implements Packet<PacketListenerPlayOut> {

    private int a;
    private int b;

    public PacketPlayOutUnloadChunk() {}

    public PacketPlayOutUnloadChunk(int i, int j) {
        this.a = i;
        this.b = j;
    }

    @Override
    public void a(PacketDataSerializer packetdataserializer) throws IOException {
        this.a = packetdataserializer.readInt();
        this.b = packetdataserializer.readInt();
    }

    @Override
    public void b(PacketDataSerializer packetdataserializer) throws IOException {
        packetdataserializer.writeInt(this.a);
        packetdataserializer.writeInt(this.b);
    }

    public void a(PacketListenerPlayOut packetlistenerplayout) {
        packetlistenerplayout.a(this);
    }
}
