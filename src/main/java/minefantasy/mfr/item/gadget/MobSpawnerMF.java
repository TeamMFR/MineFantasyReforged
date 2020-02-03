package minefantasy.mfr.item.gadget;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import minefantasy.mfr.entity.EntityCogwork;
import minefantasy.mfr.entity.mob.EntityDragon;
import minefantasy.mfr.entity.mob.EntityHound;
import minefantasy.mfr.entity.mob.EntityMinotaur;
import minefantasy.mfr.item.ItemComponentMFR;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

public class MobSpawnerMF extends ItemComponentMFR {
    public String[] types = new String[]{"dragon", "minotaur", "hound", "cogwork"};

    public MobSpawnerMF() {
        super("MF_Spawner");
        this.setHasSubtypes(true);
        this.setCreativeTab(CreativeTabs.MISC);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack item, EntityPlayer user, List list, boolean extra) {
        list.add(I18n.translateToLocal("item.spawn_" + types[Math.min(types.length - 1, item.getItemDamage())] + ".desc"));
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < types.length; i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack item) {
        return I18n.translateToLocal("item.spawn_" + types[Math.min(types.length - 1, item.getItemDamage())] + ".name");
    }

    @Override
    public boolean onItemUse(ItemStack item, EntityPlayer user, World world, BlockPos pos,
                             int face, float ox, float oy, float oz) {
        if (world.isRemote) {
            return true;
        } else {
            IBlockState block = world.getBlockState(pos);
            blockX += Facing.offsetsXForSide[face];
            blockY += Facing.offsetsYForSide[face];
            blockZ += Facing.offsetsZForSide[face];
            double d0 = 0.0D;

            if (face == 1 && block.getRenderType() == 11) {
                d0 = 0.5D;
            }

            Entity entity = spawnCreature(world, item.getItemDamage(), pos.getX() + 0.5D, pos.getY() + d0, pos.getZ() + 0.5D);

            if (entity != null) {
                if (entity instanceof EntityLivingBase && item.hasDisplayName()) {
                    ((EntityLiving) entity).setCustomNameTag(item.getDisplayName());
                }

                if (!user.capabilities.isCreativeMode) {
                    item.shrink(1);
                }
            }

            return true;
        }
    }

    private Entity spawnCreature(World world, int itemDamage, double x, double y, double z) {
        EntityLivingBase entityliving = getLiving(world, itemDamage);

        entityliving.setLocationAndAngles(x, y, z, MathHelper.wrapDegrees(world.rand.nextFloat() * 360.0F),
                0.0F);
        entityliving.rotationYawHead = entityliving.rotationYaw;
        entityliving.renderYawOffset = entityliving.rotationYaw;
        world.spawnEntity(entityliving);
        if (entityliving instanceof EntityLiving) {
            ((EntityLiving) entityliving).playLivingSound();
        }

        return entityliving;
    }

    private EntityLivingBase getLiving(World world, int id) {
        switch (id) {
            case 0:
                return new EntityDragon(world);
            case 1:
                return new EntityMinotaur(world);
            case 2:
                return new EntityHound(world);
            case 3:
                return new EntityCogwork(world);

            default:
                return new EntityDragon(world);
        }
    }
}
