package minefantasy.mf2.api.rpg;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent;

public class LevelupEvent extends LivingEvent {
    public final EntityPlayer thePlayer;
    public final Skill theSkill;
    public final int theLevel;

    public LevelupEvent(EntityPlayer player, Skill skill, int newlvl) {
        super(player);
        this.thePlayer = player;
        this.theSkill = skill;
        this.theLevel = newlvl;
    }

}
