package minefantasy.mf2.item.tool.advanced;

import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.api.stamina.StaminaBar;
import minefantasy.mf2.api.weapon.IRackItem;
import minefantasy.mf2.block.tileentity.decor.TileEntityRack;
import minefantasy.mf2.config.ConfigTools;
import minefantasy.mf2.item.tool.ItemAxeMF;
import minefantasy.mf2.util.BukkitUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

public class ItemLumberAxe extends ItemAxeMF implements IRackItem {
    private Random rand = new Random();

    public ItemLumberAxe(String name, ToolMaterial material, int rarity) {
        super(name, material, rarity);
        this.setMaxDamage(getMaxDamage() * 5);
    }

    public static boolean canAcceptCost(EntityLivingBase user) {
        return canAcceptCost(user, 0.1F);
    }

    public static boolean canAcceptCost(EntityLivingBase user, float cost) {
        if (user instanceof EntityPlayer && StaminaBar.isSystemActive) {
            return StaminaBar.isPercentStamAvailable(user, cost, true);
        }
        return true;
    }

    public static void tirePlayer(EntityLivingBase user, float points) {
        if (user instanceof EntityPlayer && StaminaBar.isSystemActive) {
            StaminaBar.modifyStaminaValue(user, -StaminaBar.getBaseDecayModifier(user, true, true) * points);
            StaminaBar.ModifyIdleTime(user, 5F * points);
        }
    }

    @Override
    public float getScale(ItemStack itemstack) {
        return 2.0F;
    }

    @Override
    public float getOffsetX(ItemStack itemstack) {
        return 0;
    }

    @Override
    public float getOffsetY(ItemStack itemstack) {
        return 0;
    }

    @Override
    public float getOffsetZ(ItemStack itemstack) {
        return 0;
    }

    @Override
    public float getRotationOffset(ItemStack itemstack) {
        return 0;
    }

    @Override
    public boolean canHang(TileEntityRack rack, ItemStack item, int slot) {
        return true;
    }

    @Override
    public boolean isSpecialRender(ItemStack item) {
        return true;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack item, World world, Block block, int x, int y, int z,
                                    EntityLivingBase user) {
        if (user instanceof EntityPlayer && canAcceptCost(user)) {
            breakChain(world, x, y, z, item, block, user, 32, block, world.getBlockMetadata(x, y, z));
        }
        return super.onBlockDestroyed(item, world, block, x, y, z, user);
    }

    private void breakChain(World world, int x, int y, int z, ItemStack item, Block block, EntityLivingBase user,
                            int maxLogs, Block orient, int orientM) {
        if (maxLogs > 0 && isLog(world, x, y, z, orient, orientM)) {
            if (MineFantasyII.isBukkitServer() && BukkitUtils.cantBreakBlock((EntityPlayer) user, x, y, z)) {
                return;
            }

            Block newblock = world.getBlock(x, y, z);
            breakSurrounding(item, world, newblock, x, y, z, user);
            if (rand.nextFloat() * 100F < (100F - ConfigTools.hvyDropChance)) {
                newblock.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z),
                        EnchantmentHelper.getFortuneModifier(user));
            }
            world.setBlockToAir(x, y, z);
            item.damageItem(1, user);

            maxLogs--;
            for (int x1 = -1; x1 <= 1; x1++) {
                for (int y1 = -1; y1 <= 1; y1++) {
                    for (int z1 = -1; z1 <= 1; z1++) {
                        breakChain(world, x + x1, y + y1, z + z1, item, newblock, user, maxLogs, orient, orientM);
                    }
                }
            }
            tirePlayer(user, 0.5F);
        }
    }

    private boolean isLog(World world, int x, int y, int z, Block orient, int orientM) {
        Block block = world.getBlock(x, y, z);
        int meta = world.getBlockMetadata(x, y, z);
        if (block != null) {
            return block == orient && block.getMaterial() == Material.wood;
        }
        return false;
    }

    public void breakSurrounding(ItemStack item, World world, Block block, int x, int y, int z, EntityLivingBase user) {
        if (!world.isRemote && ForgeHooks.isToolEffective(item, block, world.getBlockMetadata(x, y, z))) {
            for (int x1 = -2; x1 <= 2; x1++) {
                for (int y1 = -2; y1 <= 2; y1++) {
                    for (int z1 = -2; z1 <= 2; z1++) {
                        ForgeDirection FD = getFDFor(user);
                        int blockX = x + x1 + FD.offsetX;
                        int blockY = y + y1 + FD.offsetY;
                        int blockZ = z + z1 + FD.offsetZ;

                        if (!(x1 + FD.offsetX == 0 && y1 + FD.offsetY == 0 && z1 + FD.offsetZ == 0)) {
                            if (MineFantasyII.isBukkitServer() && BukkitUtils.cantBreakBlock((EntityPlayer) user, blockX, blockY, blockZ)) {
                                break;
                            }

                            Block newblock = world.getBlock(blockX, blockY, blockZ);
                            int m = world.getBlockMetadata(blockX, blockY, blockZ);

                            if (item.getItemDamage() < item.getMaxDamage() && newblock != null
                                    && user instanceof EntityPlayer && newblock.getMaterial() == Material.leaves) {
                                if (rand.nextFloat() * 100F < (100F - ConfigTools.hvyDropChance)) {
                                    newblock.dropBlockAsItem(world, blockX, blockY, blockZ, m,
                                            EnchantmentHelper.getFortuneModifier(user));
                                }
                                world.setBlockToAir(blockX, blockY, blockZ);
                                item.damageItem(1, user);
                            }
                        }
                    }
                }
            }
        }
    }

    private ForgeDirection getFDFor(EntityLivingBase user) {
        return ForgeDirection.UNKNOWN;// TODO: FD
    }

}
