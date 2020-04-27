package minefantasy.mfr.item.gadget;

import net.minecraft.util.ResourceLocation;

public class ItemArtefactLoot extends ItemLootSack {
    public ItemArtefactLoot(String name, ResourceLocation pool, int ammount) {
        super(name, ammount, 2);
        this.pool = pool;
    }
}
