package minefantasy.mf2.integration.nei;

import codechicken.nei.ItemList;
import codechicken.nei.PositionedStack;
import minefantasy.mf2.api.helpers.CustomToolHelper;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MFPositionedStack extends PositionedStack {
    public boolean permutated = false;

    public MFPositionedStack(Object object, int x, int y) {
        this(object, x, y, true);
    }

    public MFPositionedStack(Object object, int x, int y, boolean genPerm) {
        super(object, x, y, genPerm);
    }

    @Override
    public void generatePermutations() {
        if (permutated)
            return;

        ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
        for (ItemStack item : items) {
            if (item == null || item.getItem() == null)
                continue;

            if (item.getItemDamage() == Short.MAX_VALUE) {
                List<ItemStack> permutations = ItemList.itemMap.get(item.getItem());
                if (!permutations.isEmpty()) {
                    for (ItemStack stack : permutations) {
                        if (item.getItem().equals(stack.getItem())
                                && CustomToolHelper.doesMatchForRecipe(item, stack)) { // TODO:
                            // Rework
                            stacks.add(stack.copy());
                        }
                    }
                } else {
                    ItemStack base = new ItemStack(item.getItem(), item.stackSize);
                    base.setTagCompound(item.getTagCompound());
                    stacks.add(base);
                }
                continue;
            }

            stacks.add(item.copy());
        }
        items = stacks.toArray(new ItemStack[0]);

        if (items.length == 0)
            items = new ItemStack[]{new ItemStack(Blocks.fire)};

        permutated = true;
        setPermutationToRender(0);
    }
}