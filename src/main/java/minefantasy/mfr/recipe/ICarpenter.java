package minefantasy.mfr.recipe;

import minefantasy.mfr.api.rpg.Skill;
import net.minecraft.util.SoundEvent;

/**
 * @author AnonymousProductions This interface is used by the anvil in
 * MineFantasy so it can be referred to without importing the mod
 */
public interface ICarpenter {
    void setProgressMax(int i);

    void setRequiredToolTier(int i);

    void setRequiredCarpenter(int i);

    void setHotOutput(boolean i);

    void setRequiredToolType(String toolType);

    void setCraftingSound(SoundEvent sound);

    void setResearch(String research);

    void setRequiredSkill(Skill skill);
}
