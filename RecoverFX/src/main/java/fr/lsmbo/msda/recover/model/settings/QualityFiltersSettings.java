package fr.lsmbo.msda.recover.model.settings;

public class QualityFiltersSettings extends RecoverSetting {

	private ChargeStateFilterSettings chargeStateFilter;
	private FragmentIntensityFilterSettings fragmentIntensityFilter;
	private HighIntensityThresholdFilterSettings highIntensityThresholdFilter;
	private IdentificationFilterSettings identificationFilter;
	private IonReporterFilterSettings ionReporterFilter;
	private LowIntensityThresholdFilterSettings lowIntensityThresholdFilter;
	private PrecursorIntensityFilterSettings precursorIntensityFilter;
	private WrongChargeFilterSettings WrongChargeFilter;

	public QualityFiltersSettings() {
		this.chargeStateFilter = new ChargeStateFilterSettings();
		this.fragmentIntensityFilter = new FragmentIntensityFilterSettings();
		this.highIntensityThresholdFilter = new HighIntensityThresholdFilterSettings();
		this.identificationFilter = new IdentificationFilterSettings();
		this.ionReporterFilter = new IonReporterFilterSettings();
		this.lowIntensityThresholdFilter = new LowIntensityThresholdFilterSettings();
		this.precursorIntensityFilter = new PrecursorIntensityFilterSettings();
		this.WrongChargeFilter = new WrongChargeFilterSettings();
		this.initialize();
	}

	public QualityFiltersSettings(ChargeStateFilterSettings chargeStateFilter,
			FragmentIntensityFilterSettings fragmentIntensityFilter,
			HighIntensityThresholdFilterSettings highIntensityThresholdFilter,
			IdentificationFilterSettings identificationFilter, IonReporterFilterSettings ionReporterFilter,
			LowIntensityThresholdFilterSettings lowIntensityThresholdFilter,
			PrecursorIntensityFilterSettings precursorIntensityFilter, WrongChargeFilterSettings wrongChargeFilter) {
		super();
		this.chargeStateFilter = chargeStateFilter;
		this.fragmentIntensityFilter = fragmentIntensityFilter;
		this.highIntensityThresholdFilter = highIntensityThresholdFilter;
		this.identificationFilter = identificationFilter;
		this.ionReporterFilter = ionReporterFilter;
		this.lowIntensityThresholdFilter = lowIntensityThresholdFilter;
		this.precursorIntensityFilter = precursorIntensityFilter;
		this.WrongChargeFilter = wrongChargeFilter;
		this.initialize();
	}
	
	private void initialize() {
		this.name = "Quality filters";
		this.description = ""; // TODO write a proper description
	}

	public ChargeStateFilterSettings getChargeStateFilter() {
		return chargeStateFilter;
	}

	public void setChargeStateFilter(ChargeStateFilterSettings chargeStateFilter) {
		this.chargeStateFilter = chargeStateFilter;
	}

	public FragmentIntensityFilterSettings getFragmentIntensityFilter() {
		return fragmentIntensityFilter;
	}

	public void setFragmentIntensityFilter(FragmentIntensityFilterSettings fragmentIntensityFilter) {
		this.fragmentIntensityFilter = fragmentIntensityFilter;
	}

	public HighIntensityThresholdFilterSettings getHighIntensityThresholdFilter() {
		return highIntensityThresholdFilter;
	}

	public void setHighIntensityThresholdFilter(HighIntensityThresholdFilterSettings highIntensityThresholdFilter) {
		this.highIntensityThresholdFilter = highIntensityThresholdFilter;
	}

	public IdentificationFilterSettings getIdentificationFilter() {
		return identificationFilter;
	}

	public void setIdentificationFilter(IdentificationFilterSettings identificationFilter) {
		this.identificationFilter = identificationFilter;
	}

	public IonReporterFilterSettings getIonReporterFilter() {
		return ionReporterFilter;
	}

	public void setIonReporterFilter(IonReporterFilterSettings ionReporterFilter) {
		this.ionReporterFilter = ionReporterFilter;
	}

	public LowIntensityThresholdFilterSettings getLowIntensityThresholdFilter() {
		return lowIntensityThresholdFilter;
	}

	public void setLowIntensityThresholdFilter(LowIntensityThresholdFilterSettings lowIntensityThresholdFilter) {
		this.lowIntensityThresholdFilter = lowIntensityThresholdFilter;
	}

	public PrecursorIntensityFilterSettings getPrecursorIntensityFilter() {
		return precursorIntensityFilter;
	}

	public void setPrecursorIntensityFilter(PrecursorIntensityFilterSettings precursorIntensityFilter) {
		this.precursorIntensityFilter = precursorIntensityFilter;
	}

	public WrongChargeFilterSettings getWrongChargeFilter() {
		return WrongChargeFilter;
	}

	public void setWrongChargeFilter(WrongChargeFilterSettings wrongChargeFilter) {
		WrongChargeFilter = wrongChargeFilter;
	}
	
	@Override
	public String toString() {
		return 
				"chargeStateFilter: "+chargeStateFilter+ "\n" + 
				"fragmentIntensityFilter: "+fragmentIntensityFilter+ "\n" + 
				"highIntensityThresholdFilter: "+highIntensityThresholdFilter+ "\n" + 
				"identificationFilter: "+identificationFilter+ "\n" + 
				"ionReporterFilter: "+ionReporterFilter+ "\n" + 
				"lowIntensityThresholdFilter: "+lowIntensityThresholdFilter+ "\n" + 
				"precursorIntensityFilter: "+precursorIntensityFilter+ "\n" + 
				"WrongChargeFilter: "+WrongChargeFilter;
	}

}
