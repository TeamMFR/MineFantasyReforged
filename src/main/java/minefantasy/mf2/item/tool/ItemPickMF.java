package minefantasy.mf2.item.tool;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.api.helpers.CustomToolHelper;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.api.tier.IToolMaterial;
import minefantasy.mf2.item.list.CreativeTabMF;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Anonymous Productions
 */
public class ItemPickMF extends ItemPickaxe implements IToolMaterial {
    protected int itemRarity;
    private String name;
    private float baseDamage = 2F;
    // ===================================================== CUSTOM START
    // =============================================================\\
    private boolean isCustom = false;
    private float efficiencyMod = 1.0F;
    private IIcon detailTex = null;
    private IIcon haftTex = null;

    public ItemPickMF(String name, ToolMaterial material, int rarity) {
        super(material);
        itemRarity = rarity;
        setCreativeTab(CreativeTabMF.tabOldTools);
        this.name = name;

        setTextureName("minefantasy2:Tool/" + name);
        GameRegistry.registerItem(this, name, MineFantasyII.MODID);
        this.setUnlocalizedName(name);
    }

    @Override
    public ToolMaterial getMaterial() {
        return toolMaterial;
    }

    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
        if (!world.isRemote)
            return item;

        MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, true);

        if (movingobjectposition == null) {
            return item;
        } else {
            if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                int i = movingobjectposition.blockX;
                int j = movingobjectposition.blockY;
                int k = movingobjectposition.blockZ;

                if (!world.canMineBlock(player, i, j, k)) {
                    return item;
                }

                if (!player.canPlayerEdit(i, j, k, movingobjectposition.sideHit, item)) {
                    return item;
                }

                Block block = world.getBlock(i, j, k);
                int blockTier = block.getHarvestLevel(world.getBlockMetadata(i, j, k));

                int HL = CustomToolHelper.getHarvestLevel(item, toolMaterial.getHarvestLevel());
                if (blockTier > HL) {
                    String msg = StatCollector.translateToLocalFormatted("prospect.cannotmine", HL, blockTier);
                    player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.RED + msg));
                } else {
                    String msg = StatCollector.translateToLocalFormatted("prospect.canmine", HL, blockTier);
                    player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.GREEN + msg));
                }
            }

            return item;
        }
    }

    private boolean canMineBlock(World world, int i, int j, int k) {
        // TODO Auto-generated method stub
        return false;
    }

    public ItemPickMF setCustom(String s) {
        canRepair = false;
        setTextureName("minefantasy2:custom/tool/" + s + "/" + name);
        isCustom = true;
        return this;
    }

    public ItemPickMF setBaseDamage(float baseDamage) {
        this.baseDamage = baseDamage;
        return this;
    }

    public ItemPickMF setEfficiencyMod(float efficiencyMod) {
        this.efficiencyMod = efficiencyMod;
        return this;
    }

    @Override
    public Multimap getAttributeModifiers(ItemStack item) {
        Multimap map = HashMultimap.create();
        map.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
                new AttributeModifier(field_111210_e, "Weapon modifier", getMeleeDamage(item), 0));

        return map;
    }

    /**
     * Gets a stack-sensitive value for the melee dmg
     */
    protected float getMeleeDamage(ItemStack item) {
        return baseDamage + CustomToolHelper.getMeleeDamage(item, toolMaterial.getDamageVsEntity());
    }

    protected float getWeightModifier(ItemStack stack) {
        return CustomToolHelper.getWeightModifier(stack, 1.0F);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {
        if (isCustom) {
            haftTex = reg.registerIcon(this.getIconString() + "_haft");
            detailTex = reg.registerIcon(this.getIconString() + "_detail");

        }
        super.registerIcons(reg);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return isCustom;
    }

    // Returns the number of render passes this item has.
    @Override
    public int getRenderPasses(int metadata) {
        return 3;
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        if (isCustom && pass == 1 && haftTex != null) {
            return haftTex;
        }
        if (isCustom && pass == 2 && detailTex != null) {
            return detailTex;
        }
        return super.getIcon(stack, pass);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack item, int layer) {
        return CustomToolHelper.getColourFromItemStack(item, layer, super.getColorFromItemStack(item, layer));
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return CustomToolHelper.getMaxDamage(stack, super.getMaxDamage(stack));
    }

    public ItemStack construct(String main, String haft) {
        return CustomToolHelper.construct(this, main, haft);
    }

    @Override
    public EnumRarity getRarity(ItemStack item) {
        return CustomToolHelper.getRarity(item, itemRarity);
    }

    @Override
    public float getDigSpeed(ItemStack stack, Block block, int meta) {
        if (!ForgeHooks.isToolEffective(stack, block, meta)) {
            return this.func_150893_a(stack, block);
        }
        return CustomToolHelper.getEfficiency(stack, super.getDigSpeed(stack, block, meta), efficiencyMod);
    }

    public float func_150893_a(ItemStack stack, Block block) {
        return block.getMaterial() != Material.iron && block.getMaterial() != Material.anvil
                && block.getMaterial() != Material.rock ? super.func_150893_a(stack, block)
                : CustomToolHelper.getEfficiency(stack, this.efficiencyOnProperMaterial, efficiencyMod);
    }

    @Override
    public int getHarvestLevel(ItemStack stack, String toolClass) {
        return CustomToolHelper.getHarvestLevel(stack, super.getHarvestLevel(stack, toolClass));
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        if (isCustom) {
            ArrayList<CustomMaterial> metal = CustomMaterial.getList("metal");
            Iterator iteratorMetal = metal.iterator();
            while (iteratorMetal.hasNext()) {
                CustomMaterial customMat = (CustomMaterial) iteratorMetal.next();
                if (MineFantasyII.isDebug() || customMat.getItem() != null) {
                    list.add(this.construct(customMat.name, "OakWood"));
                }
            }
        } else {
            super.getSubItems(item, tab, list);
        }
    }

    @Override
    public void addInformation(ItemStack item, EntityPlayer user, List list, boolean extra) {
        if (isCustom) {
            CustomToolHelper.addInformation(item, list);
        }
        super.addInformation(item, user, list, extra);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack item) {
        String unlocalName = this.getUnlocalizedNameInefficiently(item) + ".name";
        return CustomToolHelper.getLocalisedName(item, unlocalName);
    }
    // ====================================================== CUSTOM END
    // ==============================================================\\
}
