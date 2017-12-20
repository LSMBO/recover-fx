package fr.lsmbo.msda.recover.model.settings;

public class HighIntensityThresholdFilterSettings extends RecoverSetting {

	private Boolean selectedFilter;
	private Integer mostIntensePeaksToConsider;
	private Float percentageOfTopLine;
	private Integer maxNumberOfPeaks;

	public HighIntensityThresholdFilterSettings() {
		this.initialize();
	}

	public HighIntensityThresholdFilterSettings(Boolean selectedFilter, Integer mostIntensePeaksToConsider,
			Float percentageOfTopLine, Integer maxNumberOfPeaks) {
		super();
		this.selectedFilter = selectedFilter;
		this.mostIntensePeaksToConsider = mostIntensePeaksToConsider;
		this.percentageOfTopLine = percentageOfTopLine;
		this.maxNumberOfPeaks = maxNumberOfPeaks;
		this.initialize();
	}

	private void initialize() {
		this.name = "High intensity threshold";
		this.description = ""; // TODO write a proper description
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
		StringBuilder str = new StringBuilder();
		str.append("selectedFilter: ").append(selectedFilter).append("\n").append("mostIntensePeaksToConsider: ")
				.append(mostIntensePeaksToConsider).append("\n").append("percentageOfTopLine: ")
				.append(percentageOfTopLine).append("\n").append("maxNumberOfPeaks: ").append(maxNumberOfPeaks);
		return str.toString();
	}

}
