package minefantasy.mf2.item;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.mechanics.worldGen.structure.WorldGenAncientAlter;
import minefantasy.mf2.mechanics.worldGen.structure.WorldGenAncientForge;
import minefantasy.mf2.mechanics.worldGen.structure.WorldGenStructureBase;
import minefantasy.mf2.mechanics.worldGen.structure.dwarven.WorldGenDwarvenStronghold;
import minefantasy.mf2.util.MFLogUtil;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class ItemWorldGenPlacer extends Item {
    private static final String[] types = new String[]{"AncientForge", "AncientAlter", "DwarvenStronghold"};
    public IIcon[] icons;

    public ItemWorldGenPlacer() {
        this.setCreativeTab(CreativeTabs.tabMisc);
        GameRegistry.registerItem(this, "PlaceWorldGenMF", MineFantasyII.MODID);
        this.setHasSubtypes(true);
    }

    @Override
    public boolean onItemUse(ItemStack item, EntityPlayer user, World world, int x, int y, int z, int side,
                             float offsetX, float offsetY, float offsetZ) {
        if (side == 0) {
            --y;
        }

        if (side == 1) {
            ++y;
        }

        if (side == 2) {
            --z;
        }

        if (side == 3) {
            ++z;
        }

        if (side == 4) {
            --x;
        }

        if (side == 5) {
            ++x;
        }

        if (!user.canPlayerEdit(x, y, z, side, item)) {
            return false;
        } else {
            if (!world.isRemote) {
                WorldGenStructureBase wg = getWorldGen(item.getItemDamage());
                if (wg instanceof WorldGenDwarvenStronghold) {
                    WorldGenDwarvenStronghold ds = (WorldGenDwarvenStronghold) wg;
                    MFLogUtil.logDebug("DS: Try Cliff Build");
                    if (!ds.generate(world, itemRand, x, y, z)) {
                        MFLogUtil.logDebug("Failed... DS: Try Surface Build");
                        ds.setSurfaceMode(true);
                        if (ds.generate(world, itemRand, x, y, z)) {
                            MFLogUtil.logDebug("Success... DS: Placed Surface Build");
                        } else {
                            MFLogUtil.logDebug("Failed... DS: No Build Placed");
                        }
                    } else {
                        MFLogUtil.logDebug("Success... DS: Placed Cliff Build");
                    }
                } else {
                    wg.generate(world, itemRand, x, y, z);
                }
            }
            return true;
        }
    }

    @Override
    public String getItemStackDisplayName(ItemStack item) {
        int d = MathHelper.clamp_int(item.getItemDamage(), 0, types.length - 1);
        return "World Gen " + types[d];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (int id = 0; id < types.length; id++)
            list.add(new ItemStack(item, 1, id));
    }

    private WorldGenStructureBase getWorldGen(int meta) {
        if (meta == 1) {
            return new WorldGenAncientAlter();
        }
        if (meta == 2) {
            return new WorldGenDwarvenStronghold();
        }
        return new WorldGenAncientForge();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int d) {
        return icons[Math.min(icons.length - 1, d)];
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister reg) {
        icons = new IIcon[types.length];
        for (int i = 0; i < types.length; i++) {
            icons[i] = reg.registerIcon("minefantasy2:Other/generate_" + types[i]);
        }
    }
}
