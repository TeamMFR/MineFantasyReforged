package minefantasy.mfr.item.custom;

import minefantasy.mfr.api.crafting.ITieredComponent;
import minefantasy.mfr.api.helpers.CustomToolHelper;
import minefantasy.mfr.api.material.CustomMaterial;
import minefantasy.mfr.block.decor.BlockComponent;
import minefantasy.mfr.entity.EntityCogwork;
import minefantasy.mfr.init.ComponentListMFR;
import minefantasy.mfr.init.CreativeTabMFR;
import minefantasy.mfr.item.ItemBaseMFR;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class ItemCustomComponent extends ItemBaseMFR implements ITieredComponent {
    private final String materialType;
    private String name;
    private float mass;
    private boolean canDamage = false;
    private String blocktex;
    private String storageType;

    public ItemCustomComponent(String name, String type) {
        this(name, -1F, type);
    }

    public ItemCustomComponent(String name, float mass, String type) {
        super(name);
        this.name = name;

        this.setCreativeTab(CreativeTabMFR.tabMaterialsMFR);
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
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (!isInCreativeTab(tab)) {
            return;
        }
        if (tab != CreativeTabMFR.tabMaterialsMFR) {
            ArrayList<CustomMaterial> wood = CustomMaterial.getList("metal");
            for (CustomMaterial customMat : wood) {
                items.add(this.createComm(customMat.name));
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
    public void addInformation(ItemStack tool, World world, List<String> list, ITooltipFlag flag) {
        super.addInformation(tool, world, list, flag);
        if (!canDamage) {
            CustomToolHelper.addComponentString(tool, list, getBase(tool), mass);
        }
        if (this == ComponentListMFR.COGWORK_ARMOUR) {
            int AR = EntityCogwork.getArmourRating(getBase(tool));
            list.add(I18n.format("attribute.armour.protection") + " " + AR);
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
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer user, EnumHand hand) {
        ItemStack item = user.getHeldItem(hand);
        if (!world.isRemote && storageType != null) {
            RayTraceResult movingobjectposition = this.rayTrace(world, user, false);
            if (movingobjectposition == null) {
                return ActionResult.newResult(EnumActionResult.FAIL, item);
            } else {
                int placed = BlockComponent.useComponent(item, storageType, blocktex, world, user,
                        movingobjectposition);
                if (placed > 0) {
                    item.shrink(placed);
                    return ActionResult.newResult(EnumActionResult.PASS, item);
                }
            }
        }
        return ActionResult.newResult(EnumActionResult.FAIL, item);
    }
}