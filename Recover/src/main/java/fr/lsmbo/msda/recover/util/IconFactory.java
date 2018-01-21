package fr.lsmbo.msda.recover.util;

import java.util.HashMap;

import javafx.scene.image.Image;

/**
 * 
 * @author aromdhani
 *
 */
public class IconFactory {
	private static HashMap<ICON, Image> images = new HashMap<ICON, Image>();

	public enum ICON {
		APPLYFILTER,CONSOLE, EXPORT, EXPORT_DATA, EDIT, HELP, INFORMATION, LOAD, USEAXIS, CROSS, TICK, SAVE, SETTINGS, RECOVER, GETSPECTRUM, TRASH, WARNING, RESET, FLAG, EXIT;
	}

	/**
	 * 
	 * @param icon
	 * @return Image
	 */
	public static Image getImage(ICON icon) {
		if (!images.containsKey(icon)) {
			switch (icon) {
			case APPLYFILTER:
				images.put(icon, new Image(IconFactory.class.getResourceAsStream("/images/applyfilter.png")));
				break;
			case EXPORT:
				images.put(icon, new Image(IconFactory.class.getResourceAsStream("/images/export.png")));
				break;
			case EDIT:
				images.put(icon, new Image(IconFactory.class.getResourceAsStream("/images/editparsingrules.png")));
				break;
			case HELP:
				images.put(icon, new Image(IconFactory.class.getResourceAsStream("/images/help.png")));
				break;
			case LOAD:
				images.put(icon, new Image(IconFactory.class.getResourceAsStream("/images/load.png")));
				break;
			case CROSS:
				images.put(icon, new Image(IconFactory.class.getResourceAsStream("/images/cross.png")));
				break;
			case INFORMATION:
				images.put(icon, new Image(IconFactory.class.getResourceAsStream("/images/information.png")));
				break;
			case TICK:
				images.put(icon, new Image(IconFactory.class.getResourceAsStream("/images/tick.png")));
				break;
			case SAVE:
				images.put(icon, new Image(IconFactory.class.getResourceAsStream("/images/save.png")));
				break;
			case SETTINGS:
				images.put(icon, new Image(IconFactory.class.getResourceAsStream("/images/settings.png")));
				break;
			case TRASH:
				images.put(icon, new Image(IconFactory.class.getResourceAsStream("/images/trash.png")));
				break;
			case WARNING:
				images.put(icon, new Image(IconFactory.class.getResourceAsStream("/images/warning.png")));
				break;
			case EXPORT_DATA:
				images.put(icon, new Image(IconFactory.class.getResourceAsStream("/images/exportinbatch.png")));
				break;
			case RESET:
				images.put(icon, new Image(IconFactory.class.getResourceAsStream("/images/arrowcircle.png")));
				break;
			case FLAG:
				images.put(icon, new Image(IconFactory.class.getResourceAsStream("/images/flag.png")));
				break;
			case USEAXIS:
				images.put(icon, new Image(IconFactory.class.getResourceAsStream("/images/useaxis.png")));
				break;
			case GETSPECTRUM:
				images.put(icon, new Image(IconFactory.class.getResourceAsStream("/images/getspectrum.png")));
				break;
			case RECOVER:
				images.put(icon, new Image(IconFactory.class.getResourceAsStream("/images/recover.png")));
				break;
			case EXIT:
				images.put(icon, new Image(IconFactory.class.getResourceAsStream("/images/exit.png")));
				break;
			case CONSOLE:
				images.put(icon, new Image(IconFactory.class.getResourceAsStream("/images/console.png")));
				break;
				
			default:
				break;
			}
		}
		return images.get(icon);
	}
}
