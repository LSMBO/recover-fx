package fr.lsmbo.msda.recover.gui.model;
/**
 * Enumeration type of applied filters
 * @author Aromdhani
 *
 */
public enum AppliedFilters {
	NONE("No Filter"), CURRENT("Current Filters"), LOADED("Loaded filters");
	private final String display;

	AppliedFilters(String display) {
		this.display = display;
	}

	@Override
	public String toString() {
		return display;
	}
}
