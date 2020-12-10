package minefantasy.mfr.item.gadget;

import minefantasy.mfr.entity.EntityCogwork;
import minefantasy.mfr.entity.mob.EntityDragon;
import minefantasy.mfr.entity.mob.EntityHound;
import minefantasy.mfr.entity.mob.EntityMinotaur;
import minefantasy.mfr.item.ItemComponentMFR;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemMobSpawner extends ItemComponentMFR {
    public String[] types = new String[]{"dragon", "minotaur", "hound", "cogwork"};

    public ItemMobSpawner() {
        super("MF_Spawner");
        this.setHasSubtypes(true);
        this.setCreativeTab(CreativeTabs.MISC);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack item, @Nullable World world, List list, ITooltipFlag flagIn) {
        list.add(I18n.format("item.spawn_" + types[Math.min(types.length - 1, item.getItemDamage())] + ".desc"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack item) {
        return I18n.format("item.spawn_" + types[Math.min(types.length - 1, item.getItemDamage())] + ".name");
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer user, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack item = user.getHeldItem(hand);
        if (world.isRemote) {
            return EnumActionResult.FAIL;
        } else {
            IBlockState block = world.getBlockState(pos);

            BlockPos blockpos = pos.offset(facing);
            double d0 = this.getYOffset(world, blockpos);
            Entity entity = spawnCreature(world, getNamedIdFrom(item), (double)blockpos.getX() + 0.5D, (double)blockpos.getY() + d0, (double)blockpos.getZ() + 0.5D);

            if (entity != null) {
                if (entity instanceof EntityLivingBase && item.hasDisplayName()) {
                    ((EntityLiving) entity).setCustomNameTag(item.getDisplayName());
                }

                if (!user.capabilities.isCreativeMode) {
                    item.shrink(1);
                }
            }

            return EnumActionResult.PASS;
        }
    }

    @Nullable
    public static Entity spawnCreature(World worldIn, @Nullable ResourceLocation entityID, double x, double y, double z)
    {
        if (entityID != null && EntityList.ENTITY_EGGS.containsKey(entityID))
        {
            Entity entity = null;

            for (int i = 0; i < 1; ++i)
            {
                entity = EntityList.createEntityByIDFromName(entityID, worldIn);

                if (entity instanceof EntityLiving)
                {
                    EntityLiving entityliving = (EntityLiving)entity;
                    entity.setLocationAndAngles(x, y, z, MathHelper.wrapDegrees(worldIn.rand.nextFloat() * 360.0F), 0.0F);
                    entityliving.rotationYawHead = entityliving.rotationYaw;
                    entityliving.renderYawOffset = entityliving.rotationYaw;
                    if (net.minecraftforge.event.ForgeEventFactory.doSpecialSpawn(entityliving, worldIn, (float) x, (float) y, (float) z, null)) return null;
                    entityliving.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos(entityliving)), (IEntityLivingData)null);
                    worldIn.spawnEntity(entity);
                    entityliving.playLivingSound();
                }
            }

            return entity;
        }
        else
        {
            return null;
        }
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

    protected double getYOffset(World p_190909_1_, BlockPos p_190909_2_)
    {
        AxisAlignedBB axisalignedbb = (new AxisAlignedBB(p_190909_2_)).expand(0.0D, -1.0D, 0.0D);
        List<AxisAlignedBB> list = p_190909_1_.getCollisionBoxes((Entity)null, axisalignedbb);

        if (list.isEmpty())
        {
            return 0.0D;
        }
        else
        {
            double d0 = axisalignedbb.minY;

            for (AxisAlignedBB axisalignedbb1 : list)
            {
                d0 = Math.max(axisalignedbb1.maxY, d0);
            }

            return d0 - (double)p_190909_2_.getY();
        }
    }

    @Nullable
    public static ResourceLocation getNamedIdFrom(ItemStack stack)
    {
        NBTTagCompound nbttagcompound = stack.getTagCompound();

        if (nbttagcompound == null)
        {
            return null;
        }
        else if (!nbttagcompound.hasKey("EntityTag", 10))
        {
            return null;
        }
        else
        {
            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("EntityTag");

            if (!nbttagcompound1.hasKey("id", 8))
            {
                return null;
            }
            else
            {
                String s = nbttagcompound1.getString("id");
                ResourceLocation resourcelocation = new ResourceLocation(s);

                if (!s.contains(":"))
                {
                    nbttagcompound1.setString("id", resourcelocation.toString());
                }

                return resourcelocation;
            }
        }
    }
}
