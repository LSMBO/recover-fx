package fr.lsmbo.msda.recover;

//import java.io.File;
import fr.lsmbo.msda.recover.gui.Recover;

public class Main {

	public static void main(String[] args) {
		Config.initialize();
		// TODO add a switch for options
		// TODO add usage method (use jcommander)
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
