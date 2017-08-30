package fr.lsmbo.msda.recover.model.settings;

public class FragmentIntensityFilterSettings {

	private Boolean selectedFilter;
	private String operator;
	private Integer intensity;

	public FragmentIntensityFilterSettings() {
	}

	public FragmentIntensityFilterSettings(Boolean selectedFilter, String operator, Integer intensity) {
		super();
		this.selectedFilter = selectedFilter;
		this.operator = operator;
		this.intensity = intensity;
	}

	public Boolean getSelectedFilter() {
		return selectedFilter;
	}

	public void setSelectedFilter(Boolean selectedFilter) {
		this.selectedFilter = selectedFilter;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Integer getIntensity() {
		return intensity;
	}

	public void setIntensity(Integer intensity) {
		this.intensity = intensity;
	}
	
	@Override
	public String toString() {
		return 
				"selectedFilter: "+selectedFilter+ "\n" + 
				"operator: "+operator+ "\n" + 
				"intensity: "+intensity;
	}

}
