package minefantasy.mfr.item;

import minefantasy.mfr.init.SoundsMFR;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import minefantasy.mfr.api.rpg.RPGElements;
import minefantasy.mfr.api.rpg.Skill;
import minefantasy.mfr.init.CreativeTabMFR;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

import java.util.List;

public class ItemSkillBook extends ItemComponentMFR {
    private Skill skill;
    private String name;
    private boolean isMax = false;

    public ItemSkillBook(String name, Skill skill) {
        super(name, 1);
        setMaxStackSize(16);
        this.setCreativeTab(CreativeTabMFR.tabGadget);
        this.skill = skill;
        this.name = name;
    }

    public Item setMax() {
        isMax = true;
        return setMaxStackSize(1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack item, World world, List list, ITooltipFlag flag) {
        if (isMax) {
            list.add(I18n.translateToLocal("item." + name + ".desc"));
        } else {
            list.add(I18n.translateToLocalFormatted("item." + name + ".desc", 1));
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer user, EnumHand hand) {
        ItemStack item = user.getHeldItem(hand);
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
            user.world.playSound(user.posX, user.posY, user.posZ, SoundsMFR.UPDATE_RESEARCH, SoundCategory.NEUTRAL, 1.0F, 1.0F, true);
            if (!user.capabilities.isCreativeMode) {
                item.shrink(1);
            }
        }
        return ActionResult.newResult(EnumActionResult.PASS, item);
    }

    @Override
    public EnumRarity getRarity(ItemStack item) {
        return isMax ? EnumRarity.EPIC : EnumRarity.COMMON;
    }
}
