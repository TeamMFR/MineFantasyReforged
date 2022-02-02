package minefantasy.mfr.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.api.tier.IToolMaterial;
import minefantasy.mfr.init.MineFantasyMaterials;
import minefantasy.mfr.init.MineFantasyTabs;
import minefantasy.mfr.material.CustomMaterial;
import minefantasy.mfr.proxy.IClientRegister;
import minefantasy.mfr.util.CustomToolHelper;
import minefantasy.mfr.util.ModelLoaderHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Anonymous Productions
 */
public class ItemPickMFR extends ItemPickaxe implements IToolMaterial, IClientRegister {
	protected int itemRarity;
	private float baseDamage = 2F;
	// ===================================================== CUSTOM START
	// =============================================================\\
	private boolean isCustom = false;
	private float efficiencyMod = 1.0F;

	public ItemPickMFR(String name, ToolMaterial material, int rarity) {
		super(material);
		itemRarity = rarity;
		setCreativeTab(MineFantasyTabs.tabOldTools);
		setRegistryName(name);
		setUnlocalizedName(name);

		MineFantasyReforged.PROXY.addClientRegister(this);
	}

	@Override
	public ToolMaterial getMaterial() {
		return toolMaterial;
	}

	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack item = player.getHeldItemMainhand();

		if (!world.isRemote) {
			return super.onItemRightClick(world, player, hand);
		}

		if (!player.getHeldItemOffhand().isEmpty()) {
			return super.onItemRightClick(world, player, hand);
		}

		RayTraceResult rayTraceResult = this.rayTrace(world, player, true);

		if (rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) {

			if (!world.canMineBlockBody(player, rayTraceResult.getBlockPos())) {
				return super.onItemRightClick(world, player, hand);
			}

			if (!player.canPlayerEdit(rayTraceResult.getBlockPos(), rayTraceResult.sideHit, item)) {
				return super.onItemRightClick(world, player, hand);
			}

			Block block = world.getBlockState(rayTraceResult.getBlockPos()).getBlock();
			int blockTier = block.getHarvestLevel(world.getBlockState(rayTraceResult.getBlockPos()));

			int harvestLevel = CustomToolHelper.getHarvestLevel(item, toolMaterial.getHarvestLevel());
			if (blockTier > harvestLevel) {
				String msg = I18n.format("prospect.cannotmine", harvestLevel, blockTier);
				player.sendMessage(new TextComponentString(TextFormatting.RED + msg));
			} else {
				String msg = I18n.format("prospect.canmine", harvestLevel, blockTier);
				player.sendMessage(new TextComponentString(TextFormatting.GREEN + msg));
			}
		}

		return super.onItemRightClick(world, player, hand);
	}

	public ItemPickMFR setCustom(String s) {
		canRepair = false;
		isCustom = true;
		return this;
	}

	public ItemPickMFR setBaseDamage(float baseDamage) {
		this.baseDamage = baseDamage;
		return this;
	}

	public ItemPickMFR setEfficiencyMod(float efficiencyMod) {
		this.efficiencyMod = efficiencyMod;
		return this;
	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		if (slot != EntityEquipmentSlot.MAINHAND) {
			return super.getAttributeModifiers(slot, stack);
		}

		Multimap<String, AttributeModifier> map = HashMultimap.create();
		map.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", baseDamage, 0));
		map.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.8F, 0));
		return map;
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
		if (!isInCreativeTab(tab)) {
			return;
		}
		if (isCustom) {
			ArrayList<CustomMaterial> metal = CustomMaterial.getList("metal");
			for (CustomMaterial customMat : metal) {
				if (MineFantasyReforged.isDebug() || !customMat.getItemStack().isEmpty()) {
					items.add(this.construct(customMat.name, MineFantasyMaterials.Names.OAK_WOOD));
				}
			}
		} else {
			super.getSubItems(tab, items);
		}
	}

	@Override
	public void addInformation(ItemStack item, World world, List<String> list, ITooltipFlag flag) {
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

	@Override
	@SideOnly(Side.CLIENT)
	public void registerClient() {
		ModelLoaderHelper.registerItem(this);
	}

	// ====================================================== CUSTOM END
	// ==============================================================\\
}
