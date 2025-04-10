package minefantasy.mfr.data;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.item.ItemMultiFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class CapabilityItemMultiUse {
	@CapabilityInject(CapabilityItemMultiUse.class)
	public static final Capability<CapabilityItemMultiUse> ITEM_MULTI_USE_CAPABILITY = null;

	public static final EnumFacing DEFAULT_FACING = null;

	private int currentBites;

	public CapabilityItemMultiUse() {

	}

	public static void register() {
		CapabilityManager.INSTANCE.register(CapabilityItemMultiUse.class, new Capability.IStorage<CapabilityItemMultiUse>() {
			@Override
			public NBTBase writeNBT(final Capability<CapabilityItemMultiUse> capability, final CapabilityItemMultiUse instance, final EnumFacing side) {
				final NBTTagCompound tagCompound = new NBTTagCompound();
				if (instance instanceof CapabilityItemMultiUse) {
					tagCompound.setInteger("bites", instance.getCurrentBites());
				}
				return tagCompound;
			}

			@Override
			public void readNBT(final Capability<CapabilityItemMultiUse> capability, final CapabilityItemMultiUse instance, final EnumFacing side, final NBTBase nbt) {
				if (instance instanceof CapabilityItemMultiUse) {
					final NBTTagCompound tagCompound = (NBTTagCompound) nbt;

					instance.setCurrentBites(tagCompound.getInteger("bites"));
				}
			}
		}, CapabilityItemMultiUse::new);

	}

	public int getCurrentBites() {
		return currentBites;
	}

	public static int getCurrentBites(ItemStack stack) {
		CapabilityItemMultiUse capability = getCapability(stack, ITEM_MULTI_USE_CAPABILITY, DEFAULT_FACING);
		return capability == null ? 0 : capability.getCurrentBites();
	}

	public void setCurrentBites(int currentBites) {
		this.currentBites = currentBites;
	}

	public static void setCurrentBites(ItemStack stack, int currentBites) {
		CapabilityItemMultiUse capability = getCapability(stack, ITEM_MULTI_USE_CAPABILITY, DEFAULT_FACING);
		if (capability != null) {
			capability.setCurrentBites(currentBites);
		}
	}

	// ============================================== Event Handlers ==============================================

	@SubscribeEvent
	public static void onCapabilityLoad(AttachCapabilitiesEvent<ItemStack> event) {
		if (event.getObject().getItem() instanceof ItemMultiFood) {
			event.addCapability(new ResourceLocation(MineFantasyReforged.MOD_ID, "ItemMultiUse"), new Provider());
		}
	}

	@Nullable
	public static <T> T getCapability(@Nullable final ICapabilityProvider provider, final Capability<T> capability, @Nullable final EnumFacing facing) {
		return provider != null && provider.hasCapability(capability, facing) ? provider.getCapability(capability, facing) : null;
	}

	// ========================================== Capability Boilerplate ==========================================

	/**
	 * This is a nested class for a few reasons: firstly, it makes sense because instances of this and PlayerData go
	 * hand-in-hand; secondly, it's too short to be worth a separate file; and thirdly (and most importantly) it allows
	 * access to PLAYER_DATA_CAPABILITY while keeping it private.
	 */
	public static class Provider implements ICapabilitySerializable<NBTBase> {

		/**
		 * The {@link Capability} instance to provide the handler for.
		 */
		protected final Capability<CapabilityItemMultiUse> capability;

		/**
		 * The {@link EnumFacing} to provide the handler for.
		 */
		protected final EnumFacing facing;

		private final CapabilityItemMultiUse instance;

		public Provider() {
			this.capability = ITEM_MULTI_USE_CAPABILITY;
			this.instance = new CapabilityItemMultiUse();
			this.facing = null;
		}

		public Provider(final CapabilityItemMultiUse capability) {
			this.capability = ITEM_MULTI_USE_CAPABILITY;
			this.instance = capability;
			this.facing = null;
		}

		@Override
		public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
			return capability == ITEM_MULTI_USE_CAPABILITY;
		}

		@Override
		public <T> T getCapability(Capability<T> capability, EnumFacing facing) {

			if (capability == ITEM_MULTI_USE_CAPABILITY) {
				return ITEM_MULTI_USE_CAPABILITY.cast(instance);
			}

			return null;
		}

		@Nullable
		@Override
		public NBTBase serializeNBT() {
			return capability.writeNBT(instance, facing);
		}

		@Override
		public void deserializeNBT(final NBTBase nbt) {
			capability.readNBT(instance, facing, nbt);
		}

	}
}
