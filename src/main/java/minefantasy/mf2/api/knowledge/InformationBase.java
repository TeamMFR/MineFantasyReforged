package minefantasy.mf2.api.knowledge;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.api.knowledge.client.EntryPage;
import minefantasy.mf2.api.rpg.RPGElements;
import minefantasy.mf2.api.rpg.Skill;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.IStatStringFormat;
import net.minecraft.util.StatCollector;

import java.util.ArrayList;

public class InformationBase {
    public static boolean easyResearch;
    public static boolean unlockAll;
    private static int nextID;
    public final int displayColumn;
    public final int displayRow;
    public final InformationBase parentInfo;
    public final ItemStack theItemStack;
    private final String description;
    private final String idName;
    public int ID;
    public String[] requirements;
    /**
     * Returns the fully description of the achievement - ready to be displayed on
     * screen.
     */
    public Object[] descriptValues;
    private boolean startedUnlocked = false;
    @SideOnly(Side.CLIENT)
    private IStatStringFormat statStringFormatter;
    private boolean isSpecial;
    private boolean isPerk;
    private ArrayList<SkillRequirement> skills = new ArrayList<>();
    private int artefactCount;
    private ArrayList<EntryPage> pages = new ArrayList<>();

    public InformationBase(String name, int x, int y, int artefacts, Item icon, InformationBase parent) {
        this(name, x, y, artefacts, new ItemStack(icon), parent);
    }

    public InformationBase(String name, int x, int y, int artefacts, Block icon, InformationBase parent) {
        this(name, x, y, artefacts, new ItemStack(icon), parent);
    }

    public InformationBase(String name, int x, int y, int artefacts, ItemStack icon, InformationBase parent) {
        this.idName = name;
        this.theItemStack = icon;
        this.description = "knowledge." + idName + ".desc";
        this.displayColumn = x;
        this.displayRow = y;

        if (x < InformationList.minDisplayColumn) {
            InformationList.minDisplayColumn = x;
        }

        if (y < InformationList.minDisplayRow) {
            InformationList.minDisplayRow = y;
        }

        if (x > InformationList.maxDisplayColumn) {
            InformationList.maxDisplayColumn = x;
        }

        if (y > InformationList.maxDisplayRow) {
            InformationList.maxDisplayRow = y;
        }
        this.parentInfo = parent;
        this.artefactCount = Math.max(0, artefacts);
    }

    public InformationBase addSkill(Skill skill, int level) {
        if (RPGElements.isSystemActive) {
            skills.add(new SkillRequirement(skill, level));
        }
        return this;
    }

    public InformationBase setUnlocked() {
        startedUnlocked = true;
        return this;
    }

    public InformationBase setPage(InformationPage page) {
        page.addInfo(this);
        return this;
    }

    /*
     * public IChatComponent func_150951_e() { IChatComponent ichatcomponent =
     * super.func_150951_e();
     * ichatcomponent.getChatStyle().setColor(this.getSpecial() ?
     * EnumChatFormatting.DARK_PURPLE : EnumChatFormatting.GREEN); return
     * ichatcomponent; }
     *
     * public InformationBase func_150953_b(Class p_150953_1_) { return
     * (InformationBase)super.func_150953_b(p_150953_1_); }
     */

    /**
     * Special achievements have a 'spiked' (on normal texture pack) frame, special
     * achievements are the hardest ones to achieve.
     */
    public InformationBase setSpecial() {
        this.isSpecial = true;
        return this;
    }

    public InformationBase setPerk() {
        this.isPerk = true;
        return this;
    }

    /**
     * Register the stat into StatList.
     */
    public InformationBase registerStat() {
        ID = nextID;
        nextID++;
        InformationList.knowledgeList.add(this);
        InformationList.nameMap.put(idName.toLowerCase(), this);
        return this;
    }

    public InformationBase setDescriptValues(Object... values) {
        descriptValues = values;
        return this;
    }

