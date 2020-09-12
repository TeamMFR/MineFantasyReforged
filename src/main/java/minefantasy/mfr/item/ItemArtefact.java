package minefantasy.mfr.item;

import minefantasy.mfr.api.knowledge.IArtefact;
import minefantasy.mfr.block.decor.BlockSchematic;
import minefantasy.mfr.init.ComponentListMFR;
import minefantasy.mfr.init.CreativeTabMFR;
import minefantasy.mfr.init.ToolListMFR;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemArtefact extends ItemBaseMFR implements IArtefact {
    public static final String MYTHIC = "mythic_artefacts";
    public static final String DWARVEN = "dwarven_artefacts";
    public static final String GNOMISH = "gnomish_artefacts";

    public final String name;
    public final int rarity, time;
    public final String[] researches;
    public final String lootType;
    public final int dropWeight;

    public ItemArtefact(String name, int rarity, String lootType, int dropChance) {
            this(name, 0, rarity, lootType, dropChance);
    }

    public ItemArtefact(String name, int time, int rarity, String lootType, int dropChance, String... researches) {
        super(name);
        this.name = name;
        this.time = time;
        this.rarity = rarity;
        this.researches = researches;
        this.lootType = lootType;
        this.dropWeight = dropChance;

        this.setCreativeTab(CreativeTabMFR.tabMaterialsMFR);
        this.setHasSubtypes(true);
    }

    @Override
    public String getUnlocalizedName(ItemStack item) {
        return "item." + getArtefact(item).name;
    }

    private ItemArtefact getArtefact(ItemStack item) {
        return this;
    }

    @Override
    public EnumRarity getRarity(ItemStack item) {
        int lvl = getArtefact(item).rarity + 1;

        if (item.isItemEnchanted()) {
            if (lvl == 0) {
                lvl++;
            }
            lvl++;
        }
        if (lvl >= ToolListMFR.RARITY.length) {
            lvl = ToolListMFR.RARITY.length - 1;
        }
        return ToolListMFR.RARITY[lvl];
    }

    @Override
    public int getStudyTime(ItemStack item) {
        return getArtefact(item).time;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer user, EnumHand hand) {
        ItemStack item = user.getHeldItem(hand);
        if (!world.isRemote) {
            RayTraceResult movingobjectposition = this.rayTrace(world, user, false);

            if (movingobjectposition == null) {
                return ActionResult.newResult(EnumActionResult.PASS, item);
            } else {
                if (BlockSchematic.useSchematic(item, world, user, movingobjectposition)) {
                    item.shrink(1);
                    return ActionResult.newResult(EnumActionResult.PASS, item);
                }
            }
        }
        return ActionResult.newResult(EnumActionResult.FAIL, item);
    }

    @Override
    public void registerClient() {

    }


}
