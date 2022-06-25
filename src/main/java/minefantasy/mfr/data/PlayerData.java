package minefantasy.mfr.data;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.mechanics.RPGElements;
import minefantasy.mfr.mechanics.knowledge.ResearchLogic;
import minefantasy.mfr.network.NetworkHandler;
import minefantasy.mfr.network.PlayerDataPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author/Inspiration Credit: Electroblob. Used with permission, thank you Electroblob!
 **/

@Mod.EventBusSubscriber
public class PlayerData implements INBTSerializable<NBTTagCompound> {

	@CapabilityInject(PlayerData.class)
	private static final Capability<PlayerData> PLAYER_DATA_CAPABILITY = null;

	/**
	 * The player this PlayerData instance belongs to.
	 */
	private final EntityPlayer player;

	/**
	 * Internal storage of registered variable keys. This only contains the stored keys.
	 **/
	private static final List<IStoredVariable> storedVariables = new ArrayList<>();

	/**
	 * Internal storage of custom data. Note that a {@code Map} cannot specify that its values are of
	 * the same type as the type parameter of its keys, so to ensure this condition always holds, the map must only
	 * be modified via {@link PlayerData#setVariable(IVariable, Object)}, which (as a method) is able to enforce it.
	 **/
	private final Map<IVariable, Object> playerData;

	public PlayerData() {
		this(null);
	}

	public PlayerData(EntityPlayer player) {
		this.player = player;
		this.playerData = new HashMap<>();
	}

	/**
	 * Called from preInit in the main mod class to register the PlayerData capability.
	 */
	public static void register() {
		CapabilityManager.INSTANCE.register(PlayerData.class, new IStorage<PlayerData>() {
			@Override
			public NBTBase writeNBT(Capability<PlayerData> capability, PlayerData instance, EnumFacing side) {
				return null;
			}

			@Override
			public void readNBT(Capability<PlayerData> capability, PlayerData instance, EnumFacing side, NBTBase nbt) {}

		}, PlayerData::new);
	}

	public static PlayerData get(EntityPlayer player) {
		return player.getCapability(PLAYER_DATA_CAPABILITY, null);
	}

	// ============================================= Variable Storage =============================================

	/**
	 * Registers the given {@link IStoredVariable} objects as keys that will be stored to NBT for each {@code PlayerData}
	 * instance.
	 */
	public static void registerStoredVariables(IStoredVariable<?>... variables) {
		storedVariables.addAll(Arrays.asList(variables));
	}

	/**
	 * Returns a set containing the registered {@link IStoredVariable} objects for which {@link IVariable#isSynced()}
	 * returns true. Used internally for packet reading.
	 */
	public static List<IVariable> getSyncedVariables() {
		return storedVariables.stream().filter(IVariable::isSynced).collect(Collectors.toList());
	}

	/**
	 * Stores the given value under the given key in this {@code PlayerData} object.
	 *
	 * @param variable The key under which the value is to be stored. See {@link IVariable} for more details.
	 * @param value    The value to be stored.
	 * @param <T>      The type of the value to be stored. Note that the given variable (key) may be of a supertype of the
	 *                 stored value itself; however, when the value is retrieved its type will match that of the key. In
	 *                 other words, if an {@code Integer} is stored under a {@code IVariable<Number>}, a {@code Number} will
	 *                 be returned when the value is retrieved.
	 */
	// This use of type parameters guarantees that data may only be stored (and therefore may only be accessed)
	// using a compatible key. For instance, the following code will not compile:
	// Number i = 1;
	// setVariable(StoredVariable.ofInt("key", Persistence.ALWAYS), i);
	public <T> void setVariable(IVariable<? super T> variable, T value) {
		this.playerData.put(variable, value);
	}

	/**
	 * Returns the value stored under the given key in this {@code PlayerData} object, or null if the key was not
	 * stored.
	 *
	 * @param variable The key whose associated value is to be returned.
	 * @param <T>      The type of the returned value.
	 * @return The value associated with the given key, or null no such key was stored. <i>Beware of auto-unboxing
	 * of primitive types! Directly assigning the result to a primitive type, as in {@code int i = getVariable(...)},
	 * will cause a {@link NullPointerException} if the key was not stored.</i>
	 */
	@SuppressWarnings("unchecked") // The playerData map is fully encapsulated so we can be sure that the cast is safe
	@Nullable
	public <T> T getVariable(IVariable<T> variable) {
		return (T) playerData.get(variable);
	}

	// ============================================== Data Handling ==============================================

