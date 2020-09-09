package minefantasy.mfr.tile;

import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import minefantasy.mfr.api.cooking.CookRecipe;
import minefantasy.mfr.api.crafting.IHeatSource;
import minefantasy.mfr.api.crafting.IHeatUser;
import minefantasy.mfr.block.crafting.BlockRoast;
import minefantasy.mfr.init.BlockListMFR;
import minefantasy.mfr.proxy.NetworkUtils;
import minefantasy.mfr.network.TileInventoryPacket;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;

import java.util.Random;

public class TileEntityRoast extends TileEntity implements IInventory, IHeatUser, ITickable {
    /**
     * Enable high temperatures ruin cooking
     */
    public static boolean enableOverheat = true;
    public ItemStack[] items = new ItemStack[1];
    public float progress;
    public float maxProgress;
    public String texname = "basic";
    private int tempTicksExisted = 0;
    private Random rand = new Random();
    private int ticksExisted;
    private CookRecipe recipe;
    private boolean isOvenTemp;

    public TileEntityRoast() {
    }

    @Override
    public void update() {
        ++tempTicksExisted;
        if (tempTicksExisted == 10) {
            updateRecipe();
        }
        int temp = getTemp();
        ++ticksExisted;
        if (ticksExisted % 20 == 0 && !world.isRemote) {
            if (recipe != null && temp > 0 && maxProgress > 0 && temp > recipe.minTemperature) {
                if (enableOverheat && recipe.canBurn && temp > recipe.maxTemperature) {
                    setInventorySlotContents(0, recipe.burnt.copy());
                    updateRecipe();
                }
                progress += (temp / 100F);
                if (progress >= maxProgress) {
                    setInventorySlotContents(0, recipe.output.copy());
                    updateRecipe();
                }
            }
        }
        if (temp > 0 && world.isRemote && rand.nextInt(20) == 0) {
            world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.2F + (rand.nextFloat() * 0.6F), pos.getY() + 0.2F,
                    pos.getZ() + 0.2F + (rand.nextFloat() * 0.6F), 0F, 0F, 0F);
        }
    }

    private int getTemp() {
        TileEntity tile = world.getTileEntity(pos.add(0,-1,0));
        if (tile != null && tile instanceof IHeatSource) {
            return ((IHeatSource) tile).getHeat();
        }
        return 0;
    }

    public TileEntityRoast setInventoryModel(String tex, boolean isOven) {
        this.isOvenTemp = isOven;
        this.texname = tex;
        return this;
    }

    public boolean isOven() {
        if (world == null)
            return isOvenTemp;
        Block base = world.getBlockState(pos).getBlock();
        if (base instanceof BlockRoast) {
            return ((BlockRoast) base).isOven();
        }
        return false;
    }

    public boolean interact(EntityPlayer player) {
        ItemStack held = player.getHeldItemMainhand();
        ItemStack item = items[0];
        if (item == null) {
            if (held != null && !(held.getItem() instanceof ItemBlock)
                    && CookRecipe.getResult(held, isOven()) != null) {
                ItemStack item2 = held.copy();
                item2.setCount(1);
                setInventorySlotContents(0, item2);
                tryDecrMainItem(player);
                updateRecipe();
                if (!isOven() && this.getTemp() > 0) {
                    world.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.AMBIENT, 1.0F, 1.0F, false);
                }
                return true;
            }
        } else {
            if (!player.inventory.addItemStackToInventory(item)) {
                player.entityDropItem(item, 0.0F);
            }
            setInventorySlotContents(0, null);
            updateRecipe();
            return true;
        }
        return false;
    }

    private void tryDecrMainItem(EntityPlayer player) {
        int held = player.inventory.currentItem;
        if (held >= 0 && held < 9) {
            player.inventory.decrStackSize(held, 1);
        }
    }

    public void updateRecipe() {
        recipe = CookRecipe.getResult(getStackInSlot(0), isOven());
        if (recipe != null) {
            maxProgress = recipe.time;
        }
        progress = 0;
        sendPacketToClients();
    }

    public int getProgressBar(int i) {
        if (maxProgress <= 0)
            return 0;
        return (int) (i / maxProgress * progress);
    }

    public String getResultName() {
        if (recipe != null) {
            return recipe.output.getDisplayName();
        }
        return "";
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        progress = nbt.getFloat("Progress");
        maxProgress = nbt.getFloat("maxProgress");

        NBTTagList savedItems = nbt.getTagList("Items", 10);

        for (int i = 0; i < savedItems.tagCount(); ++i) {
            NBTTagCompound savedSlot = savedItems.getCompoundTagAt(i);
            byte slotNum = savedSlot.getByte("Slot");

            if (slotNum >= 0 && slotNum < items.length) {
                items[slotNum] = new ItemStack(savedSlot);
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setFloat("Progress", progress);
        nbt.setFloat("maxProgress", maxProgress);

        NBTTagList savedItems = new NBTTagList();

        for (int i = 0; i < items.length; ++i) {
            if (items[i] != null) {
                NBTTagCompound savedSlot = new NBTTagCompound();
                savedSlot.setByte("Slot", (byte) i);
                items[i].writeToNBT(savedSlot);
                savedItems.appendTag(savedSlot);
            }
        }

        nbt.setTag("Items", savedItems);
        return nbt;
    }

    // INVENTORY
    public void onInventoryChanged() {
    }

    @Override
    public int getSizeInventory() {
        return items.length;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return items[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int num) {
        onInventoryChanged();
        if (this.items[slot] != null) {
            ItemStack itemstack;

            if (this.items[slot].getCount() <= num) {
                itemstack = this.items[slot];
                this.items[slot] = null;
                return itemstack;
            } else {
                itemstack = this.items[slot].splitStack(num);

                if (this.items[slot].getCount() == 0) {
                    this.items[slot] = null;
                }

                return itemstack;
            }
        } else {
            return null;
        }
    }

    @Override
    public ItemStack removeStackFromSlot(int slot) {
        return items[slot];
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack item) {
        onInventoryChanged();
        items[slot] = item;
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer user) {
        return user.getDistance(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) < 8D;
    }

    @Override
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack item) {
        return false;
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {

    }

    private void dropItem(ItemStack itemstack) {
        if (itemstack != null) {
            float f = this.rand.nextFloat() * 0.8F + 0.1F;
            float f1 = this.rand.nextFloat() * 0.8F + 0.1F;
            float f2 = this.rand.nextFloat() * 0.8F + 0.1F;

            while (itemstack.getCount() > 0) {
                int j1 = this.rand.nextInt(21) + 10;

                if (j1 > itemstack.getCount()) {
                    j1 = itemstack.getCount();
                }

                itemstack.shrink(j1);
                EntityItem entityitem = new EntityItem(world, pos.getX() + f, pos.getY() + f1, pos.getZ() + f2,
                        new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

                if (itemstack.hasTagCompound()) {
                    entityitem.getItem().setTagCompound(itemstack.getTagCompound().copy());
                }

                float f3 = 0.05F;
                entityitem.motionX = (float) this.rand.nextGaussian() * f3;
                entityitem.motionY = (float) this.rand.nextGaussian() * f3 + 0.2F;
                entityitem.motionZ = (float) this.rand.nextGaussian() * f3;
                world.spawnEntity(entityitem);
            }
        }
    }

    @Override
    public boolean canAccept(TileEntity tile) {
        return true;
    }

    private void sendPacketToClients() {
        if (world.isRemote)
            return;

        NetworkUtils.sendToWatchers(new TileInventoryPacket(this, this).generatePacket(), (WorldServer) world, this.pos);

		/*
        List<EntityPlayer> players = ((WorldServer) worldObj).playerEntities;
		for (int i = 0; i < players.size(); i++) {
			EntityPlayer player = players.get(i);
			((WorldServer) worldObj).getEntityTracker().func_151248_b(player,
					new TileInventoryPacket(this, this).generatePacket());
		}
		*/
    }

    @SideOnly(Side.CLIENT)
    public String getTexName() {
        if (world == null)
            return texname;

        Block base = world.getBlockState(pos).getBlock();
        if (base instanceof BlockRoast) {
            return ((BlockRoast) base).tex;
        }
        return "basic";
    }

    @Override
    public Block getBlockType() {
        if (world == null)
            return BlockListMFR.OVEN_STONE;

        return super.getBlockType();
    }

    @Override
    public int getBlockMetadata() {
        if (world == null)
            return 0;
        return super.getBlockMetadata();
    }

    @Override
    public String getName() {
        return "tile.roast.name";
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }
}
