package minefantasy.mf2.item.gadget;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.entity.EntityCogwork;
import minefantasy.mf2.entity.mob.EntityDragon;
import minefantasy.mf2.entity.mob.EntityHound;
import minefantasy.mf2.entity.mob.EntityMinotaur;
import minefantasy.mf2.item.ItemComponentMF;
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

public class MobSpawnerMF extends ItemComponentMF {
    public String[] types = new String[]{"dragon", "minotaur", "hound", "cogwork"};
    public IIcon[] icons;

    public MobSpawnerMF() {
        super("MF_Spawner");
        this.setHasSubtypes(true);
        this.setCreativeTab(CreativeTabs.tabMisc);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack item, EntityPlayer user, List list, boolean extra) {
        list.add(StatCollector
                .translateToLocal("item.spawn_" + types[Math.min(types.length - 1, item.getItemDamage())] + ".desc"));
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
        return StatCollector
                .translateToLocal("item.spawn_" + types[Math.min(types.length - 1, item.getItemDamage())] + ".name");
    }

    @Override
    public boolean onItemUse(ItemStack item, EntityPlayer user, World world, int blockX, int blockY, int blockZ,
                             int face, float ox, float oy, float oz) {
        if (world.isRemote) {
            return true;
        } else {
            Block block = world.getBlock(blockX, blockY, blockZ);
            blockX += Facing.offsetsXForSide[face];
            blockY += Facing.offsetsYForSide[face];
            blockZ += Facing.offsetsZForSide[face];
            double d0 = 0.0D;

            if (face == 1 && block.getRenderType() == 11) {
                d0 = 0.5D;
            }

            Entity entity = spawnCreature(world, item.getItemDamage(), blockX + 0.5D, blockY + d0, blockZ + 0.5D);

            if (entity != null) {
                if (entity instanceof EntityLivingBase && item.hasDisplayName()) {
                    ((EntityLiving) entity).setCustomNameTag(item.getDisplayName());
                }

                if (!user.capabilities.isCreativeMode) {
                    --item.stackSize;
                }
            }

            return true;
        }
    }

    private Entity spawnCreature(World world, int itemDamage, double x, double y, double z) {
        EntityLivingBase entityliving = getLiving(world, itemDamage);

        entityliving.setLocationAndAngles(x, y, z, MathHelper.wrapAngleTo180_float(world.rand.nextFloat() * 360.0F),
                0.0F);
        entityliving.rotationYawHead = entityliving.rotationYaw;
        entityliving.renderYawOffset = entityliving.rotationYaw;
        if (entityliving instanceof EntityLiving) {
            ((EntityLiving) entityliving).onSpawnWithEgg((IEntityLivingData) null);
        }
        world.spawnEntityInWorld(entityliving);
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
            icons[i] = reg.registerIcon("minefantasy2:Other/spawn_" + types[i]);
        }
    }
}
