package minefantasy.mf2.api.crafting.anvil;

import minefantasy.mf2.api.rpg.Skill;

/**
 * @author AnonymousProductions This interface is used by the anvil in
 * MineFantasy so it can be referred to without importing the mod
 */
public interface IAnvil {
    void setForgeTime(int i);

    void setHammerUsed(int i);

    void setRequiredAnvil(int i);

    void setHotOutput(boolean i);

    void setToolType(String toolType);

    void setSkill(Skill skill);

    public void setResearch(String research);

    int getRecipeHammer();

    int getRecipeAnvil();
}
