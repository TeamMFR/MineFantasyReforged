package minefantasy.mfr.tile;

import minefantasy.mfr.api.crafting.IBasicMetre;
import minefantasy.mfr.api.crafting.IHeatSource;
import minefantasy.mfr.api.crafting.IHeatUser;
import minefantasy.mfr.api.helpers.CustomToolHelper;
import minefantasy.mfr.api.helpers.Functions;
import minefantasy.mfr.api.rpg.RPGElements;
import minefantasy.mfr.api.rpg.SkillList;
import minefantasy.mfr.block.crafting.BlockFirepit;
import minefantasy.mfr.container.ContainerBase;
import minefantasy.mfr.init.ComponentListMFR;
import minefantasy.mfr.init.FoodListMFR;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Random;

public class TileEntityFirepit extends TileEntityBase implements ITickable, IBasicMetre, IHeatSource {
	private boolean isLit;
	private final int maxFuel = 12000; // 10 minutes
	public int fuel = 0;
	private float charcoal = 0;
	private int ticksExisted;
	private Random rand = new Random();

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
	{
		return oldState.getBlock() != newState.getBlock();
	}


	/**
	 * Gets the burn time
	 * <p>
	 * Wood tools and plank item are 1 minute sticks and saplings are 30seconds
	 */
	public static int getItemBurnTime(ItemStack input) {
		if (input == null) {
			return 0;
		} else {
			Item i = input.getItem();
			if (i == Items.STICK)
				return 600;// 30Sec
			if (i == ComponentListMFR.TIMBER || i == ComponentListMFR.TIMBER_CUT) {
				return (int) (200 * CustomToolHelper.getBurnModifier(input));
			}
			if (i == ComponentListMFR.TIMBER_PANE) {
				return (int) (600 * CustomToolHelper.getBurnModifier(input));
			}

			return 0;
		}
	}

	@Override
	public void update() {
		if (world.isRemote) {
			if (isBurning()) {
				fuel--;
			}
			return;
		}

		++ticksExisted;
		if (isLit()) {
			if (isWet()) {
				extinguish();
				return;
			}

			if (fuel > 0) {
				fuel--;
				charcoal += (0.25F / 20F / 60F);
			}

			if (fuel <= 0) {
				setLit(false);
			}
		} else if (fuel > 0 && ticksExisted % 10 == 0) {
			tryLight();
		}
	}

	private boolean isWet() {
		if (isWater(-1, 0, 0) || isWater(1, 0, 0) || isWater(0, 0, -1) || isWater(0, 0, 1) || isWater(0, 1, 0)) {
			return true;
		}

		return world.isRainingAt(pos.add(0, 1, 0));
	}

	public boolean isBurning() {
		return isLit() && fuel > 0;
	}

	public boolean isLit() {
		IBlockState state = world.getBlockState(pos);
		BlockFirepit firepit = getActiveBlock();
		return firepit.getBurningValue(state);
	}

	public void setLit(boolean lit) {
		BlockFirepit.setActiveState(lit, fuel > 0, hasBlockAbove(), world, pos);
		isLit = lit;
		ticksExisted = 0;
	}

	public BlockFirepit getActiveBlock() {
		if (world == null)
			return null;

		Block block = world.getBlockState(pos).getBlock();

		if (block instanceof BlockFirepit) {
			return (BlockFirepit) block;
		}
		return null;
	}

	private void tryLight() {
		if (isFire(-1, 0, 0) || isFire(1, 0, 0) || isFire(0, 0, -1) || isFire(0, 0, 1) || isFire(0, -1, 0) || isFire(0, 1, 0)) {
			setLit(true);
		}
	}

	private boolean isFire(int x, int y, int z) {
		return world.getBlockState(pos.add(x, y, z)).getMaterial() == Material.FIRE;
	}

	private boolean isWater(int x, int y, int z) {
		return world.getBlockState(pos.add(x, y, z)).getMaterial() == Material.WATER;
	}

