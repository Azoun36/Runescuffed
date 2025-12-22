package zynix.runescuffed.item.custom;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import zynix.runescuffed.event.ElementalStoneDetector;

public class ElementalTalisman extends Item {
    private final Block targetBlock;
    private final String stoneName;
    private final Formatting color;

    public ElementalTalisman(Settings settings, Block targetBlock, String stoneName, Formatting color) {
        super(settings);
        this.targetBlock = targetBlock;
        this.stoneName = stoneName;
        this.color = color;
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient()) {
            ElementalStoneDetector.findNearestStone((ServerPlayerEntity) user, targetBlock, stoneName, color);
            user.getItemCooldownManager().set(user.getStackInHand(hand), 200);
        }

        return ActionResult.SUCCESS;
    }
}
