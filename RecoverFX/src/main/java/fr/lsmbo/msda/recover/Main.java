package fr.lsmbo.msda.recover;

//import java.io.File;
import fr.lsmbo.msda.recover.gui.Recover;

public class Main {

	private static final String recover = "Recover";
	private static final String version = "3.0.0 pre-alpha"; // TODO grab the version from the pom file
	private static final String date = "20160411";

	public static void main(String[] args) {
		Session.RECOVER_RELEASE_NAME = recover;
		Session.RECOVER_RELEASE_VERSION = version;
		Session.RECOVER_RELEASE_DATE = date;
		// TODO add a switch for options
		// TODO add usage method
		if (args.length == 0) {
			// open GUI
			System.out.println("open GUI");
			// TODO just for fast testing
//			Session.CURRENT_FILE = new File(Main.class.getClassLoader().getResource("test/X004081MROLM.mgf").getFile());
//			Session.SECOND_FILE = new File(Main.class.getClassLoader().getResource("test/X004094MROLM.mgf").getFile());
			Recover.run();

		} else {
			System.out.println("check CLI");
			// TODO add CLI stuff
		}
	}

	public static String recoverTitle() {
		String title = Session.RECOVER_RELEASE_NAME + " " + Session.RECOVER_RELEASE_VERSION + " ("
				+ Session.RECOVER_RELEASE_DATE + ")";
		return title;
	}

}
