package minefantasy.mfr.item;

import minefantasy.mfr.constants.Rarity;
import minefantasy.mfr.data.CapabilityItemMultiUse;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class ItemMultiFood extends ItemFoodMFR {
	private final int maxBites;

	public ItemMultiFood(String name, int bites, int hunger, float saturation, boolean meat, Rarity rarity) {
		super(name, hunger, saturation, meat, rarity);
		this.maxBites = bites;
		setMaxStackSize(1);
		setMaxDamage(bites - 1);

		this.addPropertyOverride(new ResourceLocation("bites"), new IItemPropertyGetter() {
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
				return CapabilityItemMultiUse.getCurrentBites(stack);
			}
		});
	}

	@Override
	public ICapabilityProvider initCapabilities(final ItemStack stack, @Nullable final NBTTagCompound nbt) {
		return new CapabilityItemMultiUse.Provider(new CapabilityItemMultiUse());
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack food, World world, EntityLivingBase consumer) {
		ItemStack stack = super.onItemUseFinish(food, world, consumer);
		int currentBites = CapabilityItemMultiUse.getCurrentBites(stack);
		currentBites++;
		if (currentBites >= maxBites) {
			return ItemStack.EMPTY;
		}
		CapabilityItemMultiUse.setCurrentBites(stack, currentBites);
		return stack;
	}

	@Override
	public boolean isRepairable() {
		return false;
	}
}
