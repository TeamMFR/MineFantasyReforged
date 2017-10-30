package minefantasy.mf2.api.knowledge;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;

public class ResearchArtefacts {
    public static void addArtefact(Object input, InformationBase research) {
        addArtefact(input, research.getUnlocalisedName());
    }

    public static void addArtefact(Object input, String research) {
        ItemStack itemstack = convert(input);

        if (itemstack != null) {
            OreDictionary.registerOre("Artefact-" + research, itemstack);
        }
    }

    public static ArrayList<String> getResearchNames(ItemStack item) {
        ArrayList<String> names = new ArrayList<String>();
        if (item == null)
            return names;

        for (int i : OreDictionary.getOreIDs(item)) {
            String name = OreDictionary.getOreName(i);
            if (name != null && name.startsWith("Artefact-")) {
                String str = name.substring(9);
                names.add(str);
            }
        }
        return names;
    }

    public static ItemStack convert(Object input) {
        if (input == null)
            return null;

        ItemStack itemstack = null;
        if (input instanceof ItemStack) {
            return (ItemStack) input;
        } else if (input instanceof Block) {
            return new ItemStack((Block) input, 1, OreDictionary.WILDCARD_VALUE);
        } else if (input instanceof Item) {
            return new ItemStack((Item) input, 1, OreDictionary.WILDCARD_VALUE);
        }

        return null;
    }

    public static int useArtefact(ItemStack itemStack, InformationBase base, EntityPlayer user) {
        int artefacts = ResearchLogic.addArtefact(base.getUnlocalisedName().toLowerCase(), user);
        ResearchLogic.addArtefactUsed(user, base, itemStack);
        if (artefacts >= base.getArtefactCount()) {
            ResearchLogic.tryUnlock(user, base);
            return -1;
        }
        return artefacts;
    }
}
