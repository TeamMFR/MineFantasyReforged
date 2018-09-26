package minefantasy.mf2.config;

public class ConfigTools extends ConfigurationBaseMF {
    public static final String CATEGORY_BONUS = "Bonuses";
    public static final String CATEGORY_PENALTIES = "Penalties";
    public static float handpickBonus;
    public static boolean handpickFortune;
    public static float hvyDropChance;

    @Override
    protected void loadConfig() {
        handpickBonus = Float.parseFloat(config.get(CATEGORY_BONUS, "Handpick double-drop chance", 20F,
                "This is the percent value (0-100) handpicks double item drops").getString());

        handpickFortune = Boolean.getBoolean(config.get(CATEGORY_BONUS, "Fortune enchantment for handpick", false,
                "Be careful, may ruin balance!").getString());

        hvyDropChance = Float.parseFloat(config.get(CATEGORY_PENALTIES, "Heavy ruin-drop chance", 25F,
                "This is the percent value (0-100) heavy picks/shovels and lumber axe do NOT drop blocks on break")
                .getString());
    }
}