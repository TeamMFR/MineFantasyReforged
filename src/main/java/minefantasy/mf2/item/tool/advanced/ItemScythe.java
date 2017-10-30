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
import minefantasy.mf2.api.weapon.IDamageType;
import minefantasy.mf2.api.weapon.IRackItem;
import minefantasy.mf2.block.tileentity.decor.TileEntityRack;
import minefantasy.mf2.farming.FarmingHelper;
import minefantasy.mf2.item.list.CreativeTabMF;
import minefantasy.mf2.util.BukkitUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * @author Anonymous Productions
 */
public class ItemScythe extends Item implements IToolMaterial, IDamageType, IRackItem {
    protected int itemRarity;
    private Random rand = new Random();
    private ToolMaterial toolMaterial;
    private String name;
    private float baseDamage = 3.0F;
    // ===================================================== CUSTOM START
    // =============================================================\\
    private boolean isCustom = false;
    private float efficiencyMod = 1.0F;
    private IIcon detailTex = null;
    private IIcon haftTex = null;

    /**
     */
    public ItemScythe(String name, ToolMaterial material, int rarity) {
        this.toolMaterial = material;
        this.setFull3D();
        itemRarity = rarity;
        this.name = name;
        setCreativeTab(CreativeTabMF.tabOldTools);
        setTextureName("minefantasy2:Tool/Advanced/" + name);
        GameRegistry.registerItem(this, name, MineFantasyII.MODID);
        this.setUnlocalizedName(name);
        this.maxStackSize = 1;
        this.setMaxDamage(material.getMaxUses());
    }

    @Override
    public ToolMaterial getMaterial() {
        return toolMaterial;
    }

    private boolean cutGrass(World world, int x, int y, int z, int r, EntityPlayer entity, boolean leaf) {
        boolean flag = false;
        ItemStack item = entity.getHeldItem();
        if (item == null)
            return false;

        for (int x2 = -r; x2 <= r; x2++) {
            for (int y2 = -r; y2 <= r; y2++) {
                for (int z2 = -r; z2 <= r; z2++) {
                    Block block = world.getBlock(x + x2, y + y2, z + z2);
                    int meta = world.getBlockMetadata(x + x2, y + y2, z + z2);
                    if (block != null) {
                        Material m = block.getMaterial();
                        if (canCutMaterial(m, block.getBlockHardness(world, x + x2, y + y2, z + z2), leaf)) {
                            if ((MineFantasyII.isBukkitServer()
                                    && BukkitUtils.cantBreakBlock(entity, x + x2, y + y2, z + z2))) {
                                continue;
                            }

                            if (getDistance(x + x2, y + y2, z + z2, x, y, z) < r * 1) {
                                flag = true;

                                ArrayList<ItemStack> items = block.getDrops(world, x + x2, y + y2, z + z2, meta, 0);
                                world.setBlockToAir(x + x2, y + y2, z + z2);
                                world.playAuxSFXAtEntity(entity, 2001, x + x2, y + y2, z + z2,
                                        Block.getIdFromBlock(block)
                                                + (world.getBlockMetadata(x + x2, y + y2, z + z2) << 16));
                                tryBreakFarmland(world, x + x2, y + y2 - 1, z + z2);
                                if (!entity.capabilities.isCreativeMode) {
                                    ItemLumberAxe.tirePlayer(entity, 1F);
                                    for (ItemStack drop : items) {
                                        if (world.rand.nextFloat() <= 1.0F) {
                                            dropBlockAsItem_do(world, x + x2, y + y2, z + z2, drop);
                                        }
                                    }
                                }
                                item.damageItem(1, entity);
                            }
                        }
                    }
                }
            }
        }
        return flag;
    }

    private void tryBreakFarmland(World world, int x, int y, int z) {
        Block base = world.getBlock(x, y, z);

        if (base != null && base == Blocks.farmland && FarmingHelper.didHarvestRuinBlock(world, true)) {
            world.setBlock(x, y, z, Blocks.dirt);
        }
    }

    protected void dropBlockAsItem_do(World world, int x, int y, int z, ItemStack drop) {
        if (!world.isRemote && world.getGameRules().getGameRuleBooleanValue("doTileDrops")) {
            float var6 = 0.7F;
            double var7 = world.rand.nextFloat() * var6 + (1.0F - var6) * 0.5D;
            double var9 = world.rand.nextFloat() * var6 + (1.0F - var6) * 0.5D;
            double var11 = world.rand.nextFloat() * var6 + (1.0F - var6) * 0.5D;
            EntityItem var13 = new EntityItem(world, x + var7, y + var9, z + var11, drop);
            var13.delayBeforeCanPickup = 10;
            world.spawnEntityInWorld(var13);
        }
    }

    private boolean canCutMaterial(Material m, float str, boolean leaf) {
        if (!leaf) {
            if (str <= 0.0F) {
                return m == Material.vine || m == Material.plants || m == Material.grass;
            } else
                return false;
        }
        return m == Material.leaves || m == Material.vine;
    }

    public double getDistance(double x, double y, double z, int posX, int posY, int posZ) {
        double var7 = posX - x;
        double var9 = posY - y;
        double var11 = posZ - z;
        return MathHelper.sqrt_double(var7 * var7 + var9 * var9 + var11 * var11);
    }

    @Override
    public boolean onItemUse(ItemStack hoe, EntityPlayer player, World world, int x, int y, int z, int facing,
                             float pitch, float yaw, float pan) {
        if (!player.canPlayerEdit(x, y, z, facing, hoe) || !ItemLumberAxe.canAcceptCost(player)) {
            return false;
        } else {
            Block block = world.getBlock(x, y, z);

            if (block != null) {
                Material m = block.getMaterial();
                float hard = block.getBlockHardness(world, x, y, z);
                if (this.canCutMaterial(m, hard, false)) {
                    if (cutGrass(world, x, y, z, 5, player, false)) {
                        player.swingItem();
                    }
                } else if (this.canCutMaterial(m, hard, true)) {
                    if (cutGrass(world, x, y, z, 3, player, true)) {
                        player.swingItem();
                    }
                }
            }
        }
        return false;
    }

    @Override
    public float[] getDamageRatio(Object... implement) {
        return new float[]{1, 0, 2};
    }

    @Override
    public float getPenetrationLevel(Object implement) {
        return 0F;
    }

    public ItemScythe setCustom(String s) {
        canRepair = false;
        setTextureName("minefantasy2:custom/tool/" + s + "/" + name);
        isCustom = true;
        return this;
    }

    public ItemScythe setBaseDamage(float baseDamage) {
        this.baseDamage = baseDamage;
        return this;
    }

    public ItemScythe setEfficiencyMod(float efficiencyMod) {
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
        return 5F / 16F;
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
        return rack.hasRackBelow(slot);
    }

    @Override
    public boolean isSpecialRender(ItemStack item) {
        return false;
    }
}