	public boolean addFuel(ItemStack input) {
		int amount = getItemBurnTime(input);
		if (amount > 0 && fuel < maxFuel) {
			fuel += amount;
			if (fuel > maxFuel) {
				fuel = maxFuel;
			}
			BlockFirepit.setActiveState(isBurning(),fuel > 0, hasBlockAbove(), world, pos);
			return true;
		}
		return false;
	}

	@Override
	public boolean canPlaceAbove() {
		return true;
	}

	@Override
	public int getHeat() {
		if (!isBurning()) {
			return 0;
		}
		return Functions.getIntervalWave1_i(ticksExisted, 30, 100, 200);// 100*-300*
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldShowMetre() {
		return fuel > 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getMetreScale(int width) {
		return this.fuel * width / (maxFuel);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getLocalisedName() {
		int seconds = (int) Math.floor(fuel / 20F);
		int mins = (int) Math.floor(seconds / 60F);
		seconds -= mins * 60;

		String s = "";
		if (seconds < 10) {
			s += "0";
		}

		return "150C " + I18n.format("forge.fuel.name") + " " + mins + ":" + s + seconds;
	}

	public void extinguish() {
		world.playSound(pos.getY() + 0.5F, pos.getY() + 0.25F, pos.getZ() + 0.5F, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.AMBIENT, 0.4F, 2.0F + this.rand.nextFloat() * 0.4F, true);
		world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
		world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);

		setLit(false);
		BlockFirepit.setActiveState(isBurning(), fuel > 0, hasBlockAbove(), world, pos);
	}

	public boolean tryCook(EntityPlayer player, ItemStack held) {
		if (held.getItem() instanceof ItemFood) {
			player.swingArm(EnumHand.MAIN_HAND);
			if (world.isRemote) {
				return false;
			}
			ItemStack result = FurnaceRecipes.instance().getSmeltingResult(held);
			if (!result.isEmpty()) {
				if (rand.nextInt(10) != 0) {
					return false;
				}
				float chance = 75F;
				if (RPGElements.isSystemActive) {
					int skill = RPGElements.getLevel(player, SkillList.provisioning);
					chance += (int) ((float) skill / 4);
				}
				boolean success = (rand.nextFloat() * 100) < chance;
				ItemStack creation = success ? result.copy() : new ItemStack(FoodListMFR.BURNT_FOOD);
				dropItem(player, creation);
				SkillList.provisioning.addXP(player, success ? 2 : 1);
				return true;
			}
		}
		return false;
	}

	public void dropItem(EntityPlayer player, ItemStack item) {
		EntityItem drop = new EntityItem(world, player.posX, player.posY, player.posZ, item);
		drop.setPickupDelay(10);
		drop.motionX = drop.motionY = drop.motionZ = 0;
		world.spawnEntity(drop);
	}

	public boolean hasBlockAbove() {
		if (world == null)
			return false;

		TileEntity tile = world.getTileEntity(pos.add(0, 1, 0));
		if (tile != null && tile instanceof IHeatUser) {
			return ((IHeatUser) tile).canAccept(this);
		}

		return false;
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("fuel", fuel);
		return new SPacketUpdateTileEntity(pos, 0, tag);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
		fuel = packet.getNbtCompound().getInteger("fuel");
	}

	@Override
	public final NBTTagCompound getUpdateTag(){
		return this.writeToNBT(new NBTTagCompound());
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("fuel", fuel);
		nbt.setInteger("ticksExisted", ticksExisted);
		nbt.setBoolean("isLit", isLit);
		nbt.setFloat("charcoal", charcoal);
		return nbt;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		fuel = nbt.getInteger("fuel");
		ticksExisted = nbt.getInteger("ticksExisted");
		isLit = nbt.getBoolean("isLit");
		charcoal = nbt.getFloat("charcoal");
	}

	@Override
	protected ItemStackHandler createInventory() {
		return null;
	}

	@Override
	public ItemStackHandler getInventory() {
		return null;
	}

	@Override
	public ContainerBase createContainer(EntityPlayer player) {
		return null;
	}

	@Override
	protected int getGuiId() {
		return 0;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack item) {
		return false;
	}
}
