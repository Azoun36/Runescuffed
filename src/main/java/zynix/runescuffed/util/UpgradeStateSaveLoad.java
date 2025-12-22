package zynix.runescuffed.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Uuids;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.PersistentStateType;
import net.minecraft.world.World;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UpgradeStateSaveLoad extends PersistentState {

    public final List<UUID> upgradedPlayers;

    public UpgradeStateSaveLoad(List<UUID> upgradedPlayers) {
        this.upgradedPlayers = new ArrayList<>(upgradedPlayers);
    }

    public UpgradeStateSaveLoad() {
        this.upgradedPlayers = new ArrayList<>();
    }

    public static final Codec<UpgradeStateSaveLoad> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Uuids.INT_STREAM_CODEC.listOf()
                            .fieldOf("upgraded_players")
                            .forGetter(state -> state.upgradedPlayers)
            ).apply(instance, UpgradeStateSaveLoad::new)
    );

    public static final PersistentStateType<UpgradeStateSaveLoad> TYPE = new PersistentStateType<>(
            "runescuffed_upgrades",
            UpgradeStateSaveLoad::new,
            CODEC,
            DataFixTypes.PLAYER
    );

    public boolean isPlayerUpgraded(UUID uuid) {
        return this.upgradedPlayers.contains(uuid);
    }

    public void addUpgradedPlayer(UUID uuid) {
        if (!this.upgradedPlayers.contains(uuid)) {
            this.upgradedPlayers.add(uuid);
            this.markDirty();
        }
    }

    public static UpgradeStateSaveLoad getServerState(MinecraftServer server) {
        if (server == null) return new UpgradeStateSaveLoad();
        PersistentStateManager manager = server.getWorld(World.OVERWORLD).getPersistentStateManager();
        return manager.getOrCreate(TYPE);
    }
}