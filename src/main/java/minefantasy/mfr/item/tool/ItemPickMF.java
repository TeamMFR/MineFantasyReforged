package minefantasy.mfr.item.tool;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.api.helpers.CustomToolHelper;
import minefantasy.mfr.api.material.CustomMaterial;
import minefantasy.mfr.api.tier.IToolMaterial;
import minefantasy.mfr.init.CreativeTabMFR;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
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
public class ItemPickMF extends ItemPickaxe implements IToolMaterial {
    protected int itemRarity;
    private String name;
    private float baseDamage = 2F;
    // ===================================================== CUSTOM START
    // =============================================================\\
    private boolean isCustom = false;
    private float efficiencyMod = 1.0F;

    public ItemPickMF(String name, ToolMaterial material, int rarity) {
        super(material);
        itemRarity = rarity;
        setCreativeTab(CreativeTabMFR.tabOldTools);
        this.name = name;
        setRegistryName(name);
        setUnlocalizedName(MineFantasyReborn.MOD_ID + "." + name);
        GameRegistry.findRegistry(Item.class).register(this);
    }

    @Override
    public ToolMaterial getMaterial() {
        return toolMaterial;
    }

    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
        if (!world.isRemote)
            return item;

        RayTraceResult movingobjectposition = this.rayTrace(world, player, true);

        if (movingobjectposition == null) {
            return item;
        } else {
            if (movingobjectposition.typeOfHit == RayTraceResult.Type.BLOCK) {

                if (!world.canMineBlockBody(player, movingobjectposition.getBlockPos())) {
                    return item;
                }

                if (!player.canPlayerEdit(movingobjectposition.getBlockPos(), movingobjectposition.sideHit, item)) {
                    return item;
                }

                Block block = world.getBlockState(movingobjectposition.getBlockPos()).getBlock();
                int blockTier = block.getHarvestLevel(world.getBlockState(movingobjectposition.getBlockPos()));

                int HL = CustomToolHelper.getHarvestLevel(item, toolMaterial.getHarvestLevel());
                if (blockTier > HL) {
                    String msg = I18n.translateToLocalFormatted("prospect.cannotmine", HL, blockTier);
                    player.sendMessage(new TextComponentString(TextFormatting.RED + msg));
                } else {
                    String msg = I18n.translateToLocalFormatted("prospect.canmine", HL, blockTier);
                    player.sendMessage(new TextComponentString(TextFormatting.GREEN + msg));
                }
            }

            return item;
        }
    }

    private boolean canMineBlock(World world, int i, int j, int k) {
        // TODO Auto-generated method stub
        return false;
    }

    public ItemPickMF setCustom(String s) {
        canRepair = false;
        isCustom = true;
        return this;
    }

    public ItemPickMF setBaseDamage(float baseDamage) {
        this.baseDamage = baseDamage;
        return this;
    }

    public ItemPickMF setEfficiencyMod(float efficiencyMod) {
        this.efficiencyMod = efficiencyMod;
        return this;
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack item) {
        Multimap map = HashMultimap.create();
        map.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(),
                new AttributeModifier(UUID.fromString(slot.getName()), "Weapon modifier", getMeleeDamage(item), 0));

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

    public float getDigSpeed(ItemStack stack, Block block, World world, BlockPos pos, EntityPlayer player) {
        if (!ForgeHooks.isToolEffective(world, pos, stack)) {
            return this.getDestroySpeed(stack, block);
        }
        float digSpeed = player.getDigSpeed(block.getDefaultState(), pos);
        return CustomToolHelper.getEfficiency(stack, digSpeed, efficiencyMod / 10);
    }

    public float getDestroySpeed(ItemStack stack, Block block) {
        return block.getMaterial(block.getDefaultState()) != Material.IRON && block.getMaterial(block.getDefaultState()) != Material.ANVIL
                && block.getMaterial(block.getDefaultState()) != Material.ROCK ? super.getDestroySpeed(stack, block.getDefaultState())
                : CustomToolHelper.getEfficiency(stack, this.efficiency, efficiencyMod / 2);
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
            super.getSubItems( tab, items);
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
