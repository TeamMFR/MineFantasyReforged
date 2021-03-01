package minefantasy.mfr.event;

import minefantasy.mfr.constants.Skill;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent;

public class LevelUpEvent extends LivingEvent {
	public final EntityPlayer thePlayer;
	public final Skill theSkill;
	public final int theLevel;

	public LevelUpEvent(EntityPlayer player, Skill skill, int newlvl) {
		super(player);
		this.thePlayer = player;
		this.theSkill = skill;
		this.theLevel = newlvl;
	}

}
