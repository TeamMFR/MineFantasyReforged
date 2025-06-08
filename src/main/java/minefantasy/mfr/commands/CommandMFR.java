package minefantasy.mfr.commands;

import minefantasy.mfr.material.CustomMaterial;
import minefantasy.mfr.registry.CustomMaterialRegistry;
import minefantasy.mfr.registry.types.CustomMaterialType;
import minefantasy.mfr.util.CustomToolHelper;
import minefantasy.mfr.util.ToolHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandMFR extends CommandBase {

	private final List aliases = new ArrayList<String>() {{
		add("mf");
		add("minefantasy");
	}};

	private List setupMaterialsList() {
		List materials = new ArrayList<String>();
		for (CustomMaterial material : CustomMaterialRegistry.getValues()) {
			if (material.getType() == CustomMaterialType.WOOD_MATERIAL || material.getType() == CustomMaterialType.METAL_MATERIAL) {
				materials.add(material);
			}
		}
		return materials;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] strings) {
		if (sender instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) sender;
			if (strings.length > 0) {
				if (strings[0].equalsIgnoreCase("edit")) {
					ItemStack equippedItem = player.getHeldItemMainhand();
					if (equippedItem.isEmpty()) {
						player.sendMessage(new TextComponentString(I18n.format("command.invalid.item")));
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
			player.sendMessage(new TextComponentString(I18n.format("command.invalid.item")));
			return;
		}

		CustomMaterial material = CustomMaterialRegistry.getMaterial(strings[2]);
		if (material == CustomMaterialRegistry.NONE) {
			player.sendMessage(new TextComponentString(I18n.format("command.edit.invalid.material")));
			return;
		}

		String slot = material.getType() == CustomMaterialType.METAL_MATERIAL ? CustomToolHelper.slot_main : CustomToolHelper.slot_haft;
		CustomMaterialRegistry.addMaterial(equippedItem, slot, material.getName());
		onSuccess(player);
	}

	private void processQualityCommand(String[] strings, EntityPlayer player, ItemStack equippedItem) {
		int qualityLvl = Integer.parseInt(strings[2]);
		if (0 <= qualityLvl && qualityLvl <= 200) {
			equippedItem = ToolHelper.setQuality(equippedItem, qualityLvl);
			//this is only approximate trait calculation
			if (qualityLvl <= 50) {
				equippedItem.getTagCompound().setBoolean("MF_Inferior", true);
			}
			if (qualityLvl >= 150) {
				equippedItem.getTagCompound().setBoolean("MF_Inferior", false);
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
		player.sendMessage(new TextComponentString(I18n.format("command.edit.success")));
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] strings, @Nullable BlockPos targetPos) {
		if (strings[0].equalsIgnoreCase("edit")) {
			if (strings.length == 2) {
				return Arrays.asList("material", "quality", "unbreakable");
			}

			if (strings.length == 3) {
				if (strings[2].equalsIgnoreCase("material")) {
					return setupMaterialsList();
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
		return "CommandMF";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "command.MF.usage";
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return false;
	}

	@Override
	public boolean isUsernameIndex(String[] strings, int i) {
		return i == 1;
	}
}
