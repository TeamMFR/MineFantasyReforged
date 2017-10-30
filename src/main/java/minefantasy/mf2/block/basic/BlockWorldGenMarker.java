package minefantasy.mf2.block.basic;

import cpw.mods.fml.common.registry.GameRegistry;
import minefantasy.mf2.block.tileentity.TileEntityWorldGenMarker;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockWorldGenMarker extends BlockContainer {

    public BlockWorldGenMarker() {
        super(Material.air);
        this.setBlockUnbreakable();
        GameRegistry.registerBlock(this, "WorldGenFlag");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityWorldGenMarker();
    }

}
