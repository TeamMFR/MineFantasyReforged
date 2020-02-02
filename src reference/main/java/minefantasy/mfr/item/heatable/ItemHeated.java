package minefantasy.mfr.item.heatable;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.api.heating.Heatable;
import minefantasy.mfr.api.heating.IHotItem;
import minefantasy.mfr.api.heating.TongsHelper;
import minefantasy.mfr.api.helpers.GuiHelper;
import minefantasy.mfr.init.ComponentListMFR;
import minefantasy.mfr.util.MFRLogUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;
import java.util.List;

public class ItemHeated extends Item implements IHotItem {
    public static boolean renderDynamicHotIngotRendering = true;

    public ItemHeated() {
        GameRegistry.findRegistry(Item.class).register(this);
        this.setUnlocalizedName(MineFantasyReborn.MODID + ".MF_Hot_Item");
        this.setHasSubtypes(true);
        this.setMaxStackSize(64);
    }

    public static int getTemp(ItemStack item) {
        return Heatable.getTemp(item);
    }

    public static int getWorkTemp(ItemStack item) {
        return Heatable.getWorkTemp(item);
    }

    public static int getUnstableTemp(ItemStack item) {
        return Heatable.getWorkTemp(item);
    }

    public static ItemStack getItem(ItemStack item) {
        return Heatable.getItem(item);
    }

    public static void setTemp(ItemStack item, int heat) {
        NBTTagCompound nbt = getNBT(item);

        nbt.setInteger(Heatable.NBT_CurrentTemp, heat);
    }

    public static void setWorkTemp(ItemStack item, int heat) {
        NBTTagCompound nbt = getNBT(item);
        MFRLogUtil.logDebug("Set Workable Temp: " + heat);
        nbt.setInteger(Heatable.NBT_WorkableTemp, heat);
    }

    public static void setUnstableTemp(ItemStack item, int heat) {
        NBTTagCompound nbt = getNBT(item);
        MFRLogUtil.logDebug("Set Unstable Temp: " + heat);
        nbt.setInteger(Heatable.NBT_UnstableTemp, heat);
    }

    public static ItemStack createHotItem(ItemStack item) {
        return createHotItem(item, false);
    }

    public static ItemStack createHotItem(ItemStack item, int temp) {
        ItemStack hot = createHotItem(item, true);
        setTemp(hot, temp);

        return hot;
    }

    public static ItemStack createHotItem(ItemStack item, boolean ignoreStats) {
        Heatable stats = Heatable.loadStats(item);
        if (stats != null) {
            ItemStack out = new ItemStack(ComponentListMFR.hotItem, item.getCount());
            NBTTagCompound nbt = getNBT(out);
            NBTTagCompound save = new NBTTagCompound();
            item.writeToNBT(save);
            nbt.setTag(Heatable.NBT_Item, save);

            nbt.setBoolean(Heatable.NBT_ShouldDisplay, true);
            setWorkTemp(out, stats.getWorkableStat(item));
            setUnstableTemp(out, stats.getUnstableStat(item));

            return out;
        } else if (ignoreStats) {
            ItemStack out = new ItemStack(ComponentListMFR.hotItem, item.getCount());
            NBTTagCompound nbt = getNBT(out);
            NBTTagCompound save = new NBTTagCompound();
            item.writeToNBT(save);
            nbt.setTag(Heatable.NBT_Item, save);

            nbt.setBoolean(Heatable.NBT_ShouldDisplay, false);
            setWorkTemp(out, 0);
            setUnstableTemp(out, 0);

            return out;
        }
        return item;
    }

    private static void shareTraits(NBTTagCompound nbt, ItemStack item) {
        NBTTagCompound itemtag = getNBT(item);
        if (itemtag.hasKey("Unbreakable")) {
            nbt.setBoolean("Unbreakable", itemtag.getBoolean("Unbreakable"));
        }
        if (itemtag.hasKey("MF_Inferior")) {
            nbt.setBoolean("MF_Inferior", itemtag.getBoolean("MF_Inferior"));
        }
        if (itemtag.hasKey("MFCraftQuality")) {
            nbt.setFloat("MFCraftQuality", itemtag.getFloat("MFCraftQuality"));
        }
    }

    public static boolean showTemp(ItemStack stack) {
        if (stack == null)
            return false;

        NBTTagCompound nbt = getNBT(stack);

        if (nbt == null)
            return false;

        if (nbt.hasKey(Heatable.NBT_ShouldDisplay)) {
            return nbt.getBoolean(Heatable.NBT_ShouldDisplay);
        }
        return false;
    }

