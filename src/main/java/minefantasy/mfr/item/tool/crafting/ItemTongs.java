package minefantasy.mfr.item.tool.crafting;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.api.heating.Heatable;
import minefantasy.mfr.api.heating.TongsHelper;
import minefantasy.mfr.api.helpers.CustomToolHelper;
import minefantasy.mfr.api.material.CustomMaterial;
import minefantasy.mfr.api.tier.IToolMaterial;
import minefantasy.mfr.api.tool.ISmithTongs;
import minefantasy.mfr.init.CreativeTabMFR;
import minefantasy.mfr.proxy.IClientRegister;
import minefantasy.mfr.util.ModelLoaderHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Anonymous Productions
 */
public class ItemTongs extends ItemTool implements IToolMaterial, ISmithTongs, IClientRegister {
    protected int itemRarity;
    private ToolMaterial material;
    private float baseDamage;
    private String name;
    // ===================================================== CUSTOM START
    // =============================================================\\
    private boolean isCustom = false;
    private float efficiencyMod = 1.0F;

    public ItemTongs(String name, ToolMaterial material, int rarity) {
        super(0F, 1.0F, material, Sets.newHashSet(new Block[]{}));
        this.material = material;
        itemRarity = rarity;
        this.name = name;
        setCreativeTab(CreativeTabMFR.tabOldTools);
        this.setMaxDamage(getMaxDamage() / 5);
        setRegistryName(name);
        setUnlocalizedName(name);

        MineFantasyReborn.PROXY.addClientRegister(this);
    }

    @Override
    public ToolMaterial getMaterial() {
        return toolMaterial;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack item = player.getHeldItem(hand);
        RayTraceResult movingobjectposition = this.rayTrace(world, player, true);

        if (movingobjectposition == null) {
            return ActionResult.newResult(EnumActionResult.PASS, item);
        } else {
            if (movingobjectposition.typeOfHit == RayTraceResult.Type.BLOCK) {
                BlockPos pos = movingobjectposition.getBlockPos();

                if (!world.canMineBlockBody(player, pos)) {
                    return ActionResult.newResult(EnumActionResult.PASS, item);
                }

                if (!player.canPlayerEdit(pos, movingobjectposition.sideHit, item)) {
                    return ActionResult.newResult(EnumActionResult.PASS, item);
                }

                float water = TongsHelper.getWaterSource(world, pos);

                if (TongsHelper.getHeldItem(item) != null && water >= 0) {
                    ItemStack drop = TongsHelper.getHeldItem(item).copy(), cooled = drop;

                    if (TongsHelper.isCoolableItem(drop)) {
                        cooled = Heatable.getQuenchedItem(drop, water);
                        cooled.setCount(drop.getCount());

                        player.playSound(SoundEvents.ENTITY_GENERIC_SPLASH, 1F, 1F);
                        player.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 2F, 0.5F);

                        for (int a = 0; a < 5; a++) {
                            world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.getX() + 0.5F, pos.getY() + 1, pos.getZ() + 0.5F, 0, 0.065F, 0);
                        }
                    }
                    if (cooled != null && !world.isRemote) {
                        if (world.isAirBlock(pos.add(0,1,0))) {
                            EntityItem entity = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, cooled);
                            entity.setPickupDelay(20);
                            entity.motionX = entity.motionY = entity.motionZ = 0F;
                            world.spawnEntity(entity);
                        } else {
                            player.entityDropItem(cooled, 0);
                        }
                    }

                    return ActionResult.newResult(EnumActionResult.PASS, TongsHelper.clearHeldItem(item, player));
                }
            }

            return ActionResult.newResult(EnumActionResult.FAIL, item);
        }
    }

    public ItemTongs setCustom(String s) {
        canRepair = false;
        isCustom = true;
        return this;
    }

    public ItemTongs setBaseDamage(float baseDamage) {
        this.baseDamage = baseDamage;
        return this;
    }

    public ItemTongs setEfficiencyMod(float efficiencyMod) {
        this.efficiencyMod = efficiencyMod;
        return this;
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        if (slot != EntityEquipmentSlot.MAINHAND) {
            return super.getAttributeModifiers(slot, stack);
        }

        Multimap map = HashMultimap.create();
        map.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(),
                new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", getMeleeDamage(stack), 0));

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

    public ItemStack construct(String main) {
        return CustomToolHelper.construct(this, main, null);
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
    public int getHarvestLevel(ItemStack stack, String toolClass, EntityPlayer player, IBlockState state) {
        return CustomToolHelper.getHarvestLevel(stack, super.getHarvestLevel(stack, toolClass, player, state));
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
                    items.add(this.construct(customMat.name));
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
        } else {

            ItemStack held = TongsHelper.getHeldItem(item);
            if (held != null) {
                list.add("");
                list.add(held.getItem().getItemStackDisplayName(held));
                held.getItem().addInformation(held, world, list, flag);
            }
        }

        super.addInformation(item, world, list, flag);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack item) {
        String unlocalName = this.getUnlocalizedNameInefficiently(item) + ".name";
        return CustomToolHelper.getLocalisedName(item, unlocalName);
    }

    @Override
    public boolean hitEntity(ItemStack item, EntityLivingBase target, EntityLivingBase user) {
        return true;
    }
    // ====================================================== CUSTOM END
    // ==============================================================\\

    @Override
    public void registerClient() {
        ModelLoaderHelper.registerItem(this);
    }
}
