package minefantasy.mf2.block.crafting;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.api.archery.AmmoMechanicsMF;
import minefantasy.mf2.api.crafting.Salvage;
import minefantasy.mf2.api.helpers.ToolHelper;
import minefantasy.mf2.api.knowledge.ResearchLogic;
import minefantasy.mf2.api.rpg.RPGElements;
import minefantasy.mf2.api.rpg.SkillList;
import minefantasy.mf2.item.list.CreativeTabMF;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class BlockSalvage extends Block {
    protected float dropLevel;
    protected IIcon top, side, bottom;
    private String type;
    private Random rand = new Random();

    public BlockSalvage(String name, float dropLevel) {
        super(Material.wood);

        this.dropLevel = dropLevel;
        this.type = name;

        this.setBlockTextureName("minefantasy2:processor/" + "salvage_" + name + "+top");
        name = "salvage_" + name;
        GameRegistry.registerBlock(this, ItemBlockSalvage.class, name);
        setBlockName(name);
        this.setStepSound(Block.soundTypeAnvil);
        this.setHardness(2F);
        this.setResistance(1F);
        this.setCreativeTab(CreativeTabMF.tabGadget);
    }

    public static float getPlayerDropLevel(EntityPlayer user) {
        float rate = 1.0F;

        if (RPGElements.isSystemActive) {
            int lvl = RPGElements.getLevel(user, SkillList.artisanry);
            if (lvl > 10) {
                rate += ((lvl - 10) * 0.01F);
            }
        }
        if (ResearchLogic.hasInfoUnlocked(user, "scrapper")) {
            rate += 0.5F;
        }

        return rate;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta) {
        return side == 1 ? this.top : (side == 0 ? this.bottom : this.side);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister reg) {
        this.side = reg.registerIcon("minefantasy2:processor/salvage_" + type + "_side");
        this.top = reg.registerIcon("minefantasy2:processor/salvage_" + type + "_top");
        this.bottom = reg.registerIcon("minefantasy2:processor/salvage_" + type + "_base");
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer user, int side, float xOffset,
                                    float yOffset, float zOffset) {
        if (world.isRemote) {
            return true;
        }
        ItemStack held = user.getHeldItem();
        String toolType = ToolHelper.getCrafterTool(held);

        /*
         * Block above = world.getBlock(x, y+1, z); if(above != null) {
         * if(salvageBlock(world, user, above, x, y, z)) { world.setBlockToAir(x, y+1,
         * z); return true; } }
         */

        EntityItem drop = getDrop(world, x, y, z);
        if (drop != null && !user.isSwingInProgress) {
            ItemStack junk = drop.getEntityItem();
            int amount = Math.min(junk.stackSize, 4);

            for (int a = 0; a < amount; a++) {
                if (salvageItem(world, user, junk, x, y, z)) {
                    if (junk.stackSize == 1) {
                        AmmoMechanicsMF.dropAmmo(world, junk, x + 0.5D, y + 1.25D, z + 0.5D);
                        AmmoMechanicsMF.dropAmmoCrate(world, junk, x + 0.5D, y + 1.25D, z + 0.5D);
                    }

                    --drop.getEntityItem().stackSize;
                    if (drop.getEntityItem().stackSize <= 0) {
                        drop.setDead();
                        return true;
                    }
                }
            }
            return true;
        }
        return false;
    }
    /*
     * private boolean salvageBlock(World world, EntityPlayer user, Block junk, int
     * x, int y, int z) { List<ItemStack> salvage = Salvage.salvageBlock(junk,
     * dropLevel);
     *
     * if(salvage != null) { dropSalvage(world, x, y, z, salvage);
     * world.playAuxSFX(1021, x, y, z, 0); return true; } return false; }
     */

    private boolean salvageItem(World world, EntityPlayer user, ItemStack junk, int x, int y, int z) {
        float modifier = 0.5F;
        ItemStack held = user.getHeldItem();
        String type = ToolHelper.getCrafterTool(held);
        float efficiency = ToolHelper.getCrafterEfficiency(held);
        if (type.equalsIgnoreCase("saw")) {
            modifier += (efficiency * 0.1F);
            world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, "minefantasy2:block.sawcarpenter", 2F, 1F);
        }
        if (type.equalsIgnoreCase("hammer") || type.equalsIgnoreCase("hvyhammer")) {
            modifier += (efficiency * 0.05F);
            world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, "minefantasy2:block.anvilsucceed", 1F, 1F);
        }

        List<ItemStack> salvage = Salvage.salvage(user, junk, dropLevel * getPlayerDropLevel(user) * modifier);

        if (salvage != null) {
            dropSalvage(world, x, y, z, salvage, junk);
            world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, "random.break", 1F, 1F);
            world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, "mob.zombie.woodbreak", 0.5F, 1.5F);
            world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, "dig.glass", 1.0F, 0.5F);
            return true;
        }
        return false;
    }

    private void dropSalvage(World world, int x, int y, int z, List<ItemStack> salvage, ItemStack junk) {
        Iterator iterator = salvage.iterator();
        while (iterator.hasNext()) {
            ItemStack drop = (ItemStack) iterator.next();
            if (drop != null)// && !user.inventory.addItemStackToInventory(drop))
            {
                entityDropItem(world, x, y, z, drop);
            }
        }
    }

    public EntityItem entityDropItem(World world, int x, int y, int z, ItemStack item) {
        if (item.stackSize != 0 && item.getItem() != null) {
            EntityItem entityitem = new EntityItem(world, x + 0.5D, y + 1.25D, z + 0.5D, item);
            entityitem.delayBeforeCanPickup = 10;
            world.spawnEntityInWorld(entityitem);
            return entityitem;
        }
        return null;
    }

    private EntityItem getDrop(World world, int x, int y, int z) {
        AxisAlignedBB box = AxisAlignedBB.getBoundingBox(x + 0.1D, y + 1D, z + 0.1D, x + 0.9D, y + 1.5D, z + 0.9D);
        List<EntityItem> drops = world.getEntitiesWithinAABB(EntityItem.class, box);
        if (drops != null && !drops.isEmpty()) {
            return (drops.get(0));
        }
        return null;
    }
}
