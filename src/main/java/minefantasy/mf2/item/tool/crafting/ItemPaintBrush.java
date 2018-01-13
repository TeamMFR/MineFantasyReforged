package minefantasy.mf2.item.tool.crafting;

import minefantasy.mf2.api.crafting.refine.PaintOilRecipe;
import minefantasy.mf2.api.helpers.ToolHelper;
import minefantasy.mf2.api.knowledge.ResearchLogic;
import minefantasy.mf2.api.rpg.SkillList;
import minefantasy.mf2.api.weapon.IRackItem;
import minefantasy.mf2.block.tileentity.decor.TileEntityRack;
import minefantasy.mf2.item.food.FoodListMF;
import minefantasy.mf2.item.list.ComponentListMF;
import minefantasy.mf2.item.list.CreativeTabMF;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

/**
 * @author Anonymous Productions
 */
public class ItemPaintBrush extends ItemBasicCraftTool implements IRackItem {
    public ItemPaintBrush(String name, int uses) {
        super(name, "brush", 0, uses);
        setCreativeTab(CreativeTabMF.tabCraftTool);

        // setTextureName("minefantasy2:Tool/Crafting/"+name);
        // this.setUnlocalizedName(name);
        // this.setMaxDamage(uses);
        // setMaxStackSize(1);
        this.setFull3D();
    }

    @Override
    public ItemStack getContainerItem(ItemStack item) {
        item.setItemDamage(item.getItemDamage() + 1);
        return item.getItemDamage() >= item.getMaxDamage() ? null : item;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return ToolHelper.setDuraOnQuality(stack, super.getMaxDamage());
    }

    @Override
    public boolean onItemUse(ItemStack item, EntityPlayer user, World world, int x, int y, int z, int side, float f,
                             float f1, float f2) {
        if (user.canPlayerEdit(x, y, z, side, item) && ResearchLogic.hasInfoUnlocked(user, "paint_brush")) {
            if (!user.isSwingInProgress && user.inventory.hasItem(ComponentListMF.plant_oil)) {
                Block block = world.getBlock(x, y, z);
                return onUsedWithBlock(world, x, y, z, block, item, user);
            }
        }
        return false;
    }

    private boolean onUsedWithBlock(World world, int x, int y, int z, Block block, ItemStack item, EntityPlayer user) {
        Block newBlock = null;
        int meta = world.getBlockMetadata(x, y, z);
        ItemStack output = PaintOilRecipe.getPaintResult(new ItemStack(block, 1, meta));
        if (output != null && output.getItem() instanceof ItemBlock) {
            newBlock = Block.getBlockFromItem(output.getItem());
            if (output.getItemDamage() != OreDictionary.WILDCARD_VALUE) {
                meta = output.getItemDamage();
            }
        }
        if (newBlock != null) {
            world.playAuxSFXAtEntity(user, 2001, x, y, z, Block.getIdFromBlock(newBlock) + (meta << 12));

            user.inventory.consumeInventoryItem(ComponentListMF.plant_oil);
            ItemStack jug = new ItemStack(FoodListMF.jug_empty);

            if (!user.inventory.addItemStackToInventory(jug) && !world.isRemote) {
                user.entityDropItem(jug, 0F);
            }
            if (world.isRemote)
                return true;

            SkillList.construction.addXP(user, 1);
            item.damageItem(1, user);
            world.setBlock(x, y, z, newBlock);
            world.setBlockMetadataWithNotify(x, y, z, meta, 2);
        }
        return false;
    }

    @Override
    public float getScale(ItemStack itemstack) {
        return 1.0F;
    }

    @Override
    public float getOffsetX(ItemStack itemstack) {
        return 0;
    }

    @Override
    public float getOffsetY(ItemStack itemstack) {
        return 9F / 16F;
    }

    @Override
    public float getOffsetZ(ItemStack itemstack) {
        return 1F / 8F;
    }

    @Override
    public float getRotationOffset(ItemStack itemstack) {
        return 90;
    }

    @Override
    public boolean canHang(TileEntityRack rack, ItemStack item, int slot) {
        return true;
    }

    @Override
    public boolean isSpecialRender(ItemStack item) {
        return false;
    }
}
