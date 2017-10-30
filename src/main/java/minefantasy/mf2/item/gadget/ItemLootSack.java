package minefantasy.mf2.item.gadget;

import cpw.mods.fml.common.registry.GameRegistry;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.block.list.BlockListMF;
import minefantasy.mf2.item.list.ComponentListMF;
import minefantasy.mf2.item.list.CreativeTabMF;
import minefantasy.mf2.item.list.ToolListMF;
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
        this.setCreativeTab(CreativeTabMF.tabGadget);
        this.amount = amount;
        this.tier = tier;
        pool = tier == 0 ? COMMON : tier == 1 ? VALUABLE : EXQUISITE;
        setTextureName("minefantasy2:Other/" + name);
        GameRegistry.registerItem(this, name, MineFantasyII.MODID);
        this.setUnlocalizedName(name);
    }

    public static void addItems() {
        String loot = COMMON; // Some books, Commodities
        ChestGenHooks.addItem(loot,
                new WeightedRandomChestContent(new ItemStack(ToolListMF.skillbook_artisanry), 1, 1, 5));
        ChestGenHooks.addItem(loot,
                new WeightedRandomChestContent(new ItemStack(ToolListMF.skillbook_construction), 1, 1, 5));
        ChestGenHooks.addItem(loot,
                new WeightedRandomChestContent(new ItemStack(ToolListMF.skillbook_provisioning), 1, 1, 5));
        ChestGenHooks.addItem(loot,
                new WeightedRandomChestContent(new ItemStack(ToolListMF.skillbook_engineering), 1, 1, 5));
        ChestGenHooks.addItem(loot,
                new WeightedRandomChestContent(new ItemStack(ToolListMF.skillbook_combat), 1, 1, 5));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(BlockListMF.repair_basic), 1, 1, 10));

        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(Items.coal), 4, 12, 20));
        ChestGenHooks.addItem(loot,
                new WeightedRandomChestContent(ComponentListMF.plank.construct("OakWood"), 1, 8, 20));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(ComponentListMF.bar("Iron"), 1, 4, 20));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(Items.leather), 1, 4, 20));

        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(Items.string), 1, 2, 10));
        ChestGenHooks.addItem(loot,
                new WeightedRandomChestContent(ComponentListMF.plank.construct("RefinedWood"), 1, 2, 10));

        loot = VALUABLE; // Books, Valued commodities
        ChestGenHooks.addItem(loot,
                new WeightedRandomChestContent(new ItemStack(ToolListMF.skillbook_artisanry), 1, 3, 5));
        ChestGenHooks.addItem(loot,
                new WeightedRandomChestContent(new ItemStack(ToolListMF.skillbook_construction), 1, 3, 5));
        ChestGenHooks.addItem(loot,
                new WeightedRandomChestContent(new ItemStack(ToolListMF.skillbook_provisioning), 1, 3, 5));
        ChestGenHooks.addItem(loot,
                new WeightedRandomChestContent(new ItemStack(ToolListMF.skillbook_engineering), 1, 3, 5));
        ChestGenHooks.addItem(loot,
                new WeightedRandomChestContent(new ItemStack(ToolListMF.skillbook_combat), 1, 3, 5));
        ChestGenHooks.addItem(loot,
                new WeightedRandomChestContent(new ItemStack(BlockListMF.repair_advanced), 1, 1, 2));

        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(ComponentListMF.kaolinite), 1, 8, 10));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(Items.dye, 1, 4), 1, 8, 20));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(ComponentListMF.bar("Gold"), 1, 4, 20));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(ComponentListMF.bar("Silver"), 1, 4, 20));// Silver

        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(Items.diamond), 1, 2, 10));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(Items.emerald), 1, 2, 10));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(Items.ender_pearl), 1, 2, 10));
        ChestGenHooks.addItem(loot,
                new WeightedRandomChestContent(new ItemStack(ComponentListMF.talisman_lesser), 1, 4, 10));
        addRecords(loot, 2);

        loot = EXQUISITE; // Many books, Highly sought commodities
        ChestGenHooks.addItem(loot,
                new WeightedRandomChestContent(new ItemStack(ToolListMF.skillbook_artisanry), 2, 5, 5));
        ChestGenHooks.addItem(loot,
                new WeightedRandomChestContent(new ItemStack(ToolListMF.skillbook_construction), 2, 5, 5));
        ChestGenHooks.addItem(loot,
                new WeightedRandomChestContent(new ItemStack(ToolListMF.skillbook_provisioning), 2, 5, 5));
        ChestGenHooks.addItem(loot,
                new WeightedRandomChestContent(new ItemStack(ToolListMF.skillbook_engineering), 2, 5, 5));
        ChestGenHooks.addItem(loot,
                new WeightedRandomChestContent(new ItemStack(ToolListMF.skillbook_combat), 2, 5, 5));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(BlockListMF.repair_ornate), 1, 1, 5));

        ChestGenHooks.addItem(loot,
                new WeightedRandomChestContent(new ItemStack(ComponentListMF.flux_strong), 12, 32, 20));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(Items.diamond), 4, 8, 20));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(Items.emerald), 4, 8, 20));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(Items.ender_eye), 4, 8, 20));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(ComponentListMF.bar("Silver"), 1, 4, 20));// Wolframite

        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(Items.golden_apple, 1, 1), 1, 1, 10));
        addRecords(loot, 5);

    }

    private static void addRecords(String loot, int rarity) {
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(Items.record_11), 1, 1, rarity));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(Items.record_13), 1, 1, rarity));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(Items.record_blocks), 1, 1, rarity));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(Items.record_cat), 1, 1, rarity));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(Items.record_chirp), 1, 1, rarity));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(Items.record_far), 1, 1, rarity));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(Items.record_mall), 1, 1, rarity));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(Items.record_mellohi), 1, 1, rarity));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(Items.record_stal), 1, 1, rarity));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(Items.record_strad), 1, 1, rarity));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(Items.record_wait), 1, 1, rarity));
        ChestGenHooks.addItem(loot, new WeightedRandomChestContent(new ItemStack(Items.record_ward), 1, 1, rarity));
    }

    @Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer user) {
        if (user.isSwingInProgress)
            return item;

        user.swingItem();
        if (!user.capabilities.isCreativeMode) {
            --item.stackSize;
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
        return tier == 0 ? EnumRarity.common : tier == 1 ? EnumRarity.uncommon : EnumRarity.epic;
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
            contents[slot].stackSize -= num;
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
