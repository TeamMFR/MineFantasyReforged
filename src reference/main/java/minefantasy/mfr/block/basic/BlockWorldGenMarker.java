package minefantasy.mfr.block.basic;

import minefantasy.mfr.MineFantasyReborn;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;
import minefantasy.mfr.tile.TileEntityWorldGenMarker;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockWorldGenMarker extends BlockContainer {

    public BlockWorldGenMarker() {
        super(Material.AIR);
        this.setBlockUnbreakable();
        GameRegistry.findRegistry(Block.class).register(this);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityWorldGenMarker();
    }

}
