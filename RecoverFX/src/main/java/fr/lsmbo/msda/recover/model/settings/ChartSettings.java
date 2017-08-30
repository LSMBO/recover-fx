package fr.lsmbo.msda.recover.model.settings;

public class ChartSettings {

	private Boolean useFixedAxis;

	public ChartSettings() {
	}

	public ChartSettings(Boolean useFixedAxis) {
		super();
		this.useFixedAxis = useFixedAxis;
	}

	public Boolean getUseFixedAxis() {
		return useFixedAxis;
	}

	public void setUseFixedAxis(Boolean useFixedAxis) {
		this.useFixedAxis = useFixedAxis;
	}

	@Override
	public String toString() {
		return "useFixedAxis: "+useFixedAxis;
	}
}
