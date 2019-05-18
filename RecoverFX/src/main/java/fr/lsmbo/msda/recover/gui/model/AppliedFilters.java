package fr.lsmbo.msda.recover.gui.model;

public enum AppliedFilters {
	NONE("No Filter"), CURRENTFILTERS("Current Filters"), LOADEDFILTERS("Loaded filters");
	private final String display;

	AppliedFilters(String display) {
		this.display = display;
	}

	@Override
	public String toString() {
		return display;
	}
}
