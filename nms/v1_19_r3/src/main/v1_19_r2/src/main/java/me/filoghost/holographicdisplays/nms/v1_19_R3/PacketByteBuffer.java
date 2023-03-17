/*
 * Copyright (C) filoghost and contributors
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package me.filoghost.holographicdisplays.nms.v1_19_R2;

import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketDataSerializer;

import java.util.UUID;

class PacketByteBuffer extends PacketDataSerializer {

    private static final PacketByteBuffer INSTANCE = new PacketByteBuffer();

    static PacketByteBuffer get() {
        INSTANCE.clear();
        return INSTANCE;
    }

    private PacketByteBuffer() {
        super(Unpooled.buffer());
    }

    void writeVarInt(int i) {
        super.d(i);
    }

    void writeVarIntArray(int i1) {
        writeVarInt(1);
        writeVarInt(i1);
    }

    void writeVarIntArray(int i1, int i2) {
        writeVarInt(2);
        writeVarInt(i1);
        writeVarInt(i2);
    }

    void writeUUID(UUID uuid) {
        super.a(uuid);
    }

    <T> void writeDataWatcherEntry(DataWatcherKey<T> key, T value) {
        writeByte(key.getIndex());
        writeVarInt(key.getSerializerTypeID());
        key.getSerializer().a(this, value);
    }

    void writeDataWatcherEntriesEnd() {
        writeByte(0xFF);
    }

}
