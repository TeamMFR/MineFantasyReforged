package minefantasy.mf2.item.tool.advanced;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.api.helpers.CustomToolHelper;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.api.tier.IToolMaterial;
import minefantasy.mf2.block.list.BlockListMF;
import minefantasy.mf2.block.tileentity.TileEntityRoad;
import minefantasy.mf2.item.list.CreativeTabMF;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Anonymous Productions
 */
public class ItemMattock extends ItemPickaxe implements IToolMaterial {
    protected int itemRarity;
    private String name;
    // ===================================================== CUSTOM START
    // =============================================================\\
    private float baseDamage = 1.0F;
    private boolean isCustom = false;
    private float efficiencyMod = 1.0F;
    private IIcon detailTex = null;
    private IIcon haftTex = null;

    public ItemMattock(String name, ToolMaterial material, int rarity) {
        super(material);
        itemRarity = rarity;
        setCreativeTab(CreativeTabMF.tabOldTools);

        setTextureName("minefantasy2:Tool/Advanced/" + name);
        GameRegistry.registerItem(this, name, MineFantasyII.MODID);
        this.setHarvestLevel("pickaxe", Math.max(0, material.getHarvestLevel() - 2));
        this.setUnlocalizedName(name);
        this.name = name;
    }

    @Override
    public ToolMaterial getMaterial() {
        return toolMaterial;
    }

    @Override
    public boolean canHarvestBlock(Block block, ItemStack stack) {
        return super.canHarvestBlock(block, stack) || Items.iron_shovel.canHarvestBlock(block, stack);
    }

    @Override
    public boolean onItemUse(ItemStack mattock, EntityPlayer player, World world, int x, int y, int z, int facing,
                             float pitch, float yaw, float pan) {
        if (!player.canPlayerEdit(x, y, z, facing, mattock)) {
            return false;
        } else {
            Block ground = world.getBlock(x, y, z);
            int groundmeta = world.getBlockMetadata(x, y, z);
            Block above = world.getBlock(x, y + 1, z);
            int meta = ground == Blocks.sand ? 1 : 0;

            if (facing == 0 || above != Blocks.air) {
                return false;
            } else if (ground == Blocks.grass || ground == Blocks.dirt || ground == Blocks.sand) {
                Block var13 = BlockListMF.road;
                if (ground == Blocks.grass) {
                    ground = Blocks.dirt;
                }
                world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, "dig." + ground.stepSound.soundName,
                        (var13.stepSound.getVolume() + 1.0F) / 2.0F, var13.stepSound.getPitch() * 0.8F);

                if (world.isRemote) {
                    return true;
                } else {
                    world.setBlock(x, y, z, var13, meta, 2);

                    TileEntityRoad road = new TileEntityRoad();
                    road.setWorldObj(world);
                    world.setTileEntity(x, y, z, road);
                    road.setSurface(ground, groundmeta);
                    mattock.damageItem(1, player);
                    return true;
                }
            } else {
                return false;
            }
        }
    }

    public ItemMattock setCustom(String s) {
        canRepair = false;
        setTextureName("minefantasy2:custom/tool/" + s + "/" + name);
        isCustom = true;
        return this;
    }

    public ItemMattock setBaseDamage(float baseDamage) {
        this.baseDamage = baseDamage;
        return this;
    }

    public ItemMattock setEfficiencyMod(float efficiencyMod) {
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
