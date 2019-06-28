# Recover-fx

In MS/MS searches, we notice there are a huge number of unassigned spectra. These unassigned MS/MS spectra are they bad quality and non-interpretable?  Recover-Fx has been designed to extract relevant spectra based on user preferences, in order to run other searches focusing on these high quality spectra. 

Recover-Fx is an MS/MS spectra viewer and extractor designed to extract “high quality” spectra from peaklist files. It is used to filter out high quality spectra based on user-adjustable variable as well:

**Spectrum quality filters:**
* The Emergence (E) is a multiplication factor applied to the noise level (computed with an appropriate algorithm for each spectrum) allowing to define "Useful Peaks" with intensities higher than E x noise level.
* The Useful Peaks Number (UPN) is the minimal number of upper defined Useful Peaks contained in a spectrum to be recovered.

**Additional filters:**
* The charge state filter allows removing spectra according to the precursor charge states written in the peaklist.
* Identification results: an excel file containing identification results can be loaded in order to remove spectra the have been previously identified.
* Additional filtering options: Allows removing spectra with no fragment ions higher than the precursor (allows removing singly charged parent ions fragmentation spectra).

Once these filters adjusted, they can be applied in batch mode to multiple files and new peak lists can be exported for further alternative treatments such as:
* De novo searches on high quality spectra only
* Database searches with multiple PTMs
* Database searches in refined databases

Recover-fx allows reducing resource and time losses during data processing caused by the high number of low quality spectra commonly remaining in peak lists. It also allows more refined searches on selected spectra with potential high informative value.

Recover and Recover-fx have been developped by Alexandre Walter, Alexandre Burel, Aymen Romdhani and Benjamin Lombart at LSMBO, IPHC UMR7178, CNRS FRANCE. Recover is available on the MSDA web site: https://msda.unistra.fr/

<h3>Screenshot</h3>

![alt text](https://github.com/LSMBO/recover-fx/blob/master/RecoverFX/src/main/resources/images/recover-fx.png)

