package minefantasy.mfr.item;

import minefantasy.mfr.api.weapon.IRackItem;
import minefantasy.mfr.client.render.item.RenderBigTool;
import minefantasy.mfr.config.ConfigTools;
import minefantasy.mfr.mechanics.StaminaMechanics;
import minefantasy.mfr.tile.TileEntityRack;
import minefantasy.mfr.util.ModelLoaderHelper;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class ItemLumberAxe extends ItemAxeMFR implements IRackItem {
	private final Random rand = new Random();

	public ItemLumberAxe(String name, Item.ToolMaterial material, int rarity) {
		super(name, material, rarity);
		this.setMaxDamage(getMaxDamage() * 5);
	}

	@Override
	public boolean onBlockDestroyed(ItemStack item, World world, IBlockState state, BlockPos pos, EntityLivingBase user) {
		if (!user.world.isRemote && user instanceof EntityPlayer && StaminaMechanics.canAcceptCost(user)) {
			breakChain(world, pos, item, state, user, 32, state);
		}
		return super.onBlockDestroyed(item, world, state, pos, user);
	}

	private void breakChain(World world, BlockPos pos, ItemStack item, IBlockState state, EntityLivingBase user, int maxLogs, IBlockState orient) {
		if (maxLogs > 0 && isLog(world, pos, orient)) {

			IBlockState newState = world.getBlockState(pos);
			breakSurrounding(item, world, pos, user);
			if (rand.nextFloat() * 100F < (100F - ConfigTools.heavy_tool_drop_chance)) {
				newState.getBlock().dropBlockAsItem(world, pos, world.getBlockState(pos), EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, item));
			}
			world.setBlockToAir(pos);
			item.damageItem(1, user);

			maxLogs--;
			for (int x1 = -1; x1 <= 1; x1++) {
				for (int y1 = -1; y1 <= 1; y1++) {
					for (int z1 = -1; z1 <= 1; z1++) {
						breakChain(world, pos.add(x1, y1, z1), item, newState, user, maxLogs, orient);
					}
				}
			}
			if (user instanceof EntityPlayer) {
				StaminaMechanics.tirePlayer((EntityPlayer) user, 0.5F);
			}
		}
	}

	private boolean isLog(World world, BlockPos pos, IBlockState orient) {
		IBlockState state = world.getBlockState(pos);
		return state == orient && state.getMaterial() == Material.WOOD;
	}

	public void breakSurrounding(ItemStack item, World world, BlockPos pos, EntityLivingBase user) {
		if (!world.isRemote && ForgeHooks.isToolEffective(world, pos, item)) {
			for (int x1 = -2; x1 <= 2; x1++) {
				for (int y1 = -2; y1 <= 2; y1++) {
					for (int z1 = -2; z1 <= 2; z1++) {
						EnumFacing facing = getFacingFor(user, pos);
						BlockPos blockPos = pos.add(x1 + facing.getFrontOffsetX(), y1 + facing.getFrontOffsetY(), z1 + facing.getFrontOffsetZ());

						if (!(x1 + facing.getFrontOffsetX() == 0 && y1 + facing.getFrontOffsetY() == 0 && z1 + facing.getFrontOffsetZ() == 0)) {

							IBlockState newState = world.getBlockState(blockPos);

							if (item.getItemDamage() < item.getMaxDamage() && user instanceof EntityPlayer && newState.getMaterial() == Material.LEAVES) {
								if (rand.nextFloat() * 100F < (100F - ConfigTools.heavy_tool_drop_chance)) {
									newState.getBlock().dropBlockAsItem(world, blockPos, newState, EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, item));
								}
								world.setBlockToAir(blockPos);
								item.damageItem(1, user);
							}
						}
					}
				}
			}
		}
	}

	private EnumFacing getFacingFor(EntityLivingBase user, BlockPos pos) {
		return EnumFacing.getDirectionFromEntityLiving(pos, user);// TODO: FD
	}

	@Override
	public float getScale(ItemStack itemstack) {
		return 2.0F;
	}

	@Override
	public float getOffsetX(ItemStack itemstack) {
		return 1.5F;
	}

	@Override
	public float getOffsetY(ItemStack itemstack) {
		return 1.8F;
	}

	@Override
	public float getOffsetZ(ItemStack itemstack) {
		return 0.05F;
	}

	@Override
	public float getRotationOffset(ItemStack itemstack) {
		return 90F;
	}

	@Override
	public boolean canHang(TileEntityRack rack, ItemStack item, int slot) {
		return true;
	}

	@Override
	public boolean flip(ItemStack itemStack) {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerClient() {
		ModelResourceLocation modelLocation = new ModelResourceLocation(getRegistryName(), "normal");
		ModelLoaderHelper.registerWrappedItemModel(this, new RenderBigTool(() -> modelLocation, 2F, -0.5F, -15, 0.26f), modelLocation);
	}
}
