package minefantasy.mfr.item;

public class ItemMultiFood extends ItemFoodMFR {

	public ItemMultiFood(String name, int bites, int hunger, float saturation, boolean meat, int rarity) {
		super(name, hunger, saturation, meat, rarity);
		setMaxStackSize(1);
		setMaxDamage(bites - 1);
	}

	@Override
	public boolean isRepairable() {
		return false;
	}
}
