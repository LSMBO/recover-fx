package fr.lsmbo.msda.recover.model.settings;

public class ChargeStateFilterSettings extends RecoverSetting {

	private Boolean selectedFilter;
	private Boolean keepCharge1;
	private Boolean keepCharge2;
	private Boolean keepCharge3;
	private Boolean keepCharge4;
	private Boolean keepCharge5;
	private Boolean keepChargeAbove5;
	private Boolean keepUnknownCharge;

	public ChargeStateFilterSettings() {
		this.initialize();
	}

	public ChargeStateFilterSettings(Boolean selectedFilter, Boolean keepCharge1, Boolean keepCharge2,
			Boolean keepCharge3, Boolean keepCharge4, Boolean keepCharge5, Boolean keepChargeAbove5,
			Boolean keepUnknownCharge) {
		super();
		this.selectedFilter = selectedFilter;
		this.keepCharge1 = keepCharge1;
		this.keepCharge2 = keepCharge2;
		this.keepCharge3 = keepCharge3;
		this.keepCharge4 = keepCharge4;
		this.keepCharge5 = keepCharge5;
		this.keepChargeAbove5 = keepChargeAbove5;
		this.keepUnknownCharge = keepUnknownCharge;
		this.initialize();
	}

	private void initialize() {
		this.name = "Charge state";
		this.description = ""; // TODO write a proper description
	}

	public Boolean getSelectedFilter() {
		return selectedFilter;
	}

	public void setSelectedFilter(Boolean selectedFilter) {
		this.selectedFilter = selectedFilter;
	}

	public Boolean getKeepCharge1() {
		return keepCharge1;
	}

	public void setKeepCharge1(Boolean keepCharge1) {
		this.keepCharge1 = keepCharge1;
	}

	public Boolean getKeepCharge2() {
		return keepCharge2;
	}

	public void setKeepCharge2(Boolean keepCharge2) {
		this.keepCharge2 = keepCharge2;
	}

	public Boolean getKeepCharge3() {
		return keepCharge3;
	}

	public void setKeepCharge3(Boolean keepCharge3) {
		this.keepCharge3 = keepCharge3;
	}

	public Boolean getKeepCharge4() {
		return keepCharge4;
	}

	public void setKeepCharge4(Boolean keepCharge4) {
		this.keepCharge4 = keepCharge4;
	}

	public Boolean getKeepCharge5() {
		return keepCharge5;
	}

	public void setKeepCharge5(Boolean keepCharge5) {
		this.keepCharge5 = keepCharge5;
	}

	public Boolean getKeepChargeAbove5() {
		return keepChargeAbove5;
	}

	public void setKeepChargeAbove5(Boolean keepChargeAbove5) {
		this.keepChargeAbove5 = keepChargeAbove5;
	}

	public Boolean getKeepUnknownCharge() {
		return keepUnknownCharge;
	}

	public void setKeepUnknownCharge(Boolean keepUnknownCharge) {
		this.keepUnknownCharge = keepUnknownCharge;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("selectedFilter: ").append(selectedFilter).append("\n").append("selectedFilter: ")
				.append(selectedFilter).append("\n").append("keepCharge1: ").append(keepCharge1).append("\n")
				.append("keepCharge2: ").append(keepCharge2).append("\n").append("keepCharge3: ").append(keepCharge3)
				.append("\n").append("keepCharge4: ").append(keepCharge4).append("\n").append("keepCharge5: ")
				.append(keepCharge5).append("\n").append("keepChargeAbove5: ").append(keepChargeAbove5).append("\n")
				.append("keepUnknownCharge: ").append(keepUnknownCharge);
		return str.toString();
	}

}
