package minefantasy.mfr.item.gadget;

public class ItemArtefactLoot extends ItemLootSack {
    public ItemArtefactLoot(String name, String pool, int ammount) {
        super(name, ammount, 2);
        this.pool = pool;
    }
}
