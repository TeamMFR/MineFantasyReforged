package minefantasy.mf2.item.custom;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.api.crafting.ITieredComponent;
import minefantasy.mf2.api.helpers.CustomToolHelper;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.block.decor.BlockComponent;
import minefantasy.mf2.entity.EntityCogwork;
import minefantasy.mf2.item.list.ComponentListMF;
import minefantasy.mf2.item.list.CreativeTabMF;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ItemCustomComponent extends Item implements ITieredComponent {
    private final String materialType;
    @SideOnly(Side.CLIENT)
    public IIcon baseTex;
    private String name;
    private float mass;
    private boolean canDamage = false;
    // STORAGE
    private String blocktex;
    ;
    private String storageType;

    public ItemCustomComponent(String name, String type) {
        this(name, -1F, type);
    }

    public ItemCustomComponent(String name, float mass, String type) {
        this.name = name;
        this.setCreativeTab(CreativeTabMF.tabMaterials);
        GameRegistry.registerItem(this, "custom_" + name, MineFantasyII.MODID);
        this.setUnlocalizedName(name);
        this.mass = mass;
        this.materialType = type;
    }

    public ItemCustomComponent setCanDamage() {
        this.canDamage = true;
        this.setHasSubtypes(false);
        return this;
    }

    @Override
    public boolean isDamageable() {
        return canDamage;
    }

    @Override
    public boolean isRepairable() {
        return false;
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        if (tab != CreativeTabMF.tabMaterialsMF) {
            ArrayList<CustomMaterial> wood = CustomMaterial.getList("metal");
            Iterator iteratorWood = wood.iterator();
            while (iteratorWood.hasNext()) {
                CustomMaterial customMat = (CustomMaterial) iteratorWood.next();
                list.add(this.createComm(customMat.name));
            }
        }
    }

    public float getWeightInKg(ItemStack tool) {
        CustomMaterial base = getBase(tool);
        if (base != null) {
            return base.density * mass;
        }
        return mass;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack tool, EntityPlayer user, List list, boolean fullInfo) {
        super.addInformation(tool, user, list, fullInfo);
        if (!canDamage) {
            CustomToolHelper.addComponentString(tool, list, getBase(tool), mass);
        }
        if (this == ComponentListMF.cogwork_armour) {
            int AR = EntityCogwork.getArmourRating(getBase(tool));
            list.add(StatCollector.translateToLocal("attribute.armour.protection") + " " + AR);
            if (mass > 0)
                list.add(CustomMaterial.getWeightString(getWeightInKg(tool)));
        }
    }

    @Override
    public String getItemStackDisplayName(ItemStack tool) {
        return CustomToolHelper.getLocalisedName(tool, "item.commodity_" + name + ".name");
    }

    public CustomMaterial getBase(ItemStack component) {
        return CustomToolHelper.getCustomPrimaryMaterial(component);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderPasses(int metadata) {
        return 1;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack item, int layer) {
        CustomMaterial base = getBase(item);
        if (base != null) {
            return base.getColourInt();
        }
        return super.getColorFromItemStack(item, layer);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack item, int layer) {
        return baseTex;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {
        baseTex = reg.registerIcon("minefantasy2:custom/component/" + name);
    }

    public ItemStack createComm(String base) {
        return createComm(base, 1);
    }

    public ItemStack createComm(String base, int stack) {
        return createComm(base, stack, 0);
    }

    public ItemStack createComm(String base, int stack, float damage) {
        ItemStack item = new ItemStack(this, stack);
        CustomMaterial.addMaterial(item, CustomToolHelper.slot_main, base);
        int maxdam = this.getMaxDamage(item);

        item.setItemDamage((int) (maxdam * damage));
        return item;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        if (canDamage) {
            return CustomToolHelper.getMaxDamage(stack, super.getMaxDamage(stack));
        }
        return super.getMaxDamage(stack);
    }

    public ItemStack createComm(String base, int stack, int damage) {
        ItemStack item = new ItemStack(this, stack, damage);
        CustomMaterial.addMaterial(item, CustomToolHelper.slot_main, base);
        return item;
    }

    @Override
    public String getMaterialType(ItemStack item) {
        return materialType;
    }

    public ItemCustomComponent setStoragePlacement(String type, String tex) {
        this.blocktex = tex;
        this.storageType = type;
        return this;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer user) {
        if (!world.isRemote && storageType != null) {
            MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, user, false);

            if (movingobjectposition == null) {
                return item;
            } else {
                int placed = BlockComponent.useComponent(item, storageType, blocktex, world, user,
                        movingobjectposition);
                if (placed > 0) {
                    item.stackSize -= placed;
                    return item;
                }
            }
        }
        return item;
    }
}