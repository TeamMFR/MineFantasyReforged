package minefantasy.mfr.item.food;

public class ItemMultiFood extends ItemFoodMF {
    private int bites = 6;

    public ItemMultiFood(String name, int bites, int hunger, float saturation, int rarity) {
        this(name, bites, hunger, saturation, false, rarity);
    }

    public ItemMultiFood(String name, int bites, int hunger, float saturation, boolean meat, int rarity) {
        super(name, hunger, saturation, meat, rarity);
        setMaxStackSize(1);
        this.bites = bites;
        setMaxDamage(bites - 1);
    }

    @Override
    public boolean isRepairable() {
        return false;
    }
}
