package minefantasy.mf2.block.crafting;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.api.tool.ILighter;
import minefantasy.mf2.block.tileentity.TileEntityFirepit;
import minefantasy.mf2.item.list.CreativeTabMF;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockFirepit extends BlockContainer {

    public static int firepit_RI = 112;
    private Random rand = new Random();

    public BlockFirepit() {
        super(Material.wood);
        setBlockBounds(0.2F, 0F, 0.2F, 0.8F, 0.5F, 0.8F);
        String name = "firepit";
        this.setBlockName(name);
        GameRegistry.registerBlock(this, name);
        this.setLightOpacity(0);
        setHardness(2F);
        this.setCreativeTab(CreativeTabMF.tabUtil);
    }

    @Override
    public int quantityDropped(Random rand) {
        return 0;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float f, float f1,
                                    float f2) {
        TileEntityFirepit firepit = (TileEntityFirepit) world.getTileEntity(x, y, z);

        if (firepit != null) {
            ItemStack held = player.getHeldItem();
            boolean burning = firepit.isBurning();

            if (held != null) {
                if (firepit.addFuel(held) && !player.capabilities.isCreativeMode) {
                    if (!world.isRemote) {
                        if (held.stackSize == 1) {
                            if (held.getItem().getContainerItem(held) != null) {
                                player.setCurrentItemOrArmor(0, held.getItem().getContainerItem(held));
                            } else {
                                player.setCurrentItemOrArmor(0, null);
                            }
                        } else {
                            held.stackSize--;
                            if (held.getItem().getContainerItem(held) != null) {
                                if (!player.inventory.addItemStackToInventory(held.getItem().getContainerItem(held))) {
                                    player.entityDropItem(held.getItem().getContainerItem(held), 0F);
                                }
                            }
                        }
                    }
                    return true;
                }

                if (burning) {
                    if (firepit.tryCook(player, held) && !player.capabilities.isCreativeMode) {
                        ItemStack contain = held.getItem().getContainerItem(held);
                        --held.stackSize;
                        if (held.stackSize <= 0) {
                            if (contain != null) {
                                player.setCurrentItemOrArmor(0, contain);
                            } else
                                player.setCurrentItemOrArmor(0, null);
                        } else if (contain != null) {
                            firepit.dropItem(player, contain);
                        }
                    }
                    return true;
                } else if (firepit.fuel > 0) {
                    if (held.getItem() instanceof ILighter) {
                        world.playSoundEffect(x + 0.5D, y - 0.5D, z + 0.5D, "fire.ignite", 1.0F,
                                rand.nextFloat() * 0.4F + 0.8F);
                        world.spawnParticle("flame", x + 0.5D, y - 0.5D, z + 0.5D, 0F, 0F, 0F);

                        ILighter lighter = (ILighter) held.getItem();
                        if (lighter.canLight()) {
                            if (rand.nextDouble() < lighter.getChance()) {
                                if (!world.isRemote) {
                                    firepit.setLit(true);
                                    held.damageItem(1, player);
                                }
                            }
                            return true;
                        }
                    }
                    if (held.getItem() instanceof ItemFlintAndSteel) {
                        world.playSoundEffect(x + 0.5D, y - 0.5D, z + 0.5D, "fire.ignite", 1.0F,
                                rand.nextFloat() * 0.4F + 0.8F);
                        world.spawnParticle("flame", x + 0.5D, y - 0.5D, z + 0.5D, 0F, 0F, 0F);

                        if (!world.isRemote) {
                            firepit.setLit(true);
                            held.damageItem(1, player);
                        }
                        return true;
                    }
                }
            }
        }
        return super.onBlockActivated(world, x, y, z, player, i, f, f1, f2);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityFirepit();
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return Blocks.planks.getIcon(side, 0);
    }

    public boolean isOpaqueCube() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public int getRenderType() {
        return firepit_RI;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random random) {
        TileEntityFirepit firepit = (TileEntityFirepit) world.getTileEntity(x, y, z);
        if (firepit != null && firepit.isBurning()) {
            world.spawnParticle("smoke", x + 0.5D, y + 0.5D, z + 0.5D, 0.0D, 0.0D, 0.0D);
            world.spawnParticle("flame", x + 0.5D, y + 0.5D, z + 0.5D, 0.0D, 0.0D, 0.0D);
            world.playSound(x, y, z, "fire.fire", 1.0F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F, false);
        }
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z) {
        if (world.getBlockMetadata(x, y, z) == 1) {
            return 15;
        }
        return super.getLightValue(world, x, y, z);
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        if (world.isRemote) {
            return;
        }
        TileEntityFirepit tile = (TileEntityFirepit) world.getTileEntity(x, y, z);
        if (entity == null || tile == null || tile.hasBlockAbove()) {
            return;
        }
        if (entity instanceof EntityItem) {
            entity.motionX = entity.motionY = entity.motionZ = 0;
            entity.posX = x + 0.5D;
            entity.posY = y + 0.75D;
            entity.posZ = z + 0.5D;
        } else {
            if (tile.isBurning()) {
                entity.attackEntityFrom(DamageSource.inFire, 1.0F);
                entity.setFire(2);
            }
        }
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        TileEntityFirepit tile = (TileEntityFirepit) world.getTileEntity(x, y, z);
        if (tile != null) {
            int charcoal = tile.getCharcoalDrop();

            if (charcoal > 0) {
                float xDrop = this.rand.nextFloat() * 0.8F + 0.1F;
                float yDrop = this.rand.nextFloat() * 0.8F + 0.1F;
                float zDrop = this.rand.nextFloat() * 0.8F + 0.1F;

                for (int c = 0; c < charcoal; c++) {
                    EntityItem drop = new EntityItem(world, x + xDrop, y + yDrop, z + zDrop,
                            new ItemStack(Items.coal, 1, 1));

                    float jumpFactor = 0.05F;
                    drop.motionX = (float) this.rand.nextGaussian() * jumpFactor;
                    drop.motionY = (float) this.rand.nextGaussian() * jumpFactor + 0.2F;
                    drop.motionZ = (float) this.rand.nextGaussian() * jumpFactor;
                    world.spawnEntityInWorld(drop);
                }
            }
        }
        super.breakBlock(world, x, y, z, block, meta);
    }
}
