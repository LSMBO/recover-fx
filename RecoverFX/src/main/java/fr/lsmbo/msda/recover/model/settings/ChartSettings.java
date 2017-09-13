package fr.lsmbo.msda.recover.model.settings;

public class ChartSettings extends RecoverSetting {

	private Boolean useFixedAxis;

	public ChartSettings() {
		this.initialize();
	}

	public ChartSettings(Boolean useFixedAxis) {
		super();
		this.useFixedAxis = useFixedAxis;
		this.initialize();
	}
	
	private void initialize() {
		this.name = "Chart settings";
		this.description = ""; // TODO write a proper description
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
