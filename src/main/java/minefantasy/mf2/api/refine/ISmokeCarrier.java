package minefantasy.mf2.api.refine;

public interface ISmokeCarrier {
    public int getSmokeValue();

    public void setSmokeValue(int smoke);

    public int getMaxSmokeStorage();

    public boolean canAbsorbIndirect();
}
