package minefantasy.mf2.mechanics;

public class TierHelper {
    /**
     * This gets the "points" on how powerful food is (This is used to help balance
     * cost)
     */
    public static int getPointsWorthForFood(int hunger, float saturation, float staminaMax, float staminaMaxTime,
                                            float staminaSpeed, float staminaSpeedTime) {
        float satCost = 5F;// Pts for each hour
        float boostCost = 1F;// Pts for each 1 per day
        float regenCost = 2F;// Pts for each 1s per minute

        float staminaPoints = (staminaMax) * (staminaMaxTime / 1200F);
        float staminaRegenPoints = (staminaSpeed) * (staminaSpeedTime / 60F);

        float pts = 0;
        pts += saturation / 3600F * satCost;
        pts += staminaPoints * boostCost;
        pts += staminaRegenPoints * regenCost;

        return (int) pts;
    }
}
