package minefantasy.mf2.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.api.rpg.RPGElements;
import minefantasy.mf2.api.rpg.Skill;
import minefantasy.mf2.item.list.CreativeTabMF;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

public class ItemSkillBook extends ItemComponentMF {
    private Skill skill;
    private String name;
    private boolean isMax = false;

    public ItemSkillBook(String name, Skill skill) {
        super(name, 1);
        setMaxStackSize(16);
        setTextureName("minefantasy2:Other/" + name);
        this.setCreativeTab(CreativeTabMF.tabGadget);
        this.skill = skill;
        this.name = name;
    }

    public Item setMax() {
        isMax = true;
        return setMaxStackSize(1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack item, EntityPlayer user, List list, boolean fullInfo) {
        if (isMax) {
            list.add(StatCollector.translateToLocal("item." + name + ".desc"));
        } else {
            list.add(StatCollector.translateToLocalFormatted("item." + name + ".desc", 1));
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer user) {
        boolean used = false;
        if (skill != null) {
            int lvl = RPGElements.getLevel(user, skill);
            if (lvl < skill.getMaxLevel()) {
                if (isMax) {
                    skill.manualLvlUp(user, 100);
                } else {
                    skill.addXP(user, world.rand.nextInt(skill.getLvlXP(lvl, user)));
                }
                used = true;
            }
        }
        if (used) {
            user.worldObj.playSoundEffect(user.posX, user.posY, user.posZ, "minefantasy2:updateResearch", 1.0F, 1.0F);
            if (!user.capabilities.isCreativeMode) {
                --item.stackSize;
            }
        }
        return item;
    }

    @Override
    public EnumRarity getRarity(ItemStack item) {
        return isMax ? EnumRarity.epic : EnumRarity.uncommon;
    }
}