    @SideOnly(Side.CLIENT)
    public String getDescription() {
        String localised = StatCollector.translateToLocal(this.description);
        if (descriptValues != null && descriptValues.length > 0) {
            localised = StatCollector.translateToLocalFormatted(description, descriptValues);
        }
        String text = this.statStringFormatter != null ? this.statStringFormatter.formatString(localised) : localised;

        if (RPGElements.isSystemActive) {
            String[] requirements = getRequiredSkills();
            if (requirements != null && requirements.length > 0) {
                text += "\n\n";
                for (String s : requirements) {
                    text += s + "\n";
                }
            }
        }
        return text;
    }

    @SideOnly(Side.CLIENT)
    public String getDisplayName() {
        String name = this.statStringFormatter != null
                ? this.statStringFormatter.formatString(StatCollector.translateToLocal("knowledge." + this.idName))
                : StatCollector.translateToLocal("knowledge." + this.idName);

        if (!easyResearch) {
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            if (player != null && !ResearchLogic.hasInfoUnlocked(player, this)) {
                int artefacts = ResearchLogic.getArtefactCount(this.idName, player);
                int max = this.getArtefactCount();
                name += StatCollector.translateToLocalFormatted("research.cluecount", artefacts, max);
            }
        }

        return name;
    }

    /**
     * Special achievements have a 'spiked' (on normal texture pack) frame, special
     * achievements are the hardest ones to achieve.
     */
    public boolean getSpecial() {
        return this.isSpecial;
    }

    public boolean getPerk() {
        return this.isPerk;
    }

    public boolean onPurchase(EntityPlayer user) {
        if (!hasSkillsUnlocked(user)) {
            return false;
        }

        boolean success = ResearchLogic.canPurchase(user, this);
        if (success && !user.worldObj.isRemote) {
            user.worldObj.playSoundAtEntity(user, "minefantasy2:updateResearch", 1.0F, 1.0F);
            if (getPerk()) {
                user.worldObj.playSoundAtEntity(user, "random.levelup", 1.0F, 1.0F);
            }
        }

        if (isEasy()) {
            ResearchLogic.tryUnlock(user, this);
        } else {
        }
        return true;
    }

    public boolean hasSkillsUnlocked(EntityPlayer player) {
        if (skills == null)
            return true;
        for (int id = 0; id < skills.size(); id++) {
            SkillRequirement requirement = skills.get(id);
            if (!requirement.isAvailable(player)) {
                return false;
            }
        }
        return true;
    }

    public String getUnlocalisedName() {
        return idName;
    }

    public void addPages(EntryPage... info) {
        for (EntryPage page : info) {
            pages.add(page);
        }
    }

    public ArrayList<EntryPage> getPages() {
        return pages;
    }

    public boolean isUnlocked(int id, EntityPlayer player) {
        if (this.skills != null) {
            return skills.get(id) != null && skills.get(id).isAvailable(player);
        }
        return true;
    }

    public String[] getRequiredSkills() {
        if (this.requirements == null) {
            requirements = new String[skills.size()];
            for (int id = 0; id < skills.size(); id++) {
                SkillRequirement requirement = skills.get(id);
                requirements[id] = StatCollector.translateToLocalFormatted("rpg.required", requirement.level,
                        requirement.skill.getDisplayName());
            }
        }
        return requirements;
    }

    public boolean isPreUnlocked() {
        return !getPerk() && (unlockAll || startedUnlocked);
    }

    public int getArtefactCount() {
        return artefactCount;
    }

    public boolean isEasy() {
        return getPerk() || this.getArtefactCount() == 0 || easyResearch;
    }
}

class SkillRequirement {
    protected Skill skill;
    protected int level;

    SkillRequirement(Skill skill, int level) {
        this.skill = skill;
        this.level = level;
    }

    public boolean isAvailable(EntityPlayer player) {
        return RPGElements.hasLevel(player, skill, level);
    }
}
