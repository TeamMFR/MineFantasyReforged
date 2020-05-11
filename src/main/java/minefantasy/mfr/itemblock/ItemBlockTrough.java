package minefantasy.mfr.itemblock;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.api.helpers.CustomToolHelper;
import minefantasy.mfr.api.material.CustomMaterial;
import minefantasy.mfr.api.tool.IStorageBlock;
import minefantasy.mfr.block.decor.BlockTrough;
import minefantasy.mfr.block.tile.decor.TileEntityTrough;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class ItemBlockTrough extends ItemBlockBase implements IStorageBlock {
    private Random rand = new Random();

    public ItemBlockTrough(Block base) {
        super(base);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack item, World world, List list, ITooltipFlag flag) {
        if (item.hasTagCompound() && item.getTagCompound().hasKey(BlockTrough.NBT_fill)) {
            int stock = item.getTagCompound().getInteger(BlockTrough.NBT_fill);
            if (stock > 0) {
                list.add(I18n.translateToLocalFormatted("attribute.fill", stock));
            }
        }
        CustomMaterial material = CustomMaterial.getMaterialFor(item, CustomToolHelper.slot_main);
        if (material != null) {
            list.add(I18n.translateToLocalFormatted("attribute.fill.capacity.name",
                    TileEntityTrough.getCapacity(material.tier) * TileEntityTrough.capacityScale));
        }
    }

    @Override
    public void getSubItems(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        if (MineFantasyReborn.isDebug()) {
            ArrayList<CustomMaterial> wood = CustomMaterial.getList("wood");
            Iterator iteratorWood = wood.iterator();
            while (iteratorWood.hasNext()) {
                CustomMaterial customMat = (CustomMaterial) iteratorWood.next();
                items.add(this.construct(customMat.name));
            }
        } else {
            items.add(this.construct("RefinedWood"));
        }
    }

    private ItemStack construct(String name) {
        return CustomToolHelper.constructSingleColoredLayer(this, name, 1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack item) {
        return CustomToolHelper.getLocalisedName(item, this.getUnlocalizedNameInefficiently(item) + ".name");
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        RayTraceResult movingobjectposition = this.rayTrace(world, player, true);

        if (movingobjectposition == null) {
            return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
        } else {
            if (movingobjectposition.typeOfHit == RayTraceResult.Type.BLOCK) {
                BlockPos hit = movingobjectposition.getBlockPos();
                ItemStack item = player.getHeldItem(hand);

                if (!world.canMineBlockBody(player, hit)) {
                    return super.onItemUse( player, world, pos, hand, facing, hitX, hitY, hitZ);
                }

                if (!player.canPlayerEdit(hit, movingobjectposition.sideHit, item)) {
                    return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
                }

                if (isWaterSource(world, hit)) {
                    gather(player);
                    world.playSound(player, pos, SoundEvents.ENTITY_GENERIC_SPLASH, SoundCategory.AMBIENT, 0.125F + rand.nextFloat() / 4F, 0.5F + rand.nextFloat());
                    return EnumActionResult.FAIL;
                }
            }
            return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
        }
    }

    private void gather(EntityPlayer player) {
        ItemStack item = player.getHeldItem(EnumHand.MAIN_HAND);
        if (item != null) {
            int tier = 0;
            CustomMaterial material = CustomMaterial.getMaterialFor(item, CustomToolHelper.slot_main);
            if (material != null) {
                tier = material.tier;
            }
            NBTTagCompound nbt = getNBT(item);
            nbt.setInteger(BlockTrough.NBT_fill, TileEntityTrough.getCapacity(tier) * TileEntityTrough.capacityScale);
        }
        player.swingArm(EnumHand.MAIN_HAND);

    }

    private NBTTagCompound getNBT(ItemStack item) {
        if (!item.hasTagCompound()) {
            item.setTagCompound(new NBTTagCompound());
        }
        return item.getTagCompound();
    }

    private boolean isWaterSource(World world, BlockPos pos) {
        if (world.getBlockState(pos).getMaterial() == Material.WATER) {
            return true;
        }
        return false;
    }
}
