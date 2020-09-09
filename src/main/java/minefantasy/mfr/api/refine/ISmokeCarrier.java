package minefantasy.mfr.api.refine;

public interface ISmokeCarrier {
    int getSmokeValue();

    void setSmokeValue(int smoke);

    int getMaxSmokeStorage();

    boolean canAbsorbIndirect();
}
