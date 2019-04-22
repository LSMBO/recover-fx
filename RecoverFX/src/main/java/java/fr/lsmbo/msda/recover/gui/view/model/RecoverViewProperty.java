package fr.lsmbo.msda.recover.gui.view.model;

import fr.lsmbo.msda.recover.gui.Session;
import fr.lsmbo.msda.recover.gui.model.Spectrum;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/***
 * This class wraps the main view properties.
 * 
 * @author Aromdhani
 *
 */
public final class RecoverViewProperty {

	// General properties
	private ObjectProperty<Spectrum> spectrumProperty = new SimpleObjectProperty<Spectrum>();
	private StringProperty spectrumNbProperty = new SimpleStringProperty();
	private StringProperty recoveredNbProperty = new SimpleStringProperty();
	private StringProperty matchedNbProperty = new SimpleStringProperty();
	private StringProperty recoveredPercProperty = new SimpleStringProperty();
	private StringProperty identifiedNbProperty = new SimpleStringProperty();
	private StringProperty identifiedPercProperty = new SimpleStringProperty();
	private StringProperty regexProperty = new SimpleStringProperty();

	// Session Properties
	private StringProperty sessionMaxMozProperty = new SimpleStringProperty();
	private StringProperty sessionMaxIntensityProperty = new SimpleStringProperty();
	private StringProperty sessionFileNameProperty = new SimpleStringProperty();

	/**
	 * Default constructor
	 */
	public RecoverViewProperty() {
	}

	/**
	 * @return the spectrum property
	 */
	public ObjectProperty<Spectrum> getSpectrumProperty() {
		return spectrumProperty;
	}

	/**
	 * @return the spectrum
	 */
	public final Spectrum getSpectrum() {
		return spectrumProperty.getValue();
	}

	/**
	 * @param spectrumProperty
	 *            the spectrum property to set
	 */
	public final void setSpectrumProperty(ObjectProperty<Spectrum> spectrumProperty) {
		this.spectrumProperty = spectrumProperty;
	}

	/**
	 * @param spectrum
	 *            the spectrum property to set
	 */
	public final void setSpectrum(Spectrum spectrum) {
		this.spectrumProperty.setValue(spectrum);
	}

	/**
	 * @return the spectrum number property
	 */
	public StringProperty getSpectrumNbProperty() {
		return spectrumNbProperty;
	}

	/**
	 * @return the spectrum number as string
	 */
	public final String getSpectrumNb() {
		return spectrumNbProperty.getValue();
	}

	/**
	 * @param spectrumNbProperty
	 *            the spectrum number property to set
	 */
	public final void setSpectrumNbProperty(StringProperty spectrumNbProperty) {
		this.spectrumNbProperty = spectrumNbProperty;
	}

	/**
	 * @param spectrumNb
	 *            the spectrum number to set
	 */
	public final void setSpectrumNb(String spectrumNb) {
		this.spectrumNbProperty.setValue(spectrumNb);
	}

	/**
	 * @return the recovered number property
	 */
	public StringProperty getRecoveredNbProperty() {
		return recoveredNbProperty;
	}

	/**
	 * @return the recovered number
	 */
	public final String getRecoveredNb() {
		return recoveredNbProperty.getValue();
	}

	/**
	 * @param recoveredNbProperty
	 *            the recovered number property to set
	 */
	public final void setRecoveredNbProperty(StringProperty recoveredNbProperty) {
		this.recoveredNbProperty = recoveredNbProperty;
	}

	/**
	 * @param recoveredNb
	 *            the recovered number to set
	 */
	public final void setRecoveredNb(String recoveredNb) {
		this.recoveredNbProperty.setValue(recoveredNb);
	}

	/**
	 * @return the matched number property
	 */
	public StringProperty getMatchedNbProperty() {
		return matchedNbProperty;
	}

	/**
	 * @return the matched number as String
	 */
	public final String getMatchedNb() {
		return matchedNbProperty.getValue();
	}

	/**
	 * @param matchedNbProperty
	 *            the matched number property to set
	 */
	public final void setMatchedNbProperty(StringProperty matchedNbProperty) {
		this.matchedNbProperty = matchedNbProperty;
	}

	/**
	 * @param matchedNb
	 *            the matched number to set
	 */
	public final void setMatchedNb(String matchedNb) {
		this.matchedNbProperty.setValue(matchedNb);
	}

	/**
	 * @return the recovered percentage property
	 */
	public StringProperty getRecoveredPercProperty() {
		return recoveredPercProperty;
	}

	/**
	 * @return the recovered percentage
	 */
	public String getRecoveredPerc() {
		return recoveredPercProperty.getValue();
	}

	/**
	 * @param recoveredPercProperty
	 *            the recovered percentage property to set
	 */
	public final void setRecoveredPercProperty(StringProperty recoveredPercProperty) {
		this.recoveredPercProperty = recoveredPercProperty;
	}

	/**
	 * @param recoveredPerc
	 *            the recovered percentage to set
	 */
	public final void setRecoveredPerc(String recoveredPerc) {
		this.recoveredPercProperty.setValue(recoveredPerc);
	}

	/**
	 * @return the identified number property
	 */
	public StringProperty getIdentifiedNbProperty() {
		return identifiedNbProperty;
	}

