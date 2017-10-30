package minefantasy.mf2.config;

import minefantasy.mf2.api.stamina.StaminaBar;

public class ConfigStamina extends ConfigurationBaseMF {
    public static String VALUES = "Value Modifiers";

    public static float exhaustDamage;
    public static float weaponModifier;
    public static float sprintModifier;
    public static int fullRegenSeconds;
    public static float weaponDrain;
    public static float bowModifier;
    public static float miningSpeed;

    public static String OPTION = "Options";

    public static boolean affectSpeed;
    public static boolean affectMining;

    public static String OTHER = "Misc";

    @Override
    protected void loadConfig() {
        StaminaBar.isSystemActive = Boolean.parseBoolean(config.get("##Activate Stamina System##", "Is enabled", true,
                "This is the main switch for the stamina bar and all it's features. Setting false disables the system entirely")
                .getString());

        StaminaBar.decayModifierCfg = Float
                .parseFloat(config.get(VALUES, "Decay Modifier", 1.0F, "This modifies the rate of decay").getString());
        StaminaBar.configRegenModifier = Float
                .parseFloat(config.get(VALUES, "Regen Modifier", 1.0F, "This modifies the rate of regen").getString());
        StaminaBar.pauseModifier = Float.parseFloat(
                config.get(VALUES, "Idle Modifier", 1.0F, "This modifies the time between decaying and regenning back")
                        .getString());
        StaminaBar.configBulk = Float.parseFloat(config.get(VALUES, "Bulk Regen Modifier", 1.0F,
                "This is the rate that worn apparel slows regen: Most items bring it down by 25%, plate is more scale/leather is less")
                .getString());
        StaminaBar.scaleDifficulty = Boolean.parseBoolean(config.get(VALUES, "Difficulty Scale Decay", false,
                "This makes difficulty change your decay rate across all fields: peaceful(-50%), easy(-25%), normal(base), hard(+25%)")
                .getString());
        StaminaBar.defaultMax = Float.parseFloat(config.get(VALUES, "Max stamina base", 100F,
                "This is where your stamina starts on spawning, Note that when changed: it only applies on death or new worlds")
                .getString());
        // StaminaBar.restrictSystem = Boolean.parseBoolean(config.get("Restrict use to
        // players", "Will restrict", false, "This restricts the stamina system only to
        // players").getString());

        weaponModifier = Float.parseFloat(
                config.get(VALUES, "Weapon Modifier", 1.0F, "This modifies the amount using weapons influences stamina")
                        .getString());
        sprintModifier = Float.parseFloat(
                config.get(VALUES, "Sprint Modifier", 1.0F, "Modify how fast sprinting decays stamina").getString());
        fullRegenSeconds = Integer.parseInt(config.get(VALUES, "Regen Time", 15,
                "Base time(seconds) until the metre is refilled(this is the base, other variables will be modified)")
                .getString());
        weaponDrain = Float.parseFloat(config.get(VALUES, "Exhausted Damage Modifier", 0.85F,
                "This is how being exhausted(empty stamina) influences your damage as a decimal. 0.85 = 85%. A value more than 1 increases the damage when out of stamina")
                .getString());
        bowModifier = Float.parseFloat(
                config.get(VALUES, "Bow Time", 1.0F, "Modifies the rate drawing bows will drain stamina").getString());
        StaminaBar.configArmourWeightModifier = Float.parseFloat(config
                .get(VALUES, "Weight Modifier", 1.0F, "Modifies the amount weight of armour slows decay").getString());
        miningSpeed = Float.parseFloat(config
                .get(VALUES, "Mining Modifier", 1.0F, "Modifies the rate block breaking drains stamina").getString());
        exhaustDamage = Float.parseFloat(config.get(VALUES, "Exhausted Damage Modifier", 1.5F,
                "How much damage you take when out of stamina (1.5 = +50% more damage)").getString());

        affectSpeed = Boolean.parseBoolean(
                config.get(OPTION, "Affect Speed", true, "Slowness effect adds when out of stamina").getString());
        affectMining = Boolean.parseBoolean(config
                .get(OPTION, "Affect Mining", true, "Breaking blocks drains stamina, adds mining fatigue").getString());

        StaminaBar.levelUp = Boolean.parseBoolean(
                config.get(OTHER, "Level Max Stamina", false, "This makes your experience bar increase max stamina")
                        .getString());
        StaminaBar.levelAmount = Float.parseFloat(config
                .get(OTHER, "Level Up Amount", 5F, "How much an experience level increases stamina max").getString());
    }

}
