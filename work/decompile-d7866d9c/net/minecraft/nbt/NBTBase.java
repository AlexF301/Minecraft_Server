package net.minecraft.nbt;

import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.EnumChatFormat;
import net.minecraft.network.chat.IChatBaseComponent;

public interface NBTBase {

    EnumChatFormat d = EnumChatFormat.AQUA;
    EnumChatFormat e = EnumChatFormat.GREEN;
    EnumChatFormat f = EnumChatFormat.GOLD;
    EnumChatFormat g = EnumChatFormat.RED;

    void write(DataOutput dataoutput) throws IOException;

    String toString();

    byte getTypeId();

    NBTTagType<?> b();

    NBTBase clone();

    default String asString() {
        return this.toString();
    }

    default IChatBaseComponent l() {
        return this.a("", 0);
    }

    IChatBaseComponent a(String s, int i);
}
