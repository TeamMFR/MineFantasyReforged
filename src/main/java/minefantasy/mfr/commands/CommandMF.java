package minefantasy.mfr.commands;

import minefantasy.mfr.api.helpers.CustomToolHelper;
import minefantasy.mfr.api.helpers.ToolHelper;
import minefantasy.mfr.api.material.CustomMaterial;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandMF implements ICommand {

    private final List materials;

    private final List aliases = new ArrayList<String>() {{
        add("mf");
        add("minefantasy");
    }};

    public CommandMF() {
        materials = setupMaterialsList();
    }

    private List setupMaterialsList() {
        List materials = new ArrayList<String>();
        for(CustomMaterial material : CustomMaterial.materialList.values()) {
            if(material.type.equalsIgnoreCase("wood") || material.type.equalsIgnoreCase("metal")) {
                materials.add(material);
            }
        }
        return materials;
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] strings) {
        if (iCommandSender instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) iCommandSender;
            if (strings.length > 0) {
                if (strings[0].equalsIgnoreCase("edit")) {
                    ItemStack equippedItem = player.getHeldItemMainhand();
                    if (equippedItem == null) {
                        player.sendMessage(new TextComponentString(I18n.translateToLocal("command.invalid.item")));
                        return;
                    }

                    switch (strings[1]) {
                        case "material":
                            processEditMaterialCommand(strings, player, equippedItem);
                            break;
                        case "quality":
                            processQualityCommand(strings, player, equippedItem);
                            break;
                        case "unbreakable":
                            processUnbreakableCommand(strings, player, equippedItem);
                            break;
                    }
                }
            }
        }
    }

    private void processEditMaterialCommand(String[] strings, EntityPlayer player, ItemStack equippedItem) {
        if (!CustomToolHelper.hasAnyMaterial(equippedItem)) {
            player.sendMessage(new TextComponentString(I18n.translateToLocal("command.invalid.item")));
            return;
        }

        CustomMaterial material = CustomMaterial.getMaterial(strings[2]);
        if (material == null) {
            player.sendMessage(new TextComponentString(I18n.translateToLocal("command.edit.invalid.material")));
            return;
        }

        String slot = material.type.equalsIgnoreCase("metal") ? CustomToolHelper.slot_main : CustomToolHelper.slot_haft;
        CustomMaterial.addMaterial(equippedItem, slot, material.getName());
        onSuccess(player);
    }

    private void processQualityCommand(String[] strings, EntityPlayer player, ItemStack equippedItem) {
        int qualityLvl = Integer.parseInt(strings[2]);
        if (0 <= qualityLvl && qualityLvl <= 200) {
            equippedItem = ToolHelper.setQuality(equippedItem, qualityLvl);
            //this is only approximate trait calculation
            if (qualityLvl <= 50) {
                equippedItem.stackTagCompound.setBoolean("MF_Inferior", true);
            }
            if (qualityLvl >= 150) {
                equippedItem.stackTagCompound.setBoolean("MF_Inferior", false);
            }
            onSuccess(player);
        }
    }

    private void processUnbreakableCommand(String[] strings, EntityPlayer player, ItemStack equippedItem) {
        boolean isUnbreakable = Boolean.parseBoolean(strings[2]);
        ToolHelper.setUnbreakable(equippedItem, isUnbreakable);
        onSuccess(player);
    }

    private void onSuccess(EntityPlayer player) {
        player.sendMessage(new TextComponentString(I18n.translateToLocal("command.edit.success")));
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender iCommandSender) {
        return true;
    }

    @Override
    public List addTabCompletionOptions(ICommandSender iCommandSender, String[] strings) {
        if (strings[0].equalsIgnoreCase("edit")) {
            if (strings.length == 2) {
                return Arrays.asList("material", "quality", "unbreakable");
            }

            if (strings.length == 3) {
                if (strings[2].equalsIgnoreCase("material")) {
                    return materials;
                }

                if (strings[2].equalsIgnoreCase("unbreakable")) {
                    return Arrays.asList("true", "false");
                }
            }
        }

        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return null;
    }

    @Override
    public List<String> getAliases() {
        return null;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return false;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] strings, int i) {
        return false;
    }

    @Override
    public int compareTo(ICommand o) {
        return 0;
    }
}
