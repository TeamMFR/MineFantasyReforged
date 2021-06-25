package minefantasy.mfr.init;

import minefantasy.mfr.MineFantasyReforged;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;

@ObjectHolder(MineFantasyReforged.MOD_ID)
@Mod.EventBusSubscriber(modid = MineFantasyReforged.MOD_ID)

public class MineFantasySounds {
	private static final List<SoundEvent> sounds = new ArrayList<>();

	public static SoundEvent BELLOWS = createSoundEvent("block.bellows");
	public static SoundEvent CARPENTER_MALLET = createSoundEvent("block.carpentermallet");
	public static SoundEvent FURNACE_CLOSE = createSoundEvent("block.furnace_close");
	public static SoundEvent FURNACE_OPEN = createSoundEvent("block.furnace_open");
	public static SoundEvent MYTHIC_ORE = createSoundEvent("block.mythicore");

	public static SoundEvent COGWORK_IDLE = createSoundEvent("entity.cogwork.idle");
	public static SoundEvent COGWORK_TOOT = createSoundEvent("entity.cogwork.toot");

	public static SoundEvent DRAGON_BITE = createSoundEvent("mob.dragon.bite");
	public static SoundEvent DRAGON_FIRE = createSoundEvent("mob.dragon.fire");
	public static SoundEvent DRAGON_HURT = createSoundEvent("mob.dragon.hurt");
	public static SoundEvent DRAGON_SAY = createSoundEvent("mob.dragon.say");
	public static SoundEvent DRAGON_STEP = createSoundEvent("mob.dragon.step");

	public static SoundEvent MINOTAUR_DEATH = createSoundEvent("mob.minotaur.death");
	public static SoundEvent MINOTAUR_HURT = createSoundEvent("mob.minotaur.hurt");
	public static SoundEvent MINOTAUR_SAY = createSoundEvent("mob.minotaur.say");

	public static SoundEvent ANVIL_FAIL = createSoundEvent("block.anvilfail");
	public static SoundEvent ANVIL_SUCCEED = createSoundEvent("block.anvilsucceed");

	public static SoundEvent CRAFT_PRIMITIVE = createSoundEvent("block.craftprimitive");
	public static SoundEvent HAMMER_CARPENTER = createSoundEvent("block.hammercarpenter");
	public static SoundEvent QUERN = createSoundEvent("block.quern");
	public static SoundEvent SAW_CARPENTER = createSoundEvent("block.sawcarpenter");
	public static SoundEvent TWIST_BOLT = createSoundEvent("block.twistbolt");

	public static SoundEvent FLIP_PAGE = createSoundEvent("block.flipPage");
	public static SoundEvent UPDATE_RESEARCH = createSoundEvent("updateResearch");

	public static SoundEvent BLADE_METAL = createSoundEvent("weapon.hit.blade.metal");
	public static SoundEvent BLADE_STONE = createSoundEvent("weapon.hit.blade.stone");
	public static SoundEvent BLADE_WOOD = createSoundEvent("weapon.hit.blade.wood");

	public static SoundEvent BLUNT_METAL = createSoundEvent("weapon.hit.blunt.metal");
	public static SoundEvent BLUNT_STONE = createSoundEvent("weapon.hit.blunt.stone");
	public static SoundEvent BLUNT_WOOD = createSoundEvent("weapon.hit.blunt.wood");

	public static SoundEvent ARROW_HIT = createSoundEvent("weapon.arrowHit");
	public static SoundEvent BOMB_BOUNCE = createSoundEvent("weapon.bombBounce");
	public static SoundEvent BOW_FIRE = createSoundEvent("weapon.bowFire");
	public static SoundEvent CRITICAL = createSoundEvent("weapon.critical");
	public static SoundEvent CROSSBOW_FIRE = createSoundEvent("weapon.crossbow_fire");
	public static SoundEvent CROSSBOW_LOAD = createSoundEvent("weapon.crossbowload");
	public static SoundEvent WOOD_PARRY = createSoundEvent("weapon.wood_parry");

	@SubscribeEvent
	public static void register(RegistryEvent.Register<SoundEvent> event) {
		IForgeRegistry<SoundEvent> registry = event.getRegistry();
		sounds.forEach(registry::register);
	}

	private static SoundEvent createSoundEvent(String soundName) {
		ResourceLocation registryName = new ResourceLocation(MineFantasyReforged.MOD_ID, soundName);
		SoundEvent soundEvent = new SoundEvent(registryName).setRegistryName(registryName);
		sounds.add(soundEvent);
		return soundEvent;
	}

}