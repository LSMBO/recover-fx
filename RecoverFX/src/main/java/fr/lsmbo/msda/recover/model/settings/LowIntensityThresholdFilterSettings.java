package fr.lsmbo.msda.recover.model.settings;

public class LowIntensityThresholdFilterSettings {

	private Boolean selectedFilter;
	private String method;
	private Float emergence;
	private Integer minUPN;
	private Integer maxUPN;

	public LowIntensityThresholdFilterSettings() {
	}

	public LowIntensityThresholdFilterSettings(Boolean selectedFilter, String method, Float emergence, Integer minUPN,
			Integer maxUPN) {
		super();
		this.selectedFilter = selectedFilter;
		this.method = method;
		this.emergence = emergence;
		this.minUPN = minUPN;
		this.maxUPN = maxUPN;
	}

	public Boolean getSelectedFilter() {
		return selectedFilter;
	}

	public void setSelectedFilter(Boolean selectedFilter) {
		this.selectedFilter = selectedFilter;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Float getEmergence() {
		return emergence;
	}

	public void setEmergence(Float emergence) {
		this.emergence = emergence;
	}

	public Integer getMinUPN() {
		return minUPN;
	}

	public void setMinUPN(Integer minUPN) {
		this.minUPN = minUPN;
	}

	public Integer getMaxUPN() {
		return maxUPN;
	}

	public void setMaxUPN(Integer maxUPN) {
		this.maxUPN = maxUPN;
	}
	
	@Override
	public String toString() {
		return 
				"selectedFilter: "+selectedFilter+ "\n" + 
				"method: "+method+ "\n" +  
				"emergence: "+emergence+ "\n" +
				"minUPN: "+minUPN+ "\n" + 
				"maxUPN: "+maxUPN;
	}

}
