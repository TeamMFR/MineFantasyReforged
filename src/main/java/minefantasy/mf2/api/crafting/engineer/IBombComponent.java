package minefantasy.mf2.api.crafting.engineer;

public interface IBombComponent {

    /**
     * The type of component only supported types (minecase, bombcase, filling)
     */
    public String getComponentType();

    /**
     * The tier, this depends on the component type
     */
    public byte getTier();
}
