package minefantasy.mfr.item;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.api.knowledge.IArtefact;
import minefantasy.mfr.api.knowledge.ResearchArtefacts;
import minefantasy.mfr.block.decor.BlockSchematic;
import minefantasy.mfr.init.CreativeTabMFR;
import minefantasy.mfr.init.ToolListMFR;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemArtefact extends Item implements IArtefact {
    public static final String MYTHIC = "mythic_artefacts";
    public static final String DWARVEN = "dwarven_artefacts";
    public static final String GNOMISH = "gnomish_artefacts";

    private static final Artefact[] types = new Artefact[]{
            new Artefact("ancient_jewel", "mithril_jewel", 20, 2, MYTHIC, 2, "smeltMithril", "smeltMaster"),
            new Artefact("ancient_jewel", "adamant_jewel", 20, 2, MYTHIC, 2, "smeltAdamantium", "smeltMaster"),
            new Artefact("ancient_jewel", "master_jewel", 30, 2, MYTHIC, 1, "smeltMaster"),
            new Artefact("trilogy_jewel", "trilogy_jewel", 3, null, 1),
            new Artefact("schem_bomb", "schem_bomb", 50, 2, null, 1, "bombObsidian", "mineObsidian"),
            new Artefact("schem_crossbow", "schem_crossbow", 50, 2, null, 1, "crossShaftAdvanced", "crossHeadAdvanced"),
            new Artefact("schem_forge", "schem_forge", 50, 2, null, 1, "advforge", "advcrucible"),
            new Artefact("schem_gears", "schem_gears", 50, 2, null, 1, "cogArmour"),
            new Artefact("schem_cogwork", "schem_cogwork", 50, 2, null, 1, "cogArmour"),
            new Artefact("schem_alloy", "schem_alloy", 50, 2, null, 1, "compPlate"),};

    public ItemArtefact(String name) {
        this(name, 2);
    }

    public ItemArtefact(String name, int rarity) {
        this.setCreativeTab(CreativeTabMFR.tabMaterialsMFR);
        setRegistryName(name);
        setUnlocalizedName(MineFantasyReborn.MODID + "." + name);
        GameRegistry.findRegistry(Item.class).register(this);
        this.setHasSubtypes(true);
    }

    @Override
    public String getUnlocalizedName(ItemStack item) {
        return "item." + getArtefact(item).name;
    }

    private Artefact getArtefact(ItemStack item) {
        return types[Math.min(item.getItemDamage(), types.length - 1)];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        for (int i = 0; i < types.length; i++) {
            items.add(items.get(i));
        }
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
        if (lvl >= ToolListMFR.rarity.length) {
            lvl = ToolListMFR.rarity.length - 1;
        }
        return ToolListMFR.rarity[lvl];
    }

    public void registerAll() {
        for (int id = 0; id < types.length; id++) {
            types[id].register(this, id);
        }
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
}

class Artefact {
    public final String name, tex;
    public final int rarity, time;
    public final String[] researches;
    public final String lootType;
    public final int dropWeight;

    Artefact(String name, String tex, int rarity, String lootType, int dropChance) {
        this(name, tex, 0, rarity, lootType, dropChance);
    }

    Artefact(String name, String tex, int time, int rarity, String lootType, int dropChance, String... researches) {
        this.name = name;
        this.time = time;
        this.tex = tex;
        this.rarity = rarity;
        this.researches = researches;
        this.lootType = lootType;
        this.dropWeight = dropChance;
    }

    public void register(Item item, int id) {
        if (researches != null) {
            for (String research : this.researches) {
                ResearchArtefacts.addArtefact(new ItemStack(item, 1, id), research.toLowerCase());
            }
        }
        if (lootType != null) {
            ChestGenHooks.addItem(lootType,
                    new WeightedRandomChestContent(new ItemStack(item, 1, id), 1, 1, dropWeight));
        }
    }
}
