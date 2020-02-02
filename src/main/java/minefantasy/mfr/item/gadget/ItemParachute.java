package minefantasy.mfr.item.gadget;

import net.minecraft.init.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.common.registry.GameRegistry;
import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.entity.EntityParachute;
import minefantasy.mfr.init.CreativeTabMFR;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemParachute extends Item {
    public ItemParachute() {
        String name = "parachute";
        this.maxStackSize = 1;
        setRegistryName(name);
        setUnlocalizedName(MineFantasyReborn.MODID + "." + name);
        GameRegistry.findRegistry(Item.class).register(this);
        this.setCreativeTab(CreativeTabMFR.tabGadget);
        this.setUnlocalizedName(name);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer user, EnumHand hand) {
        ItemStack item = user.getHeldItem(hand);
        if (!world.isRemote) {
            world.playSound(user, user.getPosition(), SoundEvents.ENTITY_HORSE_SADDLE, SoundCategory.AMBIENT, 1.0F, 1.0F);
            EntityParachute chute = new EntityParachute(world, user.posX, user.posY, user.posZ);
            world.spawnEntity(chute);
            user.startRiding(chute);
            item.shrink(1);
        }
        return ActionResult.newResult(EnumActionResult.PASS, item);
    }
}