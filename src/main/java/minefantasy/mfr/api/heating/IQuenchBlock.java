package minefantasy.mfr.api.heating;

public interface IQuenchBlock {
	/**
	 * Quench an item
	 *
	 * @return 0 means success, 1-100 means item is damaged, <0 means fail.
	 */
	float quench();
}
