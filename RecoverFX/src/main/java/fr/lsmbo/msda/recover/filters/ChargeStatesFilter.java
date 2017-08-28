package fr.lsmbo.msda.recover.filters;

import fr.lsmbo.msda.recover.model.Spectrum;

/**
 * This class provide information about charge states filter (getter and setter). filter spectrum according to his specific charge and charges we want to keep.
 * Parameters used in this class are booleans. Spectrum will be kept according to value of these booleans.
 * 
 * @author BL
 * 
 */
public class ChargeStatesFilter implements BasicFilter {

	private Boolean keepCharge1 = true;
	private Boolean keepCharge2 = true;
	private Boolean keepCharge3 = true;
	private Boolean keepCharge4 = true;
	private Boolean keepCharge5 = true;
	private Boolean keepChargeAbove5 = true;
	private Boolean keepUnknownCharge = true;


	/**
	 * set the different parameters (Boolean) to this class
	 * 
	 * @param z1
	 * 	value of the boolean to keep or not charge +1
	 * @param z2
	 * value of the boolean to keep or not charge +2
	 * @param z3
	 * value of the boolean to keep or not charge +3
	 * @param z4
	 * value of the boolean to keep or not charge +4
	 * @param z5
	 * value of the boolean to keep or not charge +5
	 * @param zOver5
	 * value of the boolean to keep or not charge over +5
	 * @param zUnknown
	 * value of the boolean to keep or not unknown charge
	 */
	public void setParameters(Boolean z1, Boolean z2, Boolean z3, Boolean z4, Boolean z5, Boolean zOver5,
			Boolean zUnknown) {
		keepCharge1 = z1;
		keepCharge2 = z2;
		keepCharge3 = z3;
		keepCharge4 = z4;
		keepCharge5 = z5;
		keepChargeAbove5 = zOver5;
		keepUnknownCharge = zUnknown;
	}

	// test if the spectra is valid or not
	@Override
	/**
	 * Determine if the spectrum are keep or not by comparing this charge with charges we want to keep
	 * @param spectrum
	 *		spectrum to apply charge states filter
	 * @return true (spectrum are kept) or false (spectrum are rejected)
	 * @see Spectrum
	 */
	public Boolean isValid(Spectrum spectrum) {
		if (spectrum.getCharge() == -1 && !keepUnknownCharge)
			return false;
		if (spectrum.getCharge() == 1 && !keepCharge1)
			return false;
		if (spectrum.getCharge() == 2 && !keepCharge2)
			return false;
		if (spectrum.getCharge() == 3 && !keepCharge3)
			return false;
		if (spectrum.getCharge() == 4 && !keepCharge4)
			return false;
		if (spectrum.getCharge() == 5 && !keepCharge5)
			return false;
		if (spectrum.getCharge() >= 6 && !keepChargeAbove5)
			return false;
		return true;
	}

	// returns description
	@Override
	public String getFullDescription() {
		return "###Parameters used for Charge States Filter : \n" + "###keep charge 1+ : " + getKeepCharge1() + " ; "
				+ "keep charge 2+ : " + getKeepCharge2() + " ; " + "keep charge 3+ : " + getKeepCharge3() + " ; "
				+ "keep charge 4+ : " + getKeepCharge4() + " ; " + "keep charge 5+ : " + getKeepCharge5() + " ; "
				+ "keep charge above 5+ : " + getKeepChargeAbove5() + " ; " + "keep unkwnon charge : "
				+ getKeepUnknownCharge() + "\n";
	}

	/**
	 * @return states of the boolean for charge +1
	 */
	public Boolean getKeepCharge1() {
		return keepCharge1;
	}
	/**
	 * @return states of the boolean for charge +2
	 */
	public Boolean getKeepCharge2() {
		return keepCharge2;
	}
	/**
	 * @return states of the boolean for charge +3
	 */
	public Boolean getKeepCharge3() {
		return keepCharge3;
	}
	/**
	 * @return states of the boolean for charge +4
	 */
	public Boolean getKeepCharge4() {
		return keepCharge4;
	}
	/**
	 * @return states of the boolean for charge +5
	 */
	public Boolean getKeepCharge5() {
		return keepCharge5;
	}
	/**
	 * @return states of the boolean for charge over +5
	 */
	public Boolean getKeepChargeAbove5() {
		return keepChargeAbove5;
	}
	/**
	 * @return states of the boolean for unkwnown charge
	 */
	public Boolean getKeepUnknownCharge() {
		return keepUnknownCharge;
	}


}
