package minefantasy.mfr.block.basic;

import minefantasy.mfr.tile.TileEntityWorldGenMarker;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockWorldGenMarker extends BlockContainer {

    public BlockWorldGenMarker(String name) {
        super(Material.AIR);
        this.setBlockUnbreakable();
        setRegistryName(name);

    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityWorldGenMarker();
    }

}
