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
	 * @return the filtered number property
	 */
	public StringProperty getFilteredNbProperty() {
		return filteredNbProperty;
	}

	/**
	 * @return the filtered number
	 */
	public final String getFilteredNb() {
		return filteredNbProperty.getValue();
	}

	/**
	 * @param filteredNbProperty
	 *            the filtered number property to set
	 */
	public final void setFilteredNbProperty(StringProperty filteredNbProperty) {
		this.filteredNbProperty = filteredNbProperty;
	}

	/**
	 * @param filteredNb
	 *            the filtered number to set
	 */
	public final void setFilteredNb(String filteredNb) {
		this.filteredNbProperty.setValue(filteredNb);
	}

	/**
	 * @return the filtered percentage property
	 */
	public StringProperty getFilteredPercProperty() {
		return filteredPercProperty;
	}

	/**
	 * @return the filtered percentage
	 */
	public String getFilteredPerc() {
		return filteredPercProperty.getValue();
	}

	/**
	 * @param filteredPercProperty
	 *            the filtered percentage property to set
	 */
	public final void setFilteredPercProperty(StringProperty filteredPercProperty) {
		this.filteredPercProperty = filteredPercProperty;
	}

	/**
	 * @param filteredPerc
	 *            the filtered percentage to set
	 */
	public final void setFilteredPerc(String filteredPerc) {
		this.filteredPercProperty.setValue(filteredPerc);
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

}
