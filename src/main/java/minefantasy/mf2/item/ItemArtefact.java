package minefantasy.mf2.item;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.api.knowledge.IArtefact;
import minefantasy.mf2.api.knowledge.ResearchArtefacts;
import minefantasy.mf2.block.decor.BlockSchematic;
import minefantasy.mf2.item.list.CreativeTabMF;
import minefantasy.mf2.item.list.ToolListMF;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;

import java.util.List;

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

    @SideOnly(Side.CLIENT)
    private static IIcon[] icons;

    public ItemArtefact(String name) {
        this(name, 2);
    }

    public ItemArtefact(String name, int rarity) {
        this.setCreativeTab(CreativeTabMF.tabMaterialsMF);
        GameRegistry.registerItem(this, name, MineFantasyII.MODID);
        this.setHasSubtypes(true);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int dam) {
        return icons[Math.min(dam, icons.length - 1)];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {
        icons = new IIcon[types.length];
        for (int i = 0; i < types.length; ++i) {
            icons[i] = reg.registerIcon("minefantasy2:artefact/" + types[i].tex);
        }
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
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < types.length; i++) {
            list.add(new ItemStack(item, 1, i));
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
        if (lvl >= ToolListMF.rarity.length) {
            lvl = ToolListMF.rarity.length - 1;
        }
        return ToolListMF.rarity[lvl];
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
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer user) {
        int meta = item.getItemDamage();
        if (!world.isRemote && meta > 3) {
            MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, user, false);

            if (movingobjectposition == null) {
                return item;
            } else {
                if (BlockSchematic.useSchematic(item, world, user, movingobjectposition)) {
                    item.stackSize--;
                    return item;
                }
            }
        }
        return item;
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
