package minefantasy.mfr.network;

import io.netty.buffer.ByteBuf;
import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.mechanics.RPGElements;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import java.util.UUID;

public class LevelUpPacket extends PacketMF {
	private UUID username;
	private int level;
	private String skillName;
	private String name;
	private int skillLevel;

	public LevelUpPacket(EntityPlayer user, Skill skill, int level) {
		this.username = user.getUniqueID();
		this.skillName = skill.unlocalizedName;
		this.level = level;
	}

	public LevelUpPacket() {
	}

	@Override
	public void readFromStream(ByteBuf packet) {
		name = ByteBufUtils.readUTF8String(packet);
		skillLevel = packet.readInt();
		username = UUID.fromString(ByteBufUtils.readUTF8String(packet));

	}

	@Override
	public void writeToStream(ByteBuf packet) {
		ByteBufUtils.writeUTF8String(packet, skillName);
		packet.writeInt(level);
		ByteBufUtils.writeUTF8String(packet, username.toString());
	}

	@Override
	protected void execute(EntityPlayer player) {
		if (username != null && player != null && player.getUniqueID().equals(username)) {
			Skill skill = RPGElements.getSkillByName(name);

			if (skill != null) {
				player.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 1.0F, 0.5F);
				player.sendMessage(new TextComponentString(I18n.format("rpg.levelup", skill.getDisplayName().toLowerCase(), skillLevel)));
			}
		}
	}
}
