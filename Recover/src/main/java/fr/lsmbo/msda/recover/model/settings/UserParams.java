package fr.lsmbo.msda.recover.model.settings;

public class UserParams extends RecoverSetting {

	private String userName;
	private String timestamp;
	private String recoverVersion;

	private QualityFiltersSettings qualityFilters;
	private ChartSettings chart;
	private ComparisonSettings comparison;
	private ParsingRulesSettings parsingRules;

	public UserParams() {
		this("", "", "", new QualityFiltersSettings(), new ChartSettings(), new ComparisonSettings(),
				new ParsingRulesSettings());
	}

	public UserParams(QualityFiltersSettings qualityFilters, ChartSettings chart, ComparisonSettings comparison,
			ParsingRulesSettings parsingRules) {
		this("", "", "", qualityFilters, chart, comparison, parsingRules);
	}

	public UserParams(String userName, String timestamp, String recoverVersion, QualityFiltersSettings qualityFilters,
			ChartSettings chart, ComparisonSettings comparison, ParsingRulesSettings parsingRules) {
		super();
		this.userName = userName;
		this.timestamp = timestamp;
		this.recoverVersion = recoverVersion;
		this.qualityFilters = qualityFilters;
		this.chart = chart;
		this.comparison = comparison;
		this.parsingRules = parsingRules;
		this.initialize();
	}

	private void initialize() {
		this.name = "Current user parameters";
		this.description = ""; // TODO write a proper description
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getRecoverVersion() {
		return recoverVersion;
	}

	public void setRecoverVersion(String recoverVersion) {
		this.recoverVersion = recoverVersion;
	}

	public QualityFiltersSettings getQualityFilters() {
		return qualityFilters;
	}

	public void setQualityFilters(QualityFiltersSettings qualityFilters) {
		this.qualityFilters = qualityFilters;
	}

	public ChartSettings getChart() {
		return chart;
	}

	public void setChart(ChartSettings chart) {
		this.chart = chart;
	}

	public ComparisonSettings getComparison() {
		return comparison;
	}

	public void setComparison(ComparisonSettings comparison) {
		this.comparison = comparison;
	}

	public ParsingRulesSettings getParsingRules() {
		return parsingRules;
	}

	public void setParsingRules(ParsingRulesSettings parsingRules) {
		this.parsingRules = parsingRules;
	}

	// // TODO this method is meant to be removed
	// public void feedParamsWithBogusValues() {
	// this.userName = System.getProperty("user.name");
	// this.timestamp = ""+new Timestamp(System.currentTimeMillis());
	// this.recoverVersion = Session.RECOVER_RELEASE_VERSION;
	// this.qualityFilters = new QualityFiltersSettings(
	// new ChargeStateFilterSettings(false, false, true, true, true, true, true,
	// false),
	// new FragmentIntensityFilterSettings(false, ">", 5),
	// new HighIntensityThresholdFilterSettings(false, 1000, 20f, 5),
	// new IdentificationFilterSettings(false, true, true, "file path", "sheet
	// name", "C", 2),
	// new IonReporterFilterSettings(false, new ArrayList<IonReporter>()),
	// new LowIntensityThresholdFilterSettings(true, "MEDIAN", 3f, 5, 15),
	// new PrecursorIntensityFilterSettings(false, ">", 10),
	// new WrongChargeFilterSettings(false));
	// this.chart = new ChartSettings(false);
	// this.comparison = new ComparisonSettings(0.07f, 3, 8, 4, 30);
	// this.parsingRules = new ParsingRulesSettings("ABUpr", ".*");
	// }

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("User: ").append(this.userName).append("\n").append("Date: ").append(this.timestamp).append("\n")
				.append("Version: ").append(this.recoverVersion).append("\n").append("Quality: ")
				.append(this.qualityFilters.toString()).append("\n").append("Chart: ").append(this.chart.toString())
				.append("\n").append("Comp: ").append(this.comparison.toString()).append("\n").append("PR: ")
				.append(this.parsingRules.toString());
		return str.toString();
	}
}
