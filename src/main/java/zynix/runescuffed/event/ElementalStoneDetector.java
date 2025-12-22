package zynix.runescuffed.event;

import net.minecraft.block.Block;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;

public class ElementalStoneDetector {

    public static void findNearestStone(ServerPlayerEntity player, Block targetBlock, String stoneName, Formatting color) {
        ServerWorld world = (ServerWorld) player.getEntityWorld();
        BlockPos playerPos = player.getBlockPos();
        BlockPos foundPos = null;

        int radius = 48;
        int minY = world.getBottomY();
        int maxY = world.getTopYInclusive();

        outer:
        for (int r = 0; r <= radius; r++) {
            for (int x = -r; x <= r; x++) {
                for (int z = -r; z <= r; z++) {
                    if (Math.abs(x) == r || Math.abs(z) == r) {

                        for (int y = minY; y <= maxY; y++) {
                            BlockPos checkPos = new BlockPos(playerPos.getX() + x, y, playerPos.getZ() + z);

                            if (world.getBlockState(checkPos).isOf(targetBlock)) {
                                foundPos = checkPos;
                                break outer;
                            }
                        }
                    }
                }
            }
        }

        if (foundPos != null) {
            String dir = getDirection(playerPos, foundPos);
            player.sendMessage(Text.literal("A " + stoneName + " is detected! It lies to the " + dir)
                    .formatted(color), true);
        } else {
            player.sendMessage(Text.literal("The talisman remains still...").formatted(Formatting.GRAY), true);
        }
    }

    private static String getDirection(BlockPos origin, BlockPos target) {
        int dx = target.getX() - origin.getX();
        int dz = target.getZ() - origin.getZ();
        StringBuilder sb = new StringBuilder();

        if (Math.abs(dz) > Math.abs(dx) * 0.5) sb.append(dz > 0 ? "South" : "North");
        if (Math.abs(dx) > Math.abs(dz) * 0.5) sb.append(dx > 0 ? "East" : "West");

        return sb.isEmpty() ? "nearby" : sb.toString();
    }
}