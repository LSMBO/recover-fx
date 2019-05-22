/*
 * 
 */
package fr.lsmbo.msda.recover.gui.model.settings;

public class LowIntensityThresholdFilterSettings extends RecoverSetting {

	private Boolean selectedFilter;
	private String method;
	private Float emergence;
	private Integer minUPN;
	private Integer maxUPN;

	public LowIntensityThresholdFilterSettings() {
		this.initialize();
	}

	public LowIntensityThresholdFilterSettings(Boolean selectedFilter, String method, Float emergence, Integer minUPN,
			Integer maxUPN) {
		super();
		this.selectedFilter = selectedFilter;
		this.method = method;
		this.emergence = emergence;
		this.minUPN = minUPN;
		this.maxUPN = maxUPN;
		this.initialize();
	}
	
	public Float getEmergence() {
		return emergence;
	}

	public Integer getMaxUPN() {
		return maxUPN;
	}

	public String getMethod() {
		return method;
	}

	public Integer getMinUPN() {
		return minUPN;
	}

	public Boolean getSelectedFilter() {
		return selectedFilter;
	}

	private void initialize() {
		this.name = "Low intensity threshold";
		this.description = ""; // TODO write a proper description
	}

	public void setEmergence(Float emergence) {
		this.emergence = emergence;
	}

	public void setMaxUPN(Integer maxUPN) {
		this.maxUPN = maxUPN;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void setMinUPN(Integer minUPN) {
		this.minUPN = minUPN;
	}

	public void setSelectedFilter(Boolean selectedFilter) {
		this.selectedFilter = selectedFilter;
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
