package fr.lsmbo.msda.recover.gui.model.settings;

public class ComparisonSettings extends RecoverSetting {

	private Float deltaMoz;
	private Integer deltaRT;
	private Integer numberOfPeaksToCompare;
	private Integer minimumNumberOfIdenticalPeaks;
	private Integer minimumThetaAngle;

	public ComparisonSettings() {
		this.initialize();
	}

	public ComparisonSettings(Float deltaMoz, Integer deltaRT, Integer numberOfPeaksToCompare,
			Integer minimumNumberOfIdenticalPeaks, Integer minimumThetaAngle) {
		super();
		this.deltaMoz = deltaMoz;
		this.deltaRT = deltaRT;
		this.numberOfPeaksToCompare = numberOfPeaksToCompare;
		this.minimumNumberOfIdenticalPeaks = minimumNumberOfIdenticalPeaks;
		this.minimumThetaAngle = minimumThetaAngle;
		this.initialize();
	}

	public Float getDeltaMoz() {
		return deltaMoz;
	}

	public Integer getDeltaRT() {
		return deltaRT;
	}

	public Integer getMinimumNumberOfIdenticalPeaks() {
		return minimumNumberOfIdenticalPeaks;
	}

	public Integer getMinimumThetaAngle() {
		return minimumThetaAngle;
	}

	public Integer getNumberOfPeaksToCompare() {
		return numberOfPeaksToCompare;
	}

	private void initialize() {
		this.name = "Spectrum comparison settings";
		this.description = ""; // TODO write a proper description
	}

	public void setDeltaMoz(Float deltaMoz) {
		this.deltaMoz = deltaMoz;
	}

	public void setDeltaRT(Integer deltaRT) {
		this.deltaRT = deltaRT;
	}

	public void setMinimumNumberOfIdenticalPeaks(Integer minimumNumberOfIdenticalPeaks) {
		this.minimumNumberOfIdenticalPeaks = minimumNumberOfIdenticalPeaks;
	}

	public void setMinimumThetaAngle(Integer minimumThetaAngle) {
		this.minimumThetaAngle = minimumThetaAngle;
	}

	public void setNumberOfPeaksToCompare(Integer numberOfPeaksToCompare) {
		this.numberOfPeaksToCompare = numberOfPeaksToCompare;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("\n").append("##deltaMoz: ").append(deltaMoz).append("\n").append("##numberOfPeaksToCompare: ")
				.append(numberOfPeaksToCompare).append("\n").append("##deltaRT: ").append(deltaRT).append("\n")
				.append("##minimumNumberOfIdenticalPeaks: ").append(minimumNumberOfIdenticalPeaks).append("\n")
				.append("##minimumThetaAngle: ").append(minimumThetaAngle);
		return str.toString();
	}

}
