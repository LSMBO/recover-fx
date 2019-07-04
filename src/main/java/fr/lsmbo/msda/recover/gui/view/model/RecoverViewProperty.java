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
	private StringProperty spectrumNbProperty = new SimpleStringProperty("0");
	private StringProperty filteredNbProperty = new SimpleStringProperty("0");
	private StringProperty filteredPercProperty = new SimpleStringProperty("0");
	private StringProperty identifiedNbProperty = new SimpleStringProperty("0");
	private StringProperty identifiedPercProperty = new SimpleStringProperty("0");
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
	 * @return the filtered number
	 */
	public final String getFilteredNb() {
		return filteredNbProperty.getValue();
	}

	/**
	 * @return the filtered number property
	 */
	public StringProperty getFilteredNbProperty() {
		return filteredNbProperty;
	}

	/**
	 * @return the filtered percentage
	 */
	public String getFilteredPerc() {
		return filteredPercProperty.getValue();
	}

	/**
	 * @return the filtered percentage property
	 */
	public StringProperty getFilteredPercProperty() {
		return filteredPercProperty;
	}

	/**
	 * @return the identified number
	 */
	public String getIdentifiedNb() {
		return identifiedNbProperty.getValue();
	}

	/**
	 * @return the identified number property
	 */
	public StringProperty getIdentifiedNbProperty() {
		return identifiedNbProperty;
	}

	/**
	 * @return the identified percentage as String
	 */
	public String getIdentifiedPercentage() {
		return identifiedPercProperty.getValue();
	}

	/**
	 * @return the identifiedPercProperty
	 */
	public StringProperty getIdentifiedPercProperty() {
		return identifiedPercProperty;
	}

	/**
	 * @return the regex as String
	 */
	public String getRegex() {
		return regexProperty.getValue();
	}

	/**
	 * @return the regexProperty
	 */
	public StringProperty getRegexProperty() {
		return regexProperty;
	}

	/**
	 * @return the session file name
	 */
	public final String getSessionFileName() {
		return sessionFileNameProperty.getValue();
	}

	/**
	 * @return the session file name property
	 */
	public StringProperty getSessionFileNameProperty() {
		return sessionFileNameProperty;
	}

	/**
	 * @return the session max intensity as String
	 */
	public final String getSessionMaxIntensity() {
		return sessionMaxIntensityProperty.getValue();
	}

	/**
	 * @return the sessionMaxIntensityProperty
	 */
	public StringProperty getSessionMaxIntensityProperty() {
		return sessionMaxIntensityProperty;
	}

	/**
	 * @return the session max moz as String
	 */
	public String getSessionMaxMo() {
		return sessionMaxMozProperty.getValue();
	}

	/**
	 * @return the sessionMaxMozProperty
	 */
	public StringProperty getSessionMaxMozProperty() {
		return sessionMaxMozProperty;
	}

	/**
	 * @return the spectrum
	 */
	public final Spectrum getSpectrum() {
		return spectrumProperty.getValue();
	}

	/**
	 * @return the spectrum number as string
	 */
	public final String getSpectrumNb() {
		return spectrumNbProperty.getValue();
	}

	/**
	 * @return the spectrum number property
	 */
	public StringProperty getSpectrumNbProperty() {
		return spectrumNbProperty;
	}

	/**
	 * @return the spectrum property
	 */
	public ObjectProperty<Spectrum> getSpectrumProperty() {
		return spectrumProperty;
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
	public void notify(Spectrum Spectrum, String spectrumNb, String filteredNb, String filteredPerc,
			String identifiedNb, String identifiedPerc, String sessionFileName, String regex) {
		setSpectrum(Spectrum);
		setSpectrumNb(spectrumNb);
		setFilteredNb(filteredNb);
		setFilteredPerc(filteredPerc);
		setIdentifiedPerc(identifiedPerc);
		setIdentifiedNb(identifiedNb);
		setIdentifiedPerc(identifiedPerc);
		setSessionFileName(sessionFileName);
		setRegex(regex);
	}

	/**
	 * @param filteredNb
	 *            the filtered number to set
	 */
	public final void setFilteredNb(String filteredNb) {
		this.filteredNbProperty.setValue(filteredNb);
	}

	/**
	 * @param filteredNbProperty
	 *            the filtered number property to set
	 */
	public final void setFilteredNbProperty(StringProperty filteredNbProperty) {
		this.filteredNbProperty = filteredNbProperty;
	}

	/**
	 * @param filteredPerc
	 *            the filtered percentage to set
	 */
	public final void setFilteredPerc(String filteredPerc) {
		this.filteredPercProperty.setValue(filteredPerc);
	}

	/**
	 * @param filteredPercProperty
	 *            the filtered percentage property to set
	 */
	public final void setFilteredPercProperty(StringProperty filteredPercProperty) {
		this.filteredPercProperty = filteredPercProperty;
	}

	/**
	 * @param identifiedNb
	 *            the identified number to set
	 */
	public final void setIdentifiedNb(String identifiedNb) {
		this.identifiedNbProperty.setValue(identifiedNb);
	}

	/**
	 * @param identifiedNbProperty
	 *            the identified number property to set
	 */
	public final void setIdentifiedNbProperty(StringProperty identifiedNbProperty) {
		this.identifiedNbProperty = identifiedNbProperty;
	}

	/**
	 * @param identifiedPerc
	 *            the identified percentage to set
	 */
	public final void setIdentifiedPerc(String identifiedPerc) {
		this.identifiedPercProperty.setValue(identifiedPerc);
	}

	/**
	 * @param identifiedPercProperty
	 *            the identified percentage property to set
	 */
	public final void setIdentifiedPercProperty(StringProperty identifiedPercProperty) {
		this.identifiedPercProperty = identifiedPercProperty;
	}

	/**
	 * @param regex
	 *            the regex to set
	 */
	public final void setRegex(String regex) {
		this.regexProperty.setValue(regex);
	}

	/**
	 * @param regexProperty
	 *            the regex property to set
	 */
	public final void setRegexProperty(StringProperty regexProperty) {
		this.regexProperty = regexProperty;
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

	/**
	 * @param sessionFileNameProperty
	 *            the session file name property to set
	 */
	public final void setSessionFileNameProperty(StringProperty sessionFileNameProperty) {
		this.sessionFileNameProperty = sessionFileNameProperty;
	}

	/**
	 * @param sessionMaxIntensity
	 *            the session max intensity to set
	 */
	public void setSessionMaxIntensity(String sessionMaxIntensity) {
		this.sessionMaxIntensityProperty.setValue(sessionMaxIntensity);
	}

	/**
	 * @param sessionMaxIntensityProperty
	 *            the session max intensity property to set
	 */
	public void setSessionMaxIntensityProperty(StringProperty sessionMaxIntensityProperty) {
		this.sessionMaxIntensityProperty = sessionMaxIntensityProperty;
	}

	/**
	 * @param sessionMaxMoz
	 *            the session max moz to set
	 */
	public final void setSessionMaxMoz(String sessionMaxMoz) {
		this.sessionMaxMozProperty.setValue(sessionMaxMoz);
	}

	/**
	 * @param sessionMaxMozProperty
	 *            the session max moz property to set
	 */
	public final void setSessionMaxMozProperty(StringProperty sessionMaxMozProperty) {
		this.sessionMaxMozProperty = sessionMaxMozProperty;
	}

	/**
	 * @param spectrum
	 *            the spectrum property to set
	 */
	public final void setSpectrum(Spectrum spectrum) {
		this.spectrumProperty.setValue(spectrum);
	}

	/**
	 * @param spectrumNb
	 *            the spectrum number to set
	 */
	public final void setSpectrumNb(String spectrumNb) {
		this.spectrumNbProperty.setValue(spectrumNb);
	}

	/**
	 * @param spectrumNbProperty
	 *            the spectrum number property to set
	 */
	public final void setSpectrumNbProperty(StringProperty spectrumNbProperty) {
		this.spectrumNbProperty = spectrumNbProperty;
	}

	/**
	 * @param spectrumProperty
	 *            the spectrum property to set
	 */
	public final void setSpectrumProperty(ObjectProperty<Spectrum> spectrumProperty) {
		this.spectrumProperty = spectrumProperty;
	}

}
