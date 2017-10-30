package minefantasy.mf2.api.crafting;

public interface IBasicMetre {
    int getMetreScale(int size);

    boolean shouldShowMetre();

    String getLocalisedName();
}
