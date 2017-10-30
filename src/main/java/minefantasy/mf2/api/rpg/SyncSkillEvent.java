package minefantasy.mf2.api.rpg;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent;

public class SyncSkillEvent extends LivingEvent {
    public final EntityPlayer thePlayer;
    public final Skill theSkill;

    public SyncSkillEvent(EntityPlayer player, Skill skill) {
        super(player);
        this.thePlayer = player;
        this.theSkill = skill;
    }

}
