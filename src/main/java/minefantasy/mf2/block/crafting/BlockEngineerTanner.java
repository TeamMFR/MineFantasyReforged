package minefantasy.mf2.block.crafting;

import minefantasy.mf2.block.tileentity.TileEntityTanningRack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class BlockEngineerTanner extends BlockTanningRack {

    public BlockEngineerTanner(int tier, String tex) {
        super(tier, tex);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer user, int side, float xOffset,
                                    float yOffset, float zOffset) {
        TileEntityTanningRack tile = getTile(world, x, y, z);
        if (tile != null) {
            return tile.interact(user, false, side == 1);
        }
        return true;
    }
}
