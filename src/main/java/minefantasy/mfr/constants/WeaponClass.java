package minefantasy.mfr.constants;

import minefantasy.mfr.api.weapon.IWeaponClass;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

import java.util.HashMap;

public class WeaponClass {
	// TODO make this into an enum
	public static HashMap<String, WeaponClass> classes = new HashMap<String, WeaponClass>();

	public static WeaponClass FIST = new WeaponClass("fist", null, "blunt");

	public static WeaponClass BLADE = new WeaponClass("blade", null, "blunt");
	public static WeaponClass BLUNT = new WeaponClass("blunt", null, "blade");
	public static WeaponClass AXE = new WeaponClass("axe", null, "blade");
	public static WeaponClass POLEARM = new WeaponClass("polearm", null, "blade");
	public static WeaponClass MISC = new WeaponClass("misc", null, "blunt");

	public Skill parentSkill;
	public String name;
	public String soundCategory;

	public WeaponClass(String name, Skill parent, String sound) {
		this.name = name;
		this.parentSkill = parent;
		this.soundCategory = sound;
		classes.put(name, this);
	}

	public static WeaponClass getWeaponClass(String name) {
		WeaponClass WC = classes.get(name);
		return WC != null ? WC : WeaponClass.MISC;
	}

	public static WeaponClass getWeaponClass(ItemStack weapon) {
		if (weapon.isEmpty()) {
			return WeaponClass.FIST;
		}
		if (weapon.getItem() instanceof IWeaponClass) {
			return ((IWeaponClass) weapon.getItem()).getWeaponClass();
		}
		return WeaponClass.BLADE;
	}

	public static WeaponClass findClassForAny(ItemStack weapon) {
		if (weapon.isEmpty())
			return FIST;
		if (weapon.getItem() instanceof IWeaponClass || weapon.getItem() instanceof ItemSword) {
			return getWeaponClass(weapon);
		}
		return WeaponClass.MISC;
	}

	public String getSoundCategory() {
		return soundCategory;
	}
}
