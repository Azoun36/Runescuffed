package zynix.runescuffed;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public class SpellNetwork {

    public static final Identifier SPELL_CHANGE_PACKET = Identifier.of("runescuffed", "spell_change");

    public record SpellChangePayload(String spellName) implements CustomPayload {
        public static final Id<SpellChangePayload> ID = new Id<>(SPELL_CHANGE_PACKET);
        public static final PacketCodec<RegistryByteBuf, SpellChangePayload> CODEC = PacketCodec.tuple(
                PacketCodecs.STRING, SpellChangePayload::spellName, SpellChangePayload::new);

        @Override public Id<? extends CustomPayload> getId() { return ID; }
    }

}
