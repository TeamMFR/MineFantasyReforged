package minefantasy.mfr.item.tool.advanced;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.api.helpers.CustomToolHelper;
import minefantasy.mfr.api.material.CustomMaterial;
import minefantasy.mfr.api.tier.IToolMaterial;
import minefantasy.mfr.block.tile.TileEntityRoad;
import minefantasy.mfr.init.BlockListMFR;
import minefantasy.mfr.init.CreativeTabMFR;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * @author Anonymous Productions
 */
public class ItemMattock extends ItemPickaxe implements IToolMaterial {
    protected int itemRarity;
    private String name;
    // ===================================================== CUSTOM START
    // =============================================================\\
    private float baseDamage = 1.0F;
    private boolean isCustom = false;
    private float efficiencyMod = 1.0F;

    public ItemMattock(String name, ToolMaterial material, int rarity) {
        super(material);
        itemRarity = rarity;
        setRegistryName(name);
        setUnlocalizedName(MineFantasyReborn.MOD_ID + "." + name);

        setCreativeTab(CreativeTabMFR.tabOldTools);
        this.setHarvestLevel("pickaxe", Math.max(0, material.getHarvestLevel() - 2));
        this.name = name;
    }

    @Override
    public ToolMaterial getMaterial() {
        return toolMaterial;
    }

    @Override
    public boolean canHarvestBlock(IBlockState block, ItemStack stack) {
        return super.canHarvestBlock(block, stack) || Items.IRON_SHOVEL.canHarvestBlock(block, stack);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack mattock = player.getHeldItem(hand);
        if (!player.canPlayerEdit(pos, facing, mattock)) {
            return EnumActionResult.FAIL;
        } else {
            Block ground = world.getBlockState(pos).getBlock();
            IBlockState groundmeta = world.getBlockState(pos);
            IBlockState above = world.getBlockState(pos.add(0,1,0));

            if (facing == EnumFacing.NORTH || above != Blocks.AIR) {
                return EnumActionResult.FAIL;
            } else if (ground == Blocks.GRASS || ground == Blocks.DIRT || ground == Blocks.SAND) {
                Block var13 = BlockListMFR.ROAD;
                if (ground == Blocks.GRASS) {
                    ground = Blocks.DIRT;
                }
                world.playSound(player, pos.add(0.5F,0.5F,0.5F), ground.getSoundType().getHitSound(), SoundCategory.AMBIENT,(var13.getSoundType().getVolume() + 1.0F) / 2.0F, var13.getSoundType().getPitch() * 0.8F);

                if (world.isRemote) {
                    return EnumActionResult.PASS;
                } else {
                    world.setBlockState(pos, var13.getDefaultState(), 2);

                    TileEntityRoad road = new TileEntityRoad();
                    road.setWorld(world);
                    world.setTileEntity(pos, road);
                    road.setSurface(ground, groundmeta.getBlock());
                    mattock.damageItem(1, player);
                    return EnumActionResult.PASS;
                }
            } else {
                return EnumActionResult.FAIL;
            }
        }
    }

    public ItemMattock setCustom(String s) {
        canRepair = false;
        isCustom = true;
        return this;
    }

    public ItemMattock setBaseDamage(float baseDamage) {
        this.baseDamage = baseDamage;
        return this;
    }

    public ItemMattock setEfficiencyMod(float efficiencyMod) {
        this.efficiencyMod = efficiencyMod;
        return this;
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack item) {
        Multimap map = HashMultimap.create();
        map.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(),
                new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", getMeleeDamage(item), 0));

        return map;
    }

    /**
     * Gets a stack-sensitive value for the melee dmg
     */
    protected float getMeleeDamage(ItemStack item) {
        return baseDamage + CustomToolHelper.getMeleeDamage(item, toolMaterial.getAttackDamage());
    }

    protected float getWeightModifier(ItemStack stack) {
        return CustomToolHelper.getWeightModifier(stack, 1.0F);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return CustomToolHelper.getMaxDamage(stack, super.getMaxDamage(stack));
    }

    public ItemStack construct(String main, String haft) {
        return CustomToolHelper.construct(this, main, haft);
    }

    @Override
    public EnumRarity getRarity(ItemStack item) {
        return CustomToolHelper.getRarity(item, itemRarity);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        return CustomToolHelper.getEfficiency(stack, super.getDestroySpeed(stack, state), efficiencyMod);
    }

    @Override
    public int getHarvestLevel(ItemStack stack, String toolClass, @Nullable EntityPlayer player, @Nullable IBlockState blockState) {
        return CustomToolHelper.getHarvestLevel(stack, super.getHarvestLevel(stack, toolClass, player, blockState));
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (isCustom) {
            ArrayList<CustomMaterial> metal = CustomMaterial.getList("metal");
            Iterator iteratorMetal = metal.iterator();
            while (iteratorMetal.hasNext()) {
                CustomMaterial customMat = (CustomMaterial) iteratorMetal.next();
                if (MineFantasyReborn.isDebug() || customMat.getItem() != null) {
                    items.add(this.construct(customMat.name, "OakWood"));
                }
            }
        } else {
            super.getSubItems(tab, items);
        }
    }

    @Override
    public void addInformation(ItemStack item, World world, List list, ITooltipFlag flag) {
        if (isCustom) {
            CustomToolHelper.addInformation(item, list);
        }
        super.addInformation(item, world, list, flag);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack item) {
        String unlocalName = this.getUnlocalizedNameInefficiently(item) + ".name";
        return CustomToolHelper.getLocalisedName(item, unlocalName);
    }
    // ====================================================== CUSTOM END
    // ==============================================================\\

}
