package minefantasy.mf2.block.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.api.cooking.CookRecipe;
import minefantasy.mf2.api.crafting.IHeatSource;
import minefantasy.mf2.api.crafting.IHeatUser;
import minefantasy.mf2.block.crafting.BlockRoast;
import minefantasy.mf2.block.list.BlockListMF;
import minefantasy.mf2.network.NetworkUtils;
import minefantasy.mf2.network.packet.TileInventoryPacket;
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

public class TileEntityRoast extends TileEntity implements IInventory, IHeatUser {
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
    public void updateEntity() {
        super.updateEntity();
        ++tempTicksExisted;
        if (tempTicksExisted == 10) {
            blockMetadata = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
            updateRecipe();
        }
        int temp = getTemp();
        ++ticksExisted;
        if (ticksExisted % 20 == 0 && !worldObj.isRemote) {
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
        if (temp > 0 && worldObj.isRemote && rand.nextInt(20) == 0) {
            worldObj.spawnParticle("flame", xCoord + 0.2F + (rand.nextFloat() * 0.6F), yCoord + 0.2F,
                    zCoord + 0.2F + (rand.nextFloat() * 0.6F), 0F, 0F, 0F);
        }
    }

    private int getTemp() {
        TileEntity tile = worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
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
        if (worldObj == null)
            return isOvenTemp;
        Block base = worldObj.getBlock(xCoord, yCoord, zCoord);
        if (base instanceof BlockRoast) {
            return ((BlockRoast) base).isOven();
        }
        return false;
    }

    public boolean interact(EntityPlayer player) {
        ItemStack held = player.getHeldItem();
        ItemStack item = items[0];
        if (item == null) {
            if (held != null && !(held.getItem() instanceof ItemBlock)
                    && CookRecipe.getResult(held, isOven()) != null) {
                ItemStack item2 = held.copy();
                item2.stackSize = 1;
                setInventorySlotContents(0, item2);
                tryDecrMainItem(player);
                updateRecipe();
                if (!isOven() && this.getTemp() > 0) {
                    worldObj.playSoundEffect(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, "random.fizz", 1.0F, 1.0F);
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
                items[slotNum] = ItemStack.loadItemStackFromNBT(savedSlot);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
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
    }

    // INVENTORY
    public void onInventoryChanged() {
    }

    @Override
    public int getSizeInventory() {
        return items.length;
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

            if (this.items[slot].stackSize <= num) {
                itemstack = this.items[slot];
                this.items[slot] = null;
                return itemstack;
            } else {
                itemstack = this.items[slot].splitStack(num);

                if (this.items[slot].stackSize == 0) {
                    this.items[slot] = null;
                }

                return itemstack;
            }
        } else {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        return items[slot];
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack item) {
        onInventoryChanged();
        items[slot] = item;
    }

    @Override
    public String getInventoryName() {
        return "tile.roast.name";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer user) {
        return user.getDistance(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) < 8D;
    }

    @Override
    public void openInventory() {
    }

    @Override
    public void closeInventory() {
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack item) {
        return false;
    }

    private void dropItem(ItemStack itemstack) {
        if (itemstack != null) {
            float f = this.rand.nextFloat() * 0.8F + 0.1F;
            float f1 = this.rand.nextFloat() * 0.8F + 0.1F;
            float f2 = this.rand.nextFloat() * 0.8F + 0.1F;

            while (itemstack.stackSize > 0) {
                int j1 = this.rand.nextInt(21) + 10;

                if (j1 > itemstack.stackSize) {
                    j1 = itemstack.stackSize;
                }

                itemstack.stackSize -= j1;
                EntityItem entityitem = new EntityItem(worldObj, xCoord + f, yCoord + f1, zCoord + f2,
                        new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

                if (itemstack.hasTagCompound()) {
                    entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
                }

                float f3 = 0.05F;
                entityitem.motionX = (float) this.rand.nextGaussian() * f3;
                entityitem.motionY = (float) this.rand.nextGaussian() * f3 + 0.2F;
                entityitem.motionZ = (float) this.rand.nextGaussian() * f3;
                worldObj.spawnEntityInWorld(entityitem);
            }
        }
    }

    @Override
    public boolean canAccept(TileEntity tile) {
        return true;
    }

    private void sendPacketToClients() {
        if (worldObj.isRemote)
            return;

        NetworkUtils.sendToWatchers(new TileInventoryPacket(this, this).generatePacket(), (WorldServer) worldObj, this.xCoord, this.zCoord);

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
        if (worldObj == null)
            return texname;

        Block base = worldObj.getBlock(xCoord, yCoord, zCoord);
        if (base instanceof BlockRoast) {
            return ((BlockRoast) base).tex;
        }
        return "basic";
    }

    @Override
    public Block getBlockType() {
        if (worldObj == null)
            return BlockListMF.oven_stone;

        return super.getBlockType();
    }

    @Override
    public int getBlockMetadata() {
        if (worldObj == null)
            return 0;
        return super.getBlockMetadata();
    }
}
