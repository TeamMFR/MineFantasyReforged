package minefantasy.mf2.api.refine;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class BlastFurnaceRecipes {
    private static final BlastFurnaceRecipes smeltingBase = new BlastFurnaceRecipes();
    /**
     * The list of smelting results.
     */
    private Map<ItemStack, ItemStack> smeltingList = new HashMap<ItemStack, ItemStack>();

    private BlastFurnaceRecipes() {
    }

    /**
     * Used to call methods addRecipe, removeRecipe and getSmeltingResult
     */
    public static BlastFurnaceRecipes smelting() {
        return smeltingBase;
    }

    public Map<ItemStack, ItemStack> getSmeltingList() {
        return this.smeltingList;
    }

    public void addRecipe(Block input, ItemStack output) {
        this.addRecipe(Item.getItemFromBlock(input), output);
    }

    public void addRecipe(Item input, ItemStack output) {
        this.addRecipe(new ItemStack(input, 1, 32767), output);
    }

    public void addRecipe(ItemStack input, ItemStack output) {
        this.smeltingList.put(input, output);
    }

    public void removeRecipe(@Nullable ItemStack input, ItemStack output) {
        if (output != null) {
            for (Iterator<Map.Entry<ItemStack, ItemStack>> it = this.smeltingList.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry<ItemStack, ItemStack> entry = it.next();
                if (input != null && !this.compareItemStacks(entry.getKey(), input)) {
                    continue;
                }

                if (compareItemStacks(entry.getValue(), output)) {
                    it.remove();
                }
            }
        }
    }

    /**
     * Returns the smelting result of an item.
     */
    public ItemStack getSmeltingResult(ItemStack input) {
        Iterator iterator = this.smeltingList.entrySet().iterator();
        Entry entry;

        do {
            if (!iterator.hasNext()) {
                return null;
            }

            entry = (Entry) iterator.next();
        } while (!this.compareItemStacks(input, (ItemStack) entry.getKey()));

        return (ItemStack) entry.getValue();
    }

    private boolean compareItemStacks(ItemStack p_151397_1_, ItemStack p_151397_2_) {
        return p_151397_2_.getItem() == p_151397_1_.getItem()
                && (p_151397_2_.getItemDamage() == 32767 || p_151397_2_.getItemDamage() == p_151397_1_.getItemDamage());
    }
}