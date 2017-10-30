package minefantasy.mf2.item.gadget;

import cpw.mods.fml.common.registry.GameRegistry;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.entity.EntityParachute;
import minefantasy.mf2.item.list.CreativeTabMF;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemParachute extends Item {
    public ItemParachute() {
        String name = "parachute";
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabMF.tabGadget);
        setTextureName("minefantasy2:Other/" + name);
        GameRegistry.registerItem(this, name, MineFantasyII.MODID);
        this.setUnlocalizedName(name);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer user) {
        if (!world.isRemote) {
            world.playSoundAtEntity(user, "mob.horse.leather", 1.0F, 0.5F);
            EntityParachute chute = new EntityParachute(world, user.posX, user.posY, user.posZ);
            world.spawnEntityInWorld(chute);
            user.mountEntity(chute);
            --item.stackSize;
        }
        return item;
    }
}