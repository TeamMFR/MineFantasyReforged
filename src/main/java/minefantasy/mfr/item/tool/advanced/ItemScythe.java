package minefantasy.mfr.item.tool.advanced;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.api.helpers.CustomToolHelper;
import minefantasy.mfr.api.material.CustomMaterial;
import minefantasy.mfr.api.tier.IToolMaterial;
import minefantasy.mfr.api.weapon.IDamageType;
import minefantasy.mfr.api.weapon.IRackItem;
import minefantasy.mfr.block.tile.decor.TileEntityRack;
import minefantasy.mfr.farming.FarmingHelper;
import minefantasy.mfr.init.CreativeTabMFR;
import minefantasy.mfr.proxy.IClientRegister;
import minefantasy.mfr.util.ModelLoaderHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @author Anonymous Productions
 */
public class ItemScythe extends Item implements IToolMaterial, IDamageType, IRackItem, IClientRegister {
    protected int itemRarity;
    private Random rand = new Random();
    private ToolMaterial toolMaterial;
    private String name;
    private float baseDamage = 3.0F;
    // ===================================================== CUSTOM START
    // =============================================================\\
    private boolean isCustom = false;
    private float efficiencyMod = 1.0F;

    /**
     */
    public ItemScythe(String name, ToolMaterial material, int rarity) {
        this.toolMaterial = material;
        this.setFull3D();
        itemRarity = rarity;
        this.name = name;
        setRegistryName(name);
        setUnlocalizedName(name);

        setCreativeTab(CreativeTabMFR.tabOldTools);
        this.maxStackSize = 1;
        this.setMaxDamage(material.getMaxUses());
    }

    @Override
    public ToolMaterial getMaterial() {
        return toolMaterial;
    }

    private boolean cutGrass(World world, BlockPos pos, int r, EntityPlayer entity, boolean leaf) {
        boolean flag = false;
        ItemStack item = entity.getHeldItem(EnumHand.MAIN_HAND);
        if (item == null)
            return false;

        for (int x2 = -r; x2 <= r; x2++) {
            for (int y2 = -r; y2 <= r; y2++) {
                for (int z2 = -r; z2 <= r; z2++) {
                    Block block = world.getBlockState(pos.add(x2,y2,z2)).getBlock();
                    if (block != null) {
                        Material m = block.getMaterial(block.getDefaultState());
                        if (canCutMaterial(m, block.getBlockHardness(block.getDefaultState(), world, pos), leaf)) {
                            if (pos.getDistance(x2, y2, z2) < r * 1) {
                                flag = true;

                                List<ItemStack> items = block.getDrops(world, pos.add(x2, y2, z2), block.getDefaultState(), 1);
                                world.setBlockToAir(pos.add(x2,y2,z2));
                                world.playSound(entity,pos.add(x2,y2,z2), SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.AMBIENT,1.0F, 1.0F );
                                tryBreakFarmland(world, pos.add(x2,y2,z2));
                                if (!entity.capabilities.isCreativeMode) {
                                    ItemLumberAxe.tirePlayer(entity, 1F);
                                    for (ItemStack drop : items) {
                                        if (world.rand.nextFloat() <= 1.0F) {
                                            dropBlockAsItem_do(world, pos.add(x2, y2, z2), drop);
                                        }
                                    }
                                }
                                item.damageItem(1, entity);
                            }
                        }
                    }
                }
            }
        }
        return flag;
    }

    private void tryBreakFarmland(World world, BlockPos pos) {
        IBlockState base = world.getBlockState(pos);

        if (base != null && base == Blocks.FARMLAND && FarmingHelper.didHarvestRuinBlock(world, true)) {
            world.setBlockState(pos, (IBlockState) Blocks.DIRT);
        }
    }

