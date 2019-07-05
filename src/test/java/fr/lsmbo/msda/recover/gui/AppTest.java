package fr.lsmbo.msda.recover.gui;

import java.io.File;

import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public AppTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static TestSuite suite() {
		return new TestSuite(AppTest.class);
	}

	/**
	 * Test Files
	 */
	public void testFile() {
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			File refSpectraFile = new File(classLoader.getResource("E_040566.mgf").getFile());
			assertNotNull("The peaklist file", refSpectraFile.getName());
			File testSpectraFile = new File(classLoader.getResource("E_040676.mgf").getFile());
			assertNotNull("The peaklist file", testSpectraFile.getName());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Test the loaded spectra
	 */
	public void testSpectraLoder() {
		// Load Spectra
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			File firstSpectraFile = new File(classLoader.getResource("E_040566.mgf").getFile());
			assertNotNull("The peaklist file", firstSpectraFile.getName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
