package minefantasy.mfr.block;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.init.MineFantasyTabs;
import minefantasy.mfr.material.CustomMaterial;
import minefantasy.mfr.proxy.IClientRegister;
import minefantasy.mfr.util.CustomToolHelper;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockRepairKit extends Block implements IClientRegister {
	public float repairLevel;
	public float successRate;
	public float breakChance;
	public boolean isOrnate = false;
	public float repairLevelEnchant = 0.0F;
	private Random rand = new Random();
	AxisAlignedBB BlockBB = new AxisAlignedBB(1F / 16F, 0F, 1F / 16F, 15F / 16F, 6F / 16F, 15F / 16F);

	public BlockRepairKit(String name, float repairLevel, float rate, float breakChance) {
		super(Material.CLOTH);

		this.repairLevel = repairLevel;
		this.successRate = rate;
		this.breakChance = breakChance;
		name = "repair_kit_" + name;

		setRegistryName(name);
		setUnlocalizedName(name);
		this.setSoundType(SoundType.CLOTH);
		this.setHardness(1F);
		this.setResistance(0F);
		this.setLightOpacity(0);
		this.setCreativeTab(MineFantasyTabs.tabGadget);
		MineFantasyReforged.PROXY.addClientRegister(this);
	}

	public BlockRepairKit setOrnate(float enc) {
		repairLevelEnchant = enc;
		isOrnate = true;
		return this;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return BlockBB;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer user, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (world.isRemote) {
			return true;
		}
		ItemStack held = user.getHeldItem(hand);
		// held.getItem().isRepairable() Was used but new MF tools disable this to avoid
		// vanilla repairs
		if (!held.isEmpty() && canRepair(held) && (!held.isItemEnchanted() || isOrnate)) {
			if (rand.nextFloat() < successRate) {
				boolean broken = rand.nextFloat() < breakChance;

				float lvl = held.isItemEnchanted() ? repairLevelEnchant : repairLevel;
				int repairAmount = (int) (held.getMaxDamage() * lvl);
				held.setItemDamage(Math.max(0, held.getItemDamage() - repairAmount));
				world.playEvent(broken ? 1029 : 1030, pos, 0);

				if (broken) {
					world.playSound(user, pos, SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.AMBIENT, 1.0F, 1.0F);
					world.setBlockToAir(pos);
				}
				return true;
			} else {
				world.playSound(null, pos, SoundEvents.BLOCK_CLOTH_STEP, SoundCategory.AMBIENT, 0.5F, 0.5F);
			}
			return true;
		}
		return false;
	}

	private boolean canRepair(ItemStack held) {
		if (held.isEmpty())
			return false;
		if (held.getItem().isDamageable() && CustomToolHelper.getCustomPrimaryMaterial(held) != CustomMaterial.NONE)// Custom Tool
		{
			return held.isItemDamaged();
		}
		return held.getItem().isRepairable();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerClient() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "normal"));
	}
}
