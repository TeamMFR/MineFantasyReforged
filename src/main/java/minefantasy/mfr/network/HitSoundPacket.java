package minefantasy.mfr.network;

import io.netty.buffer.ByteBuf;
import minefantasy.mfr.config.ConfigClient;
import minefantasy.mfr.init.MineFantasySounds;
import minefantasy.mfr.util.MFRLogUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class HitSoundPacket extends PacketMF {
	private String soundCategory;
	private String material;
	private int entId;

	public HitSoundPacket(String soundCategory, String material, Entity hit) {
		this.soundCategory = soundCategory;
		this.material = material;
		this.entId = hit.getEntityId();
	}

	public HitSoundPacket() {
	}

	@Override
	public void readFromStream(ByteBuf packet) {
		soundCategory = ByteBufUtils.readUTF8String(packet);
		material = ByteBufUtils.readUTF8String(packet);
		entId = packet.readInt();
	}

	@Override
	public void writeToStream(ByteBuf packet) {
		ByteBufUtils.writeUTF8String(packet, soundCategory);
		ByteBufUtils.writeUTF8String(packet, material);
		packet.writeInt(entId);
	}

	@Override
	protected void execute(EntityPlayer player) {
		Entity entity = player.world.getEntityByID(entId);
		if (entity != null) {
			if (ConfigClient.playHitsound) {
				SoundEvent sound = getSound(soundCategory, material);
				entity.world.playSound(entity.posX, entity.posY, entity.posZ, sound, SoundCategory.AMBIENT, 1.0F, 1.0F, false);
				MFRLogUtil.logDebug("Played hit sound: " + sound.getSoundName().toString());
			}
		}
	}

	public static SoundEvent getSound(String type, String material) {
		if (type.equals("blunt") && material.equals("wood")) {
			return MineFantasySounds.BLUNT_WOOD;
		}
		if (type.equals("blunt") && material.equals("metal")) {
			return MineFantasySounds.BLUNT_METAL;
		}
		if (type.equals("blunt") && material.equals("stone")) {
			return MineFantasySounds.BLUNT_STONE;
		}
		if (type.equals("blade") && material.equals("wood")) {
			return MineFantasySounds.BLADE_WOOD;
		}
		if (type.equals("blade") && material.equals("metal")) {
			return MineFantasySounds.BLADE_METAL;
		}
		if (type.equals("blade") && material.equals("stone")) {
			return MineFantasySounds.BLADE_STONE;
		}
		return MineFantasySounds.BLUNT_STONE;
	}
}
