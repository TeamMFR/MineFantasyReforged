package minefantasy.mf2.item.food;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMultiFood extends ItemFoodMF
{
	private IIcon[] icons;
	private int bites = 6;
	public ItemMultiFood(String name, int bites, int hunger, float saturation, float noEatTime, int rarity)
	{
		super(name, hunger, saturation, noEatTime, false, rarity);
		setMaxStackSize(1);
		this.bites = bites;
		setMaxDamage(bites-1);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int damage)
    {
        int index = MathHelper.clamp_int(damage, 0, bites-1);
        return this.icons[index];
    }
	
	@Override
	public boolean isRepairable()
    {
		return false;
    }
	
	@Override
	public ItemStack onEaten(ItemStack food, World world, EntityPlayer consumer)
    {
		consumer.getFoodStats().func_151686_a(this, food);
        world.playSoundAtEntity(consumer, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
        this.onFoodEaten(food, world, consumer);
        
		if(food.attemptDamageItem(1, consumer.getRNG()))
		{
			--food.stackSize;
			if(food.stackSize < 0)
			{
				food.stackSize = 0;
			}
		}
		return food;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register)
    {
        this.icons = new IIcon[bites];

        for (int i = 0; i < bites; ++i)
        {
            this.icons[i] = register.registerIcon(this.getIconString() + "_" + i);
        }
    }
}
