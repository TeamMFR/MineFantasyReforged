package minefantasy.mf2.integration.minetweaker;

import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.api.rpg.RPGElements;
import minefantasy.mf2.api.rpg.Skill;
import minetweaker.MineTweakerAPI;
import minetweaker.MineTweakerImplementationAPI;
import minetweaker.api.player.IPlayer;
import minetweaker.api.server.ICommandFunction;
import scala.actors.threadpool.Arrays;

import java.util.Map;

public class MTCommands implements ICommandFunction {

    @Override
    public void execute(String[] arguments, IPlayer player) {
        if (arguments.length < 1) {
            player.sendChat("Please, specify a MF command");
            return;
        }

        if (arguments[0].equals("materials")) {
            MineTweakerAPI.logCommand("MineFantasy materials list:");
            for (Map.Entry<String, CustomMaterial> entry : CustomMaterial.materialList.entrySet()) {
                CustomMaterial material = entry.getValue();
                MineTweakerAPI.logCommand(String.format("    %s (type: %s, color (RGB): %s, tier: %d, hardness: %.2f, durability: %.2f, flexibility: %.2f, sharpness: %.2f, resistance: %.2f, density: %.2f)", material.name, material.type, Arrays.toString(material.colourRGB), material.tier, material.hardness, material.durability, material.flexibility, material.sharpness, material.resistance, material.density));
            }

            if (player != null) {
                player.sendChat(MineTweakerImplementationAPI.platform.getMessage("Materials list generated; see minetweaker.log in your minecraft dir"));
            }
        }
        if (arguments[0].equals("skills")) {
            MineTweakerAPI.logCommand("MineFantasy skills list:");

            for (Skill skill : RPGElements.skillsList) {
                MineTweakerAPI.logCommand(String.format("Skill %s registered as %s", skill.getDisplayName(), skill.skillName));
            }

            if (player != null) {
                player.sendChat(MineTweakerImplementationAPI.platform.getMessage("Skills list generated; see minetweaker.log in your minecraft dir"));
            }
        }
    }
}
