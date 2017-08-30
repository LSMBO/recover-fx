package fr.lsmbo.msda.recover.model.settings;

public class HighIntensityThresholdFilterSettings {

	private Boolean selectedFilter;
	private Integer mostIntensePeaksToConsider;
	private Float percentageOfTopLine;
	private Integer maxNumberOfPeaks;

	public HighIntensityThresholdFilterSettings() {
	}

	public HighIntensityThresholdFilterSettings(Boolean selectedFilter, Integer mostIntensePeaksToConsider,
			Float percentageOfTopLine, Integer maxNumberOfPeaks) {
		super();
		this.selectedFilter = selectedFilter;
		this.mostIntensePeaksToConsider = mostIntensePeaksToConsider;
		this.percentageOfTopLine = percentageOfTopLine;
		this.maxNumberOfPeaks = maxNumberOfPeaks;
	}

	public Boolean getSelectedFilter() {
		return selectedFilter;
	}

	public void setSelectedFilter(Boolean selectedFilter) {
		this.selectedFilter = selectedFilter;
	}

	public Integer getMostIntensePeaksToConsider() {
		return mostIntensePeaksToConsider;
	}

	public void setMostIntensePeaksToConsider(Integer mostIntensePeaksToConsider) {
		this.mostIntensePeaksToConsider = mostIntensePeaksToConsider;
	}

	public Float getPercentageOfTopLine() {
		return percentageOfTopLine;
	}

	public void setPercentageOfTopLine(Float percentageOfTopLine) {
		this.percentageOfTopLine = percentageOfTopLine;
	}

	public Integer getMaxNumberOfPeaks() {
		return maxNumberOfPeaks;
	}

	public void setMaxNumberOfPeaks(Integer maxNumberOfPeaks) {
		this.maxNumberOfPeaks = maxNumberOfPeaks;
	}
	
	@Override
	public String toString() {
		return 
				"selectedFilter: "+selectedFilter+ "\n" + 
				"mostIntensePeaksToConsider: "+mostIntensePeaksToConsider+ "\n" + 
				"percentageOfTopLine: "+percentageOfTopLine+ "\n" + 
				"maxNumberOfPeaks: "+maxNumberOfPeaks;
	}

}
