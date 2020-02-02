package minefantasy.mfr.item.gadget;

import net.minecraftforge.fml.common.registry.GameRegistry;
import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.init.BlockListMFR;
import minefantasy.mfr.init.ComponentListMFR;
import minefantasy.mfr.init.CreativeTabMFR;
import minefantasy.mfr.init.ToolListMFR;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;

import java.util.Random;

public class ItemLootSack extends Item {
    public static final String COMMON = "commonLootBag";
    public static final String VALUABLE = "valuableLootBag";
    public static final String EXQUISITE = "exquisiteLootBag";

    protected int amount, tier;
    protected String pool;

    public ItemLootSack(String name, int amount, int tier) {
        this.setCreativeTab(CreativeTabMFR.tabGadget);
        this.amount = amount;
        this.tier = tier;
        pool = tier == 0 ? COMMON : tier == 1 ? VALUABLE : EXQUISITE;
        setTextureName("minefantasy2:Other/" + name);
        GameRegistry.registerItem(this, name, MineFantasyReborn.MODID);
        this.setUnlocalizedName(name);
    }

    public static void addItems() {
        String loot = COMMON; // Some books, Commodities
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(ToolListMFR.skillbook_artisanry), 1, 1, 5));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(ToolListMFR.skillbook_construction), 1, 1, 5));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(ToolListMFR.skillbook_provisioning), 1, 1, 5));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(ToolListMFR.skillbook_engineering), 1, 1, 5));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(ToolListMFR.skillbook_combat), 1, 1, 5));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(BlockListMFR.REPAIR_BASIC), 1, 1, 10));

        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(Items.COAL), 4, 12, 20));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(ComponentListMFR.plank.construct("OakWood"), 1, 8, 20));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(ComponentListMFR.bar("Iron"), 1, 4, 20));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(Items.LEATHER), 1, 4, 20));

        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(Items.STRING), 1, 2, 10));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(ComponentListMFR.plank.construct("RefinedWood"), 1, 2, 10));

        loot = VALUABLE; // Books, Valued commodities
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(ToolListMFR.skillbook_artisanry), 1, 3, 5));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(ToolListMFR.skillbook_construction), 1, 3, 5));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(ToolListMFR.skillbook_provisioning), 1, 3, 5));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(ToolListMFR.skillbook_engineering), 1, 3, 5));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(ToolListMFR.skillbook_combat), 1, 3, 5));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(BlockListMFR.REPAIR_ADVANCED), 1, 1, 2));

        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(ComponentListMFR.kaolinite), 1, 8, 10));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(Items.DYE, 1, 4), 1, 8, 20));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(ComponentListMFR.bar("Gold"), 1, 4, 20));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(ComponentListMFR.bar("Silver"), 1, 4, 20));// Silver

        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(Items.DIAMOND), 1, 2, 10));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(Items.EMERALD), 1, 2, 10));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(Items.ENDER_PEARL), 1, 2, 10));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(ComponentListMFR.talisman_lesser), 1, 4, 10));
        addRecords(loot, 2);

        loot = EXQUISITE; // Many books, Highly sought commodities
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(ToolListMFR.skillbook_artisanry), 2, 5, 5));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(ToolListMFR.skillbook_construction), 2, 5, 5));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(ToolListMFR.skillbook_provisioning), 2, 5, 5));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(ToolListMFR.skillbook_engineering), 2, 5, 5));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(ToolListMFR.skillbook_combat), 2, 5, 5));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(BlockListMFR.REPAIR_ORNATE), 1, 1, 5));

        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(ComponentListMFR.flux_strong), 12, 32, 20));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(Items.DIAMOND), 4, 8, 20));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(Items.EMERALD), 4, 8, 20));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(Items.ENDER_EYE), 4, 8, 20));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(ComponentListMFR.bar("Silver"), 1, 4, 20));// Wolframite

        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(Items.GOLDEN_APPLE, 1, 1), 1, 1, 10));
        addRecords(loot, 5);

    }

    private static void addRecords(String loot, int rarity) {
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(Items.RECORD_11), 1, 1, rarity));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(Items.RECORD_13), 1, 1, rarity));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(Items.RECORD_BLOCKS), 1, 1, rarity));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(Items.RECORD_CAT), 1, 1, rarity));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(Items.RECORD_CHIRP), 1, 1, rarity));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(Items.RECORD_FAR), 1, 1, rarity));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(Items.RECORD_MALL), 1, 1, rarity));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(Items.RECORD_MELLOHI), 1, 1, rarity));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(Items.RECORD_STAL), 1, 1, rarity));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(Items.RECORD_STRAD), 1, 1, rarity));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(Items.RECORD_WAIT), 1, 1, rarity));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(Items.RECORD_WARD), 1, 1, rarity));
    }

    @Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer user) {
        if (user.isSwingInProgress)
            return item;

        user.swingItem();
        if (!user.capabilities.isCreativeMode) {
            item.shrink(1);
        }
        user.playSound("mob.horse.leather", 1.0F, 1.5F);

        if (!world.isRemote) {
            ChestGenHooks gen = ChestGenHooks.getInfo(pool);
            IInventory inv = new Loot(amount);
            Random rand = new Random();
            WeightedRandomChestContent.generateChestContents(rand, gen.getItems(rand), inv, 1 + rand.nextInt(amount));
            for (int a = 0; a < inv.getSizeInventory(); a++) {
                if (inv.getStackInSlot(a) != null) {
                    ItemStack drop = inv.getStackInSlot(a);
                    if (!user.inventory.addItemStackToInventory(drop)) {
                        user.entityDropItem(drop, 0F);
                    }
                }
            }
        }
        return item;
    }

    @Override
    public EnumRarity getRarity(ItemStack item) {
        return tier == 0 ? EnumRarity.COMMON : tier == 1 ? EnumRarity.UNCOMMON : EnumRarity.EPIC;
    }

    public static class Loot implements IInventory {
        public ItemStack[] contents;

        public Loot(int count) {
            contents = new ItemStack[count];
        }

        @Override
        public int getSizeInventory() {
            return contents.length;
        }

        @Override
        public ItemStack getStackInSlot(int slot) {
            return contents[slot];
        }

        @Override
        public ItemStack decrStackSize(int slot, int num) {
            contents[slot].shrink(num);
            return contents[slot];
        }

        @Override
        public ItemStack getStackInSlotOnClosing(int slot) {
            return null;
        }

        @Override
        public void setInventorySlotContents(int slot, ItemStack item) {
            contents[slot] = item;
        }

        @Override
        public String getInventoryName() {
            return null;
        }

        @Override
        public boolean hasCustomInventoryName() {
            return false;
        }

        @Override
        public int getInventoryStackLimit() {
            return 64;
        }

        @Override
        public void markDirty() {
            // TODO Auto-generated method stub

        }

        @Override
        public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
            return false;
        }

        @Override
        public void openInventory() {
        }

        @Override
        public void closeInventory() {
        }

        @Override
        public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
            return false;
        }

    }
}