    protected void dropBlockAsItem_do(World world, BlockPos pos, ItemStack drop) {
        if (!world.isRemote && world.getGameRules().getBoolean("doTileDrops")) {
            float var6 = 0.7F;
            double var7 = world.rand.nextFloat() * var6 + (1.0F - var6) * 0.5D;
            double var9 = world.rand.nextFloat() * var6 + (1.0F - var6) * 0.5D;
            double var11 = world.rand.nextFloat() * var6 + (1.0F - var6) * 0.5D;
            EntityItem var13 = new EntityItem(world, pos.getX() + var7, pos.getY() + var9, pos.getZ() + var11, drop);
            var13.setPickupDelay(10);
            world.spawnEntity(var13);
        }
    }

    private boolean canCutMaterial(Material m, float str, boolean leaf) {
        if (!leaf) {
            if (str <= 0.0F) {
                return m == Material.VINE || m == Material.PLANTS || m == Material.GRASS;
            } else
                return false;
        }
        return m == Material.LEAVES || m == Material.VINE;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack hoe = player.getHeldItem(hand);
        if (!player.canPlayerEdit(pos, facing, hoe) || !ItemLumberAxe.canAcceptCost(player)) {
            return EnumActionResult.FAIL;
        } else {
            Block block = world.getBlockState(pos).getBlock();

            if (block != null) {
                Material m = block.getMaterial(block.getDefaultState());
                float hard = block.getBlockHardness(block.getDefaultState(), world, pos);
                if (this.canCutMaterial(m, hard, false)) {
                    if (cutGrass(world, pos, 5, player, false)) {
                        player.swingArm(hand);
                    }
                } else if (this.canCutMaterial(m, hard, true)) {
                    if (cutGrass(world, pos, 3, player, true)) {
                        player.swingArm(hand);
                    }
                }
            }
        }
        return EnumActionResult.FAIL;
    }

    @Override
    public float[] getDamageRatio(Object... implement) {
        return new float[]{1, 0, 2};
    }

    @Override
    public float getPenetrationLevel(Object implement) {
        return 0F;
    }

    public ItemScythe setCustom(String s) {
        canRepair = false;
        isCustom = true;
        return this;
    }

    public ItemScythe setBaseDamage(float baseDamage) {
        this.baseDamage = baseDamage;
        return this;
    }

    public ItemScythe setEfficiencyMod(float efficiencyMod) {
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

//    @Override
//    @SideOnly(Side.CLIENT)
//    public int getColorFromItemStack(ItemStack item, int layer) {
//        return CustomToolHelper.getColourFromItemStack(item, layer, super.getColorFromItemStack(item, layer));
//    }

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



    public float getDigSpeed(ItemStack stack, Block block, World world, BlockPos pos, EntityPlayer player) {
        if (!ForgeHooks.isToolEffective(world, pos, stack)) {
            return this.getDestroySpeed(stack, block.getDefaultState());
        }
        float digSpeed = player.getDigSpeed(block.getDefaultState(), pos);
        return CustomToolHelper.getEfficiency(stack, digSpeed, efficiencyMod / 10);
    }

    @Override
    public int getHarvestLevel(ItemStack stack, String toolClass, @Nullable EntityPlayer player, @Nullable IBlockState blockState) {
        return CustomToolHelper.getHarvestLevel(stack, super.getHarvestLevel(stack, toolClass, player, blockState ));
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (!isInCreativeTab(tab)) {
            return;
        }
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

    @Override
    public float getScale(ItemStack itemstack) {
        return 2.0F;
    }

    @Override
    public float getOffsetX(ItemStack itemstack) {
        return 0;
    }

    @Override
    public float getOffsetY(ItemStack itemstack) {
        return 5F / 16F;
    }

    @Override
    public float getOffsetZ(ItemStack itemstack) {
        return 0;
    }

    @Override
    public float getRotationOffset(ItemStack itemstack) {
        return 0;
    }

    @Override
    public boolean canHang(TileEntityRack rack, ItemStack item, int slot) {
        return rack.hasRackBelow(slot);
    }

    @Override
    public boolean isSpecialRender(ItemStack item) {
        return false;
    }

    @Override
    public void registerClient() {
        ModelLoaderHelper.registerItem(this);
    }
}