	/**
	 * Called each time the associated player is updated.
	 */
	@SuppressWarnings("unchecked") // Again, we know it must be ok
	private void update() {

		this.playerData.forEach((k, v) -> this.playerData.put(k, k.update(player, v)));
		this.playerData.keySet().removeIf(k -> k.canPurge(player, this.playerData.get(k)));
	}

	/**
	 * Called from the event handler each time the associated player entity is cloned, i.e. on respawn or when
	 * travelling to a different dimension. Used to copy over any data that should persist over player death. This
	 * is the inverse of the old onPlayerDeath method, which reset the data that shouldn't persist.
	 *
	 * @param data    The old PlayerData whose data is to be copied over.
	 * @param respawn True if the player died and is respawning, false if they are just travelling between dimensions.
	 */
	public void copyFrom(PlayerData data, boolean respawn) {

		for (IVariable variable : data.playerData.keySet()) {
			if (variable.isPersistent(respawn))
				this.playerData.put(variable, data.playerData.get(variable));
		}

	}

	/**
	 * Sends a packet to this player's client to synchronise necessary information. Only called server side.
	 */
	public void sync() {
		if (this.player instanceof EntityPlayerMP) {
			NetworkHandler.sendToPlayer((EntityPlayerMP) player, new PlayerDataPacket(this.playerData));
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public NBTTagCompound serializeNBT() {

		NBTTagCompound properties = new NBTTagCompound();

		storedVariables.forEach(k -> k.write(properties, this.playerData.get(k)));

		return properties;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {

		if (nbt != null) {

			try {
				storedVariables.forEach(k -> this.playerData.put(k, k.read(nbt)));
			}
			catch (ClassCastException e) {
				// Should only happen if someone manually edits the save file
				MineFantasyReforged.LOG.error("Player data NBT tag was not of expected type!", e);
			}
		}
	}

	// ============================================== Event Handlers ==============================================

	@SubscribeEvent
	// The type parameter here has to be Entity, not EntityPlayer, or the event won't get fired.
	public static void onCapabilityLoad(AttachCapabilitiesEvent<Entity> event) {

		if (event.getObject() instanceof EntityPlayer) {
			event.addCapability(new ResourceLocation(MineFantasyReforged.MOD_ID, "PlayerData"), new PlayerData.Provider((EntityPlayer) event.getObject()));

		}
	}

	@SubscribeEvent
	public static void onPlayerCloneEvent(PlayerEvent.Clone event) {

		PlayerData newData = PlayerData.get(event.getEntityPlayer());
		PlayerData oldData = PlayerData.get(event.getOriginal());

		newData.copyFrom(oldData, event.isWasDeath());

		newData.sync(); // In theory this should fix client/server discrepancies (see #69)
	}

	@SubscribeEvent
	public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
		if (!event.getEntity().world.isRemote && event.getEntity() instanceof EntityPlayerMP) {
			// Synchronises player data after loading.
			PlayerData data = PlayerData.get((EntityPlayer) event.getEntity());
			if (data != null) {
				if (RPGElements.isSystemActive) {
					RPGElements.initSkills(data);
				}
				ResearchLogic.syncData((EntityPlayer) event.getEntity());
				data.sync();
			}
		}
	}

	@SubscribeEvent
	public static void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event) {

		if (event.getEntityLiving() instanceof EntityPlayer) {

			EntityPlayer player = (EntityPlayer) event.getEntityLiving();

			if (PlayerData.get(player) != null) {
				PlayerData.get(player).update();
			}
		}
	}

	// ========================================== Capability Boilerplate ==========================================

	/**
	 * This is a nested class for a few reasons: firstly, it makes sense because instances of this and PlayerData go
	 * hand-in-hand; secondly, it's too short to be worth a separate file; and thirdly (and most importantly) it allows
	 * access to PLAYER_DATA_CAPABILITY while keeping it private.
	 */
	public static class Provider implements ICapabilitySerializable<NBTTagCompound> {

		private final PlayerData data;

		public Provider(EntityPlayer player) {
			data = new PlayerData(player);
		}

		@Override
		public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
			return capability == PLAYER_DATA_CAPABILITY;
		}

		@Override
		public <T> T getCapability(Capability<T> capability, EnumFacing facing) {

			if (capability == PLAYER_DATA_CAPABILITY) {
				return PLAYER_DATA_CAPABILITY.cast(data);
			}

			return null;
		}

		@Override
		public NBTTagCompound serializeNBT() {
			return data.serializeNBT();
		}

		@Override
		public void deserializeNBT(NBTTagCompound nbt) {
			data.deserializeNBT(nbt);
		}

	}
}