	/**
	 * @return the identified number
	 */
	public String getIdentifiedNb() {
		return identifiedNbProperty.getValue();
	}

	/**
	 * @param identifiedNbProperty
	 *            the identified number property to set
	 */
	public final void setIdentifiedNbProperty(StringProperty identifiedNbProperty) {
		this.identifiedNbProperty = identifiedNbProperty;
	}

	/**
	 * @param identifiedNb
	 *            the identified number to set
	 */
	public final void setIdentifiedNb(String identifiedNb) {
		this.identifiedNbProperty.setValue(identifiedNb);
	}

	/**
	 * @return the identifiedPercProperty
	 */
	public StringProperty getIdentifiedPercProperty() {
		return identifiedPercProperty;
	}

	/**
	 * @return the identified percentage as String
	 */
	public String getIdentifiedPercentage() {
		return identifiedPercProperty.getValue();
	}

	/**
	 * @param identifiedPercProperty
	 *            the identified percentage property to set
	 */
	public final void setIdentifiedPercProperty(StringProperty identifiedPercProperty) {
		this.identifiedPercProperty = identifiedPercProperty;
	}

	/**
	 * @param identifiedPerc
	 *            the identified percentage to set
	 */
	public final void setIdentifiedPerc(String identifiedPerc) {
		this.identifiedPercProperty.setValue(identifiedPerc);
	}

	/**
	 * @return the regexProperty
	 */
	public StringProperty getRegexProperty() {
		return regexProperty;
	}

	/**
	 * @return the regex as String
	 */
	public String getRegex() {
		return regexProperty.getValue();
	}

	/**
	 * @param regexProperty
	 *            the regex property to set
	 */
	public final void setRegexProperty(StringProperty regexProperty) {
		this.regexProperty = regexProperty;
	}

	/**
	 * @param regex
	 *            the regex to set
	 */
	public final void setRegex(String regex) {
		this.regexProperty.setValue(regex);
	}

	/**
	 * @return the sessionMaxMozProperty
	 */
	public StringProperty getSessionMaxMozProperty() {
		return sessionMaxMozProperty;
	}

	/**
	 * @return the session max moz as String
	 */
	public String getSessionMaxMo() {
		return sessionMaxMozProperty.getValue();
	}

	/**
	 * @param sessionMaxMozProperty
	 *            the session max moz property to set
	 */
	public final void setSessionMaxMozProperty(StringProperty sessionMaxMozProperty) {
		this.sessionMaxMozProperty = sessionMaxMozProperty;
	}

	/**
	 * @param sessionMaxMoz
	 *            the session max moz to set
	 */
	public final void setSessionMaxMoz(String sessionMaxMoz) {
		this.sessionMaxMozProperty.setValue(sessionMaxMoz);
	}

	/**
	 * @return the sessionMaxIntensityProperty
	 */
	public StringProperty getSessionMaxIntensityProperty() {
		return sessionMaxIntensityProperty;
	}

	/**
	 * @return the session max intensity as String
	 */
	public final String getSessionMaxIntensity() {
		return sessionMaxIntensityProperty.getValue();
	}

	/**
	 * @param sessionMaxIntensityProperty
	 *            the session max intensity property to set
	 */
	public void setSessionMaxIntensityProperty(StringProperty sessionMaxIntensityProperty) {
		this.sessionMaxIntensityProperty = sessionMaxIntensityProperty;
	}

	/**
	 * @param sessionMaxIntensity
	 *            the session max intensity to set
	 */
	public void setSessionMaxIntensity(String sessionMaxIntensity) {
		this.sessionMaxIntensityProperty.setValue(sessionMaxIntensity);
	}

	/**
	 * @return the session file name property
	 */
	public StringProperty getSessionFileNameProperty() {
		return sessionFileNameProperty;
	}

	/**
	 * @return the session file name
	 */
	public final String getSessionFileName() {
		return sessionFileNameProperty.getValue();
	}

	/**
	 * @param sessionFileNameProperty
	 *            the session file name property to set
	 */
	public final void setSessionFileNameProperty(StringProperty sessionFileNameProperty) {
		this.sessionFileNameProperty = sessionFileNameProperty;
	}

	/**
	 * @param sessionFileName
	 *            the session file name property to set
	 */
	public final void setSessionFileName(String sessionFileName) {
		if (Session.CURRENT_FILE != null && Session.CURRENT_FILE.exists()) {
			this.sessionFileNameProperty.setValue(sessionFileName);
		} else {
			this.sessionFileNameProperty.setValue("empty");
		}
	}

	/***
	 * Notify changes to RecoverFx view
	 * 
	 * @param Spectrum
	 * @param spectrumNb
	 * @param identified
	 * @param identifiedPerc
	 * @param sessionFileName
	 * @param regex
	 */
	public void notify(Spectrum Spectrum, String spectrumNb, String identifiedNb, String identifiedPerc,
			String sessionFileName, String regex) {
		setSpectrum(Spectrum);
		setSpectrumNb(spectrumNb);
		setIdentifiedNb(identifiedNb);
		setIdentifiedPerc(identifiedPerc);
		setSessionFileName(sessionFileName);
		setRegex(regex);
	}

}
