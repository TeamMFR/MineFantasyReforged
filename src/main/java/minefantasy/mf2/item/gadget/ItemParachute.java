package minefantasy.mf2.item.gadget;

import java.util.List;

import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.entity.EntityBomb;
import minefantasy.mf2.entity.EntityParachute;
import minefantasy.mf2.item.list.CreativeTabMF;
import minefantasy.mf2.mechanics.BombDispenser;
import net.minecraft.block.BlockDispenser;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemParachute extends Item
{
    public ItemParachute()
    {
    	String name = "parachute";
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabMF.tabGadget);
        setTextureName("minefantasy2:Other/"+name);
		GameRegistry.registerItem(this, name, MineFantasyII.MODID);
		this.setUnlocalizedName(name);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer user)
    {
    	if(!world.isRemote)
    	{
    		world.playSoundAtEntity(user, "mob.horse.leather", 1.0F, 0.5F);
	    	EntityParachute chute = new EntityParachute(world, user.posX, user.posY, user.posZ);
	    	world.spawnEntityInWorld(chute);
	    	user.mountEntity(chute);
	    	--item.stackSize;
    	}
    	return item;
    }
}