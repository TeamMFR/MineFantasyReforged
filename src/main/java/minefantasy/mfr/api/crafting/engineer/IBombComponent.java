package minefantasy.mfr.api.crafting.engineer;

public interface IBombComponent {

	/**
	 * The type of component only supported types (minecase, bombcase, filling)
	 */
	String getComponentType();

	/**
	 * The tier, this depends on the component type
	 */
	byte getTier();
}
