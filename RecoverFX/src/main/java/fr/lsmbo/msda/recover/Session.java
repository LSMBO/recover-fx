package fr.lsmbo.msda.recover;

import java.io.File;

public class Session {

	// static list of session values
	public static String FILE_HEADER = "";
	public static String RECOVER_RELEASE_NAME = "";
	public static String RECOVER_RELEASE_DESCRIPTION = "";
	public static String RECOVER_RELEASE_VERSION = "";
	public static String RECOVER_RELEASE_DATE = "";
	// public static Boolean DATABASE_LOADED = false;
	public static File CURRENT_FILE = null;
	public static File SECOND_FILE = null;
	public static File DIRECTORY_FILTER_FILE = null;
	public static String CURRENT_REGEX_RT = "title.regex.data-analysis.rt";
	public static Boolean USE_FIXED_AXIS = false;
	public static Float HIGHEST_FRAGMENT_MZ = 0F;
	public static Float HIGHEST_FRAGMENT_INTENSITY = 0F;

	public static Float CALCULATED_NOISE_VALUE = 150F; // median fragment
														// intensity (or
														// average)
	public static Float LOW_INTENSITY_THRESHOLD = 450F; // E * median fragment
														// intensity (or
														// average)
	public static Float HIGH_INTENSITY_THRESHOLD = 2000F; //
	public static Float TOP_LINE = 2500F;
	// public static Float FILTER_EMERGENCE = 0F;
	// public static Integer FILTER_UPN_MIN = 0;
	// public static Integer FILTER_UPN_MAX = 0;
	// public static String FILTER_NOISE_ALGORITHM = "median"; // TODO change
	// this into a enum or something like that
}
