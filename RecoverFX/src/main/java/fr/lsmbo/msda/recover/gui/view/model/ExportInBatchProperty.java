package fr.lsmbo.msda.recover.gui.view.model;

import java.io.File;
import java.util.Map;

import fr.lsmbo.msda.recover.gui.model.AppliedFilters;
import fr.lsmbo.msda.recover.gui.model.settings.SpectrumTitleSelector;

/***
 * This class wraps export in batch properties .
 * 
 * @author Aromdhani
 *
 */
public final class ExportInBatchProperty {
	private AppliedFilters appliedFilters;
	private File outputDirectory;
	private Map<File, File> identifiedSpectraByPeakList;
	private File jsonFile;
	private SpectrumTitleSelector spectrumTitleRange;

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
	 *            the JSON file contains a list of filters.
	 * @param spectrumTitleRange
	 *            the template of titles selection from an excel file.
	 */
	public ExportInBatchProperty(AppliedFilters appliedFilters, File outputDirectory,
			Map<File, File> identifiedSpectraByPeakList, File jsonFile, SpectrumTitleSelector spectrumTitleRange) {
		super();
		this.appliedFilters = appliedFilters;
		this.outputDirectory = outputDirectory;
		this.identifiedSpectraByPeakList = identifiedSpectraByPeakList;
		this.jsonFile = jsonFile;
		this.spectrumTitleRange = spectrumTitleRange;
	}

	/**
	 * @return the applied filters
	 */
	public final AppliedFilters getAppliedFilters() {
		return appliedFilters;
	}

	/**
	 * @return the identified spectra by peakList
	 */
	public final Map<File, File> getIdentifiedSpectraByPeakList() {
		return identifiedSpectraByPeakList;
	}

	/**
	 * @return the json file
	 */
	public final File getJsonFile() {
		return jsonFile;
	}

	/**
	 * @return the output directory
	 */
	public final File getOutputDirectory() {
		return outputDirectory;
	}

	/**
	 * @return the spectrum title range
	 */
	public final SpectrumTitleSelector getSpectrumTitleSelection() {
		return spectrumTitleRange;
	}

	/**
	 * @param appliedFilters
	 *            the applied filters to set
	 */
	public final void setAppliedFilters(AppliedFilters appliedFilters) {
		this.appliedFilters = appliedFilters;
	}

	/**
	 * @param identifiedSpectraByPeakList
	 *            the identified spectra by peakList to set
	 */
	public final void setIdentifiedSpectraByPeakList(Map<File, File> identifiedSpectraByPeakList) {
		this.identifiedSpectraByPeakList = identifiedSpectraByPeakList;
	}

	/**
	 * @param jsonFile
	 *            the jsonFile to set
	 */
	public final void setJsonFile(File jsonFile) {
		this.jsonFile = jsonFile;
	}

	/**
	 * @param outputDirectory
	 *            the output directory to set
	 */
	public final void setOutputDirectory(File outputDirectory) {
		this.outputDirectory = outputDirectory;
	}

	/**
	 * @param spectrumTitleRange
	 *            the spectrum title range to set
	 */
	public final void setSpectrumTitleRange(SpectrumTitleSelector spectrumTitleRange) {
		this.spectrumTitleRange = spectrumTitleRange;
	}

}
