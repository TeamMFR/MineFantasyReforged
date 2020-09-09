package minefantasy.mfr.item.tool.advanced;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.api.stamina.StaminaBar;
import minefantasy.mfr.api.weapon.IRackItem;
import minefantasy.mfr.tile.decor.TileEntityRack;
import minefantasy.mfr.config.ConfigTools;
import minefantasy.mfr.item.tool.ItemAxeMF;
import minefantasy.mfr.util.BukkitUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import java.util.Random;

public class ItemLumberAxe extends ItemAxeMF implements IRackItem {
    private Random rand = new Random();

    public ItemLumberAxe(String name, Item.ToolMaterial material, int rarity) {
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
    public boolean onBlockDestroyed(ItemStack item, World world, IBlockState block, BlockPos pos, EntityLivingBase user) {
        if (user instanceof EntityPlayer && canAcceptCost(user)) {
            breakChain(world, pos, item, block, user, 32, block.getBlock());
        }
        return super.onBlockDestroyed(item, world, block, pos, user);
    }

    private void breakChain(World world, BlockPos pos, ItemStack item, IBlockState state, EntityLivingBase user, int maxLogs, Block orient) {
        if (maxLogs > 0 && isLog(world, pos, orient)) {
            if (MineFantasyReborn.isBukkitServer() && BukkitUtils.cantBreakBlock((EntityPlayer) user, pos)) {
                return;
            }

            IBlockState newblock = world.getBlockState(pos);
            breakSurrounding(item, world, newblock, pos, user);
            if (rand.nextFloat() * 100F < (100F - ConfigTools.hvyDropChance)) {
                newblock.getBlock().dropBlockAsItem(world, pos, world.getBlockState(pos), EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(35), item));
            }
            world.setBlockToAir(pos);
            item.damageItem(1, user);

            maxLogs--;
            for (int x1 = -1; x1 <= 1; x1++) {
                for (int y1 = -1; y1 <= 1; y1++) {
                    for (int z1 = -1; z1 <= 1; z1++) {
                        breakChain(world, pos.add(x1,y1,z1), item, newblock, user, maxLogs, orient);
                    }
                }
            }
            tirePlayer(user, 0.5F);
        }
    }

    private boolean isLog(World world,BlockPos pos, Block orient) {
        Block block = world.getBlockState(pos).getBlock();
        if (block != null) {
            return block == orient && block.getMaterial(block.getDefaultState()) == Material.WOOD;
        }
        return false;
    }

    public void breakSurrounding(ItemStack item, World world, IBlockState state, BlockPos pos, EntityLivingBase user) {
        if (!world.isRemote && ForgeHooks.isToolEffective(world, pos, item)) {
            for (int x1 = -2; x1 <= 2; x1++) {
                for (int y1 = -2; y1 <= 2; y1++) {
                    for (int z1 = -2; z1 <= 2; z1++) {
                        EnumFacing facing = getFacingFor(user, pos);
                        BlockPos blockPos = pos.add(x1 +facing.getFrontOffsetX(), y1 + facing.getFrontOffsetY(),z1 + facing.getFrontOffsetZ()  )

                        if (!(x1 + facing.getFrontOffsetX() == 0 && y1 + facing.getFrontOffsetY() == 0 && z1 + facing.getFrontOffsetZ() == 0)) {
                            if (MineFantasyReborn.isBukkitServer() && BukkitUtils.cantBreakBlock((EntityPlayer) user, blockPos)) {
                                break;
                            }

                            Block newblock = world.getBlockState(blockPos).getBlock();

                            if (item.getItemDamage() < item.getMaxDamage() && newblock != null
                                    && user instanceof EntityPlayer && newblock.getMaterial(state) == Material.LEAVES) {
                                if (rand.nextFloat() * 100F < (100F - ConfigTools.hvyDropChance)) {
                                    newblock.dropBlockAsItem(world, blockPos, state, EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(35), item));
                                }
                                world.setBlockToAir(blockPos);
                                item.damageItem(1, user);
                            }
                        }
                    }
                }
            }
        }
    }

    private EnumFacing getFacingFor(EntityLivingBase user, BlockPos pos) {
        return EnumFacing.getDirectionFromEntityLiving(pos, user);// TODO: FD
    }

}
