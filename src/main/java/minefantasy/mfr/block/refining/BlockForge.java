package minefantasy.mfr.block.refining;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.api.heating.ForgeFuel;
import minefantasy.mfr.api.heating.ForgeItemHandler;
import minefantasy.mfr.api.heating.Heatable;
import minefantasy.mfr.api.heating.TongsHelper;
import minefantasy.mfr.api.knowledge.ResearchLogic;
import minefantasy.mfr.block.tile.TileEntityForge;
import minefantasy.mfr.init.BlockListMFR;
import minefantasy.mfr.init.ComponentListMFR;
import minefantasy.mfr.init.CreativeTabMFR;
import minefantasy.mfr.item.armour.ItemApron;
import minefantasy.mfr.item.tool.ItemLighterMF;
import minefantasy.mfr.item.tool.crafting.ItemTongs;
import minefantasy.mfr.init.KnowledgeListMFR;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockForge extends BlockContainer {
    public static EnumBlockRenderType forge_RI;
    private static boolean keepInventory;
    public final boolean isActive;
    public int tier;
    public String type;
    private Random rand = new Random();

    public BlockForge(String tex, int tier, boolean isActive) {
        super(tier == 1 ? Material.IRON : Material.ROCK);
        this.tier = tier;
        this.type = tex;
        this.isActive = isActive;

        setRegistryName("MF_Forge" + tex + (isActive ? "Active" : ""));
        setUnlocalizedName("forge." + tex);
        this.setSoundType(SoundType.STONE);
        this.setHardness(5F);
        this.setResistance(8F);
        this.setCreativeTab(CreativeTabMFR.tabUtil);
        new AxisAlignedBB(0F, 0F, 0F, 1F, 0.5F, 1F);
        this.setLightOpacity(0);
    }

    private static TileEntityForge getTile(IBlockAccess world, BlockPos pos) {
        return (TileEntityForge) world.getTileEntity(pos);
    }

    public static void updateFurnaceBlockState(boolean state, World world, BlockPos pos) {
        TileEntityForge tileentity = getTile(world, pos);
        keepInventory = true;
        Block block = world.getBlockState(pos).getBlock();

        if (block != null && block instanceof BlockForge) {
            int blocktier = ((BlockForge) block).tier;
            if (state) {
                world.setBlockState(pos, (getActiveBlock(blocktier)).getDefaultState());
            } else {
                world.setBlockState(pos, (getInactiveBlock(blocktier)).getDefaultState());
            }
        }

        keepInventory = false;

        if (tileentity != null) {
            tileentity.validate();
            world.setTileEntity(pos, tileentity);
        }
    }

    private static Block getActiveBlock(int tier) {
        return tier == 1 ? BlockListMFR.FORGE_METAL_ACTIVE : BlockListMFR.FORGE_ACTIVE;
    }

    private static Block getInactiveBlock(int tier) {
        return tier == 1 ? BlockListMFR.FORGE_METAL : BlockListMFR.FORGE;
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> item) {
        if (!isActive) {
            super.getSubBlocks(tab, item);
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityForge();
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        if (keepInventory)
            return;

        TileEntityForge tile = getTile(world, pos);

        if (tile != null) {
            for (int i1 = 0; i1 < tile.getSizeInventory(); ++i1) {
                ItemStack itemstack = tile.getStackInSlot(i1);

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
                            entityitem.getItem().setTagCompound( itemstack.getTagCompound().copy());
                        }

                        float f3 = 0.05F;
                        entityitem.motionX = (float) this.rand.nextGaussian() * f3;
                        entityitem.motionY = (float) this.rand.nextGaussian() * f3 + 0.2F;
                        entityitem.motionZ = (float) this.rand.nextGaussian() * f3;
                        world.spawnEntity(entityitem);
                    }
                }
            }
        }

        super.breakBlock(world,pos, state);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer user, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack held = user.getHeldItem(hand);
        TileEntityForge tile = getTile(world,pos);
        if (tile != null) {
            if (tile.isLit() && !ItemApron.isUserProtected(user)) {
                user.setFire(5);
                user.attackEntityFrom(DamageSource.ON_FIRE, 1.0F);
            }
            if (held != null) {
                if (facing == EnumFacing.UP && held != null && held.getItem() instanceof ItemTongs
                        && onUsedTongs(world, user, held, tile)) {
                    return true;
                }
                int l = ItemLighterMF.tryUse(held, user);
                if (!isActive && l != 0) {
                    user.playSound(SoundEvents.ITEM_FIRECHARGE_USE, 1.0F, 1.0F);
                    if (l == 1) {
                        tile.fireUpForge();
                        held.damageItem(1, user);
                    }
                    return true;
                }
                if (Heatable.canHeatItem(held) && tile.tryAddHeatable(held)) {
                    held.shrink(1);
                    if (held.getCount() <= 0) {
                        user.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, null);
                    }
                    return true;
                }

                ForgeFuel stats = ForgeItemHandler.getStats(held);
                if (stats != null && tile.addFuel(stats, true, tier)) {
                    if (user.capabilities.isCreativeMode) {
                        return true;
                    }
                    if (user.getHeldItem(hand).getItem().getContainerItem() != null) {
                        ItemStack cont = new ItemStack(user.getHeldItem(hand).getItem().getContainerItem());
                        if (user.getHeldItem(hand).getCount() == 1) {
                            user.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, cont);
                            return true;
                        } else {
                            if (!user.inventory.addItemStackToInventory(cont)) {
                                user.entityDropItem(cont, 0.0F);
                            }
                        }
                    }
                    if (user.getHeldItem(hand).getCount() == 1) {
                        user.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, null);
                    } else {
                        user.getHeldItem(hand).shrink(1);
                    }
                    return true;
                }
                if (!world.isRemote && ResearchLogic.hasInfoUnlocked(user, KnowledgeListMFR.smeltDragonforge)
                        && held.getItem() == ComponentListMFR.DRAGON_HEART) {
                    if (user.getHeldItem(hand).getCount() == 1) {
                        user.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, null);
                    } else {
                        user.getHeldItem(hand).shrink(1);
                    }
                    tile.dragonHeartPower = 1.0F;
                    return true;
                }
            }
            if (!world.isRemote && !tile.hasBlockAbove()) {
                user.openGui(MineFantasyReborn.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }

    private boolean onUsedTongs(World world, EntityPlayer user, ItemStack held, TileEntityForge tile) {
        ItemStack contents = tile.getStackInSlot(0);
        ItemStack grabbed = TongsHelper.getHeldItem(held);

        // GRAB
        if (grabbed == null) {
            if (contents != null && contents.getItem() == ComponentListMFR.HOT_ITEM) {
                if (TongsHelper.trySetHeldItem(held, contents)) {
                    tile.setInventorySlotContents(0, null);
                    return true;
                }
            }
        } else {
            if (contents == null) {
                tile.setInventorySlotContents(0, grabbed);
                TongsHelper.clearHeldItem(held, user);
                return true;
            }
        }
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(getInactiveBlock(tier));
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(getInactiveBlock(tier));
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return forge_RI;
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
        if (tier == 1 && !world.isRemote) {
            if (this.isActive && !world.isBlockPowered(pos)) {
                world.scheduleBlockUpdate(pos, this, 1, 4);
            } else if (!this.isActive && world.isBlockPowered(pos)) {
                updateFurnaceBlockState(true, world, pos);
            }
        }
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which
     * neighbor changed (coordinates passed are their own) Args: x, y, z, neighbor
     * Block
     */
    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (tier == 1 && !world.isRemote) {
            if (this.isActive && !world.isBlockPowered(pos)) {
                world.scheduleBlockUpdate(pos, this, 1, 4);
            } else if (!this.isActive && world.isBlockPowered(pos)) {
                updateFurnaceBlockState(true, world, pos);
            }
        }
    }

    /**
     * Ticks the block if it's been scheduled
     */
    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        if (tier == 1 && !world.isRemote && this.isActive && !world.isBlockPowered(pos)) {
            updateFurnaceBlockState(false, world, pos);
        }
    }

}
