# RecoverFX

RecoverFX is the future version of Recover,it is the MS/MS spectra viewer/extractor designed to extract "high quality" spectra from peaklist files.

Recover has been developed to filter out high quality spectra from peaklists based on the following user-adjustable variables:

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

RecoverFx allows reducing resource and time losses during data processing caused by the high number of low quality spectra commonly remaining in peak lists. It also allows more refined searches on selected spectra with potential high informative value.

Recover and RecoverFX have been developped by Alexandre Walter, Alexandre Burel, Aymen Romdhani and Benjamin Lombart at LSMBO, IPHC UMR7178, CNRS FRANCE. Recover is available on the MSDA web site: https://msda.unistra.fr/

