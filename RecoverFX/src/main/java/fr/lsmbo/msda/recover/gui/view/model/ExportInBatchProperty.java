package fr.lsmbo.msda.recover.gui.view.model;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import fr.lsmbo.msda.recover.gui.Session;
import fr.lsmbo.msda.recover.gui.model.Spectrum;
import fr.lsmbo.msda.recover.gui.model.settings.SpectrumTitleRange;
import fr.lsmbo.msda.recover.gui.view.dialog.ExportInBatchDialog.AppliedFilters;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/***
 * This class wraps export in batch properties view properties.
 * 
 * @author Aromdhani
 *
 */
public final class ExportInBatchProperty {
	AppliedFilters appliedFilters = null;
	private File outputDirectory = null;
	private Map<File, File> identifiedSpectraByPeakList = null;
	private File jsonFile = null;
	private SpectrumTitleRange spectrumTitleRange = null;

	/**
	 * @return the appliedFilters
	 */
	public final AppliedFilters getAppliedFilters() {
		return appliedFilters;
	}

	/**
	 * @param appliedFilters
	 *            the appliedFilters to set
	 */
	public final void setAppliedFilters(AppliedFilters appliedFilters) {
		this.appliedFilters = appliedFilters;
	}

	/**
	 * @return the outputDirectory
	 */
	public final File getOutputDirectory() {
		return outputDirectory;
	}

	/**
	 * @param outputDirectory
	 *            the outputDirectory to set
	 */
	public final void setOutputDirectory(File outputDirectory) {
		this.outputDirectory = outputDirectory;
	}

	/**
	 * @return the identifiedSpectraByPeakList
	 */
	public final Map<File, File> getIdentifiedSpectraByPeakList() {
		return identifiedSpectraByPeakList;
	}

	/**
	 * @param identifiedSpectraByPeakList
	 *            the identifiedSpectraByPeakList to set
	 */
	public final void setIdentifiedSpectraByPeakList(Map<File, File> identifiedSpectraByPeakList) {
		this.identifiedSpectraByPeakList = identifiedSpectraByPeakList;
	}

	/**
	 * @return the jsonFile
	 */
	public final File getJsonFile() {
		return jsonFile;
	}

	/**
	 * @param jsonFile
	 *            the jsonFile to set
	 */
	public final void setJsonFile(File jsonFile) {
		this.jsonFile = jsonFile;
	}

	/**
	 * @return the spectrumTitleRange
	 */
	public final SpectrumTitleRange getSpectrumTitleRange() {
		return spectrumTitleRange;
	}

	/**
	 * @param spectrumTitleRange
	 *            the spectrumTitleRange to set
	 */
	public final void setSpectrumTitleRange(SpectrumTitleRange spectrumTitleRange) {
		this.spectrumTitleRange = spectrumTitleRange;
	}

	/**
	 * Default
	 */
	public ExportInBatchProperty() {
	}

	/**
	 * @param appliedFilters
	 *            the applied filters
	 * @param outputDirectory
	 *            the output directory
	 * @param identifiedSpectraByPeakList
	 *            the map identified Spectra by peakList
	 * @param jsonFile
	 *            the JSON file contains filters
	 * @param spectrumTitleRange
	 *            the template of titles selection from excel file.
	 */
	public ExportInBatchProperty(AppliedFilters appliedFilters, File outputDirectory,
			Map<File, File> identifiedSpectraByPeakList, File jsonFile, SpectrumTitleRange spectrumTitleRange) {
		super();
		this.appliedFilters = appliedFilters;
		this.outputDirectory = outputDirectory;
		this.identifiedSpectraByPeakList = identifiedSpectraByPeakList;
		this.jsonFile = jsonFile;
		this.spectrumTitleRange = spectrumTitleRange;
	}

}
