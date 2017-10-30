package minefantasy.mf2.item;

import minefantasy.mf2.item.food.FoodListMF;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

public class ItemBurntFood extends ItemComponentMF {

    private Random rand = new Random();

    public ItemBurntFood(String name) {
        super(name, -1);
        setTextureName("minefantasy2:food/" + name);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
        if (this == FoodListMF.burnt_food) {
            if (!world.isRemote && rand.nextInt(5) == 0) {
                player.entityDropItem(new ItemStack(Items.coal, 1, 1), 0.0F);
            }
            --item.stackSize;
            return item;
        }
        if (!world.isRemote) {
            player.entityDropItem(new ItemStack(FoodListMF.burnt_food, item.stackSize), 0.0F);
        }
        return item.getItem().getContainerItem(item);
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        return new ItemStack(getContainerItem(), itemStack.stackSize);
    }
}
