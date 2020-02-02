package minefantasy.mfr.api.crafting.carpenter;

import minefantasy.mfr.api.rpg.Skill;
import net.minecraft.util.SoundEvent;

/**
 * @author AnonymousProductions This interface is used by the anvil in
 * MineFantasy so it can be referred to without importing the mod
 */
public interface ICarpenter {
    void setForgeTime(int i);

    void setToolTier(int i);

    void setRequiredCarpenter(int i);

    void setHotOutput(boolean i);

    void setToolType(String toolType);

    void setCraftingSound(SoundEvent sound);

    void setResearch(String research);

    void setSkill(Skill skill);
}
