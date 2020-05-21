package minefantasy.mfr.item.gadget;

import minefantasy.mfr.init.CreativeTabMFR;
import minefantasy.mfr.init.LootRegistryMFR;
import minefantasy.mfr.item.ItemBaseMFR;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;

import java.util.List;
import java.util.Random;

public class ItemLootSack extends ItemBaseMFR {

    protected int amount, tier;
    protected ResourceLocation pool;

    public ItemLootSack(String name, int amount, int tier) {
        super(name);
        this.setCreativeTab(CreativeTabMFR.tabGadget);
        this.amount = amount;
        this.tier = tier;
        pool = tier == 0 ? LootRegistryMFR.LOOT_SACK_COMMON : tier == 1 ? LootRegistryMFR.LOOT_SACK_VALUABLE : LootRegistryMFR.LOOT_SACK_EXQUISITE;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer user, EnumHand hand) {
        ItemStack item = user.getHeldItem(hand);
        if (user.isSwingInProgress)
            return ActionResult.newResult(EnumActionResult.PASS, item);

        user.swingArm(EnumHand.MAIN_HAND);
        if (!user.capabilities.isCreativeMode) {
            item.shrink(1);
        }
        user.playSound(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 1.0F, 1.5F);

        if (!world.isRemote) {
            Random rand = new Random();
            LootContext.Builder lootContextBuilder = new LootContext.Builder((WorldServer)world);
            List<ItemStack> result = world.getLootTableManager().getLootTableFromLocation(this.pool).generateLootForPools(rand, lootContextBuilder.build());
            for (int a = 0; a < result.size(); a++) {
                if (result.get(a) != null) {
                    ItemStack drop = result.get(a);
                    if (!user.inventory.addItemStackToInventory(drop)) {
                        user.entityDropItem(drop, 0F);
                    }
                }
            }
        }
        return ActionResult.newResult(EnumActionResult.PASS, item);
    }

    @Override
    public EnumRarity getRarity(ItemStack item) {
        return tier == 0 ? EnumRarity.COMMON : tier == 1 ? EnumRarity.UNCOMMON : EnumRarity.EPIC;
    }
}
