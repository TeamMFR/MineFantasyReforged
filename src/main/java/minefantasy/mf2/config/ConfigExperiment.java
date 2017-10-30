package minefantasy.mf2.config;

import minefantasy.mf2.api.helpers.ArmourCalculator;

public class ConfigExperiment extends ConfigurationBaseMF {
    public static final String CATEGORY_EARLY = "Unfinished Features";
    public static final String CATEGORY_ARCHERY = "Archery Features";
    public static final String CATEGORY_ARMOUR = "Armour Features";
    public static final String CATEGORY_FOOD = "Experimental: Hunger Modification";
    public static boolean stickArrows;
    public static boolean dynamicArrows;
    public static String debug = "";

    @Override
    protected void loadConfig() {
        // RPGElements.isSystemActive = Boolean.parseBoolean(config.get(CATEGORY_EARLY,
        // "Enable Skills", false, "This will be a later feature where skills determine
        // a number of actions ingame, also will be used to unlock perks").getString());
        stickArrows = Boolean.parseBoolean(config
                .get(CATEGORY_ARCHERY, "Save Arrows", true,
                        "With this active; arrows fired into enemies save, so they can be dropped on death")
                .getString());
        dynamicArrows = Boolean.parseBoolean(config.get(CATEGORY_ARCHERY, "Dynamic arrow sync", true,
                "This is for whatever hosts the world (singleplayer, or servers); it increases the rate arrows sync their data for smoother rendering for players. This however increases packet traffic. If you have a lot of players on a server or a lot of arrows, disable this to help clean it up")
                .getString());

        String AADesc = "This feature Sets supported armours to calculate weapons differently: \n"
                + "Damage is divided into cutting and blunt, and MF armours will alter their ratings depending \n"
                + "Both weapons and ranged entities can be registered to deliver a certain ratio \n"
                + "Armours can't be registered, all coding needs to be hardwired into the code themselves \n"
                + "So this means mod armours can not support this, they need to have a system built-in to their own coding"
                + "But this ratio only effects armours that are told to. so it should have no effect on unsupported armours, being balanced as usual \n"
                + "Undefined weapon damages however have no effect either, but it will try and guess \n"
                + "This will also change change axes and blunt weapons to match blade damage (since armour is vulnerable to blunt";
        ArmourCalculator.advancedDamageTypes = Boolean
                .parseBoolean(config.get(CATEGORY_ARMOUR, "Advanced Armour Calculator", true, AADesc).getString());
        debug = config.get("###Enable Debug Mode###", "Debug Passcode", "").getString();
    }

}
