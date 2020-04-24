package minefantasy.mfr.item.tool.advanced;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.api.helpers.CustomToolHelper;
import minefantasy.mfr.api.material.CustomMaterial;
import minefantasy.mfr.api.mining.RandomDigs;
import minefantasy.mfr.api.tier.IToolMaterial;
import minefantasy.mfr.init.CreativeTabMFR;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
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
public class ItemTrowMF extends ItemSpade implements IToolMaterial {
    protected int itemRarity;
    private String name;
    private float baseDamage = 1F;
    private Random rand = new Random();
    // ===================================================== CUSTOM START
    // =============================================================\\
    private boolean isCustom = false;
    private float efficiencyMod = 1.0F;
    public ItemTrowMF(String name, ToolMaterial material, int rarity) {
        super(material);
        itemRarity = rarity;
        setCreativeTab(CreativeTabMFR.tabOldTools);
        this.name = name;
        setRegistryName(name);
        setUnlocalizedName(MineFantasyReborn.MOD_ID + "." + name);
        GameRegistry.findRegistry(Item.class).register(this);
        setMaxDamage(material.getMaxUses());
    }

    @Override
    public boolean onBlockDestroyed(ItemStack item, World world, IBlockState state, BlockPos pos, EntityLivingBase user) {
        alwaysDropFlint(state.getBlock(), item, world, user, pos);

        if (!world.isRemote) {
            int harvestlvl = this.getMaterial().getHarvestLevel();
            int fortune = EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(35), item);
            boolean silk = EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(33), item) == 1;

            ArrayList<ItemStack> specialdrops = RandomDigs.getDroppedItems(state.getBlock(), state.getBlock().getMetaFromState(state), harvestlvl, fortune, silk, pos.getY());

            if (specialdrops != null && !specialdrops.isEmpty()) {
                Iterator list = specialdrops.iterator();

                while (list.hasNext()) {
                    ItemStack newdrop = (ItemStack) list.next();

                    if (newdrop != null) {
                        if (newdrop.getCount() < 1)
                            newdrop.setCount(1);

                        dropItem(world, pos, newdrop);
                    }
                }
            }
        }
        return super.onBlockDestroyed(item, world, state, pos, user);
    }

    private void alwaysDropFlint(Block block, ItemStack item, World world, EntityLivingBase user, BlockPos pos) {
        if (block == Blocks.GRAVEL) {
            world.setBlockToAir(pos);
            int loot = 0;
            int enc = EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(35), item);
            if (enc > 0) {
                loot = rand.nextInt(enc);
            }
            dropItem(world, pos, new ItemStack(Items.FLINT, 1 + loot));
        }
    }

    private void dropItem(World world, BlockPos pos, ItemStack drop) {
        if (world.isRemote)
            return;

        EntityItem dropItem = new EntityItem(world, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, drop);
        dropItem.setPickupDelay(10);
        world.spawnEntity(dropItem);
    }

    @Override
    public ToolMaterial getMaterial() {
        return toolMaterial;
    }

    public ItemTrowMF setCustom(String s) {
        canRepair = false;
        isCustom = true;
        return this;
    }

    public ItemTrowMF setBaseDamage(float baseDamage) {
        this.baseDamage = baseDamage;
        return this;
    }

    public ItemTrowMF setEfficiencyMod(float efficiencyMod) {
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
        return CustomToolHelper.getMaxDamage(stack, super.getMaxDamage(stack)) / 2;
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
