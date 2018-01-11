package minefantasy.mf2.api.rpg;

import minefantasy.mf2.api.helpers.PlayerTagData;
import minefantasy.mf2.util.XSTRandom;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.HashMap;

public class RPGElements {
    private static final String statsName = "RPGStats_MF";
    public static boolean isSystemActive = true;
    public static float levelSpeedModifier = 1.0F;
    public static float levelUpModifier = 1.5F;
    public static ArrayList<Skill> skillsList = new ArrayList<Skill>();
    private static HashMap<String, Skill> skillsMap = new HashMap<String, Skill>();
    private static XSTRandom rand = new XSTRandom();

    static {
        if (isSystemActive) {
            SkillList.init();
        }
    }

    public static void addSkill(String name) {
        addSkill(new Skill(name));
    }

    public static void addSkill(Skill skill) {
        skillsMap.put(skill.skillName, skill);
        skillsList.add(skill);
    }

    public static NBTTagCompound getTagData(EntityPlayer player) {
        NBTTagCompound nbt = PlayerTagData.getPersistedData(player);
        if (!nbt.hasKey(statsName)) {
            nbt.setTag(statsName, new NBTTagCompound());
        }
        return nbt.getCompoundTag(statsName);
    }

    public static NBTTagCompound getSkill(EntityPlayer player, String skillname) {
        NBTTagCompound nbt = getTagData(player);
        if (!nbt.hasKey(skillname)) {
            NBTTagCompound tag = new NBTTagCompound();

            Skill skill = skillsMap.get(skillname);
            if (skill != null) {
                skill.init(tag, player);
            }
            nbt.setTag(skillname, tag);
        }
        return nbt.getCompoundTag(skillname);
    }

    public static void initSkills(EntityPlayer player) {
        for (int id = 0; id < skillsList.size(); id++) {
            Skill skill = skillsList.get(id);
            skill.sync(player);
        }
    }

    public static Skill getSkillByName(String name) {
        return skillsMap.get(name.toLowerCase());
    }

    public static int getLevel(EntityPlayer player, Skill skill) {
        return RPGElements.getSkill(player, skill.skillName).getInteger("level");
    }

    /**
     * Modifies the stamina cost for an ability (-1 means impossible)
     */
    public static float getStaminaCostModifier(EntityPlayer player, Skill skill, int minLevel) {
        NBTTagCompound stat = getSkill(player, skill.skillName);
        int level = stat.getInteger("level");

        if (level < minLevel) {
            return -1;
        } else {
            if (skill.getMaxLevel() - minLevel <= 0) {
                return 1.0F;
            }
            float progress = (level - minLevel) / (skill.getMaxLevel() - minLevel);
            return 1.5F - progress;
        }
    }

    public static float getWeaponModifier(EntityPlayer player, Skill skill) {
        int minSkill = 25;// No bonus damage until level 25+
        int level = getLevel(player, skill);
        if (level <= minSkill) {
            return 1.0F;
        }

        float progress = ((float) level - minSkill) / ((float) skill.getMaxLevel() - minSkill);
        return 1.0F + (progress);
    }

    public static boolean hasLevel(EntityPlayer player, Skill skill, int requirement) {
        return getSkill(player, skill.skillName).getInteger("level") >= requirement;
    }

    public static Skill getRandomSkill() {
        int id = rand.nextInt(skillsList.size());
        return skillsList.get(id);
    }

    public static void syncAll(EntityPlayer user) {
        if (!user.worldObj.isRemote) {
            for (int a = 0; a < skillsList.size(); a++) {
                Skill skill = skillsList.get(a);
                if (skill != null) {
                    skill.sync(user);
                }
            }
        }
    }
}
