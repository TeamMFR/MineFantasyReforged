package minefantasy.mfr.api.crafting;

public interface IBasicMetre {
    int getMetreScale(int size);

    boolean shouldShowMetre();

    String getLocalisedName();
}