    private static NBTTagCompound getNBT(ItemStack item) {
        if (!item.hasTagCompound())
            item.setTagCompound(new NBTTagCompound());
        return item.getTagCompound();
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        String name = "";

        ItemStack item = getItem(stack);
        if (item != null)
            name = item.getItem().getItemStackDisplayName(item);
        return I18n.translateToLocalFormatted("prefix.hotitem.name", name);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        ItemStack item = getItem(stack);
        if (item != null)
            return item.getItem().getRarity(item);

        return EnumRarity.COMMON;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List list, ITooltipFlag b) {
        ItemStack item = getItem(stack);

        if (item != null) {
            item.getItem().addInformation(item, world, list, b);
        } else
            super.addInformation(stack, world, list, b);

        NBTTagCompound nbt = getNBT(stack);
        if (nbt.hasKey(Heatable.NBT_ShouldDisplay)) {
            if (nbt.getBoolean(Heatable.NBT_ShouldDisplay)) {
                list.add(getHeatString(stack));
                if (!getWorkString(item, stack).equals(""))
                    list.add(getWorkString(item, stack));
            }
        }
    }

    private String getHeatString(ItemStack item) {
        int heat = getTemp(item);
        String unit = "*C";
        return heat + unit;
    }

    private String getWorkString(ItemStack heated, ItemStack item) {
        byte stage = Heatable.getHeatableStage(item);
        switch (stage) {
            case 1:
                return TextFormatting.YELLOW + I18n.translateToLocal("state.workable");
            case 2:
                return TextFormatting.RED + I18n.translateToLocal("state.unstable");
        }
        return "";
    }

    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        RayTraceResult rayTrace = this.rayTrace(world, player, true);
        ItemStack item = player.getHeldItem(hand);
        if (rayTrace == null) {
            return ActionResult.newResult(EnumActionResult.PASS, item);
        } else {
            if (rayTrace.typeOfHit == RayTraceResult.Type.BLOCK) {
                BlockPos rayTracePos = rayTrace.getBlockPos();

                if (!world.canMineBlockBody(player,rayTracePos)) {
                    return ActionResult.newResult(EnumActionResult.PASS, item);
                }

                if (!player.canPlayerEdit(rayTracePos, rayTrace.sideHit, item)) {
                    return ActionResult.newResult(EnumActionResult.PASS, item);
                }
                float water = TongsHelper.getWaterSource(world, rayTracePos);

                if (water >= 0) {
                    player.playSound(SoundEvents.ENTITY_GENERIC_SPLASH, 1F, 1F);
                    player.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 2F, 0.5F);

                    for (int a = 0; a < 5; a++) {
                        world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, rayTracePos.getX() + 0.5F, rayTracePos.getY() + 1, rayTracePos.getZ() + 0.5F, 0, 0.065F, 0);
                    }

                    ItemStack drop = getItem(item).copy();

                    if (Heatable.HCCquenchRuin) {
                        float damageDone = 50F + (water > 0F ? water : 0F);
                        if (damageDone > 99F)
                            damageDone = 99F;

                        if (drop.isItemStackDamageable()) {
                            drop.setItemDamage((int) (drop.getMaxDamage() * damageDone / 100F));
                        }
                    }
                    drop.setCount(item.getCount());
                    if (drop != null) {
                        item.setCount(0);

                        if (item.getCount() <= 0) {
                            return ActionResult.newResult(EnumActionResult.PASS, drop.copy());
                        }
                    }
                }
            }

            return ActionResult.newResult(EnumActionResult.FAIL, item);
        }
    }

    public int getColorFromItemStack(ItemStack item, int renderPass) {
        if (renderPass > 1 || !renderDynamicHotIngotRendering) {
            return Color.WHITE.getRGB();
        }
        NBTTagCompound nbt = getNBT(item);
        int heat = getTemp(item);
        int maxHeat = Heatable.forgeMaximumMetalHeat;
        double heatPer = (double) heat / (double) maxHeat * 100D;

        int red = getRedOnHeat(heatPer);
        int green = getGreenOnHeat(heatPer);
        int blue = getBlueOnHeat(heatPer);

        float curr_red = 1.0F;
        float curr_green = 1.0F;
        float curr_blue = 1.0F;

        ItemStack held = getItem(item);
        if (held != null) {
            int colour = item.getItem().getColorFromItemStack(held, 0);

            curr_red = (colour >> 16 & 255) / 255.0F;
            curr_green = (colour >> 8 & 255) / 255.0F;
            curr_blue = (colour & 255) / 255.0F;

            red = (int) (red * curr_red);
            green = (int) (green * curr_green);
            blue = (int) (blue * curr_blue);
        }

        return GuiHelper.getColourForRGB(red, green, blue);
    }

    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    private int getRedOnHeat(double percent) {
        if (percent <= 0)
            return 255;
        return 255;
    }

    private int getGreenOnHeat(double percent) {
        if (percent <= 0)
            return 255;
        if (percent > 100)
            percent = 100;
        if (percent < 0)
            percent = 0;

        if (percent <= 55) {
            return (int) (255 - ((255 / 55) * percent));
        } else {
            return (int) ((255 / 55) * (percent - 55));
        }
    }

    private int getBlueOnHeat(double percent) {
        if (percent <= 0)
            return 255;

        if (percent > 100)
            percent = 100;
        if (percent < 0)
            percent = 0;

        if (percent <= 55) {
            return (int) (255 - ((255 / 55) * percent));
        }
        return 0;
    }

    @Override
    public boolean isHot(ItemStack item) {
        return true;
    }

    @Override
    public boolean isCoolable(ItemStack item) {
        return true;
    }
}
