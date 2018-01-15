package fr.lsmbo.msda.recover.util;

import java.util.HashMap;

import javafx.scene.image.Image;

/**
 * 
 * @author aromdhani
 *
 */
public class IconRessource {
	private static HashMap<ICON, Image> images = new HashMap<ICON, Image>();

	public enum ICON {
		EXPORT, EXPORT_DATA, HELP, INFORMATION, LOAD, CROSS, TICK, SAVE, SETTINGS, TRASH, WARNING;
	}
	/**
	 * 
	 * @param icon
	 * @return Image
	 */
	public static Image getImage(ICON icon) {
		if (!images.containsKey(icon)) {
			switch (icon) {
			case EXPORT:
				images.put(icon, new Image(IconRessource.class.getResourceAsStream("/images/export.png")));
				break;
			case HELP:
				images.put(icon, new Image(IconRessource.class.getResourceAsStream("/images/help.png")));
				break;
			case LOAD:
				images.put(icon, new Image(IconRessource.class.getResourceAsStream("/images/load.png")));
				break;
			case CROSS:
				images.put(icon, new Image(IconRessource.class.getResourceAsStream("/images/cross.png")));
				break;
			case INFORMATION:
				images.put(icon, new Image(IconRessource.class.getResourceAsStream("/images/information.png")));
				break;
			case TICK:
				images.put(icon, new Image(IconRessource.class.getResourceAsStream("/images/tick.png")));
				break;
			case SAVE:
				images.put(icon, new Image(IconRessource.class.getResourceAsStream("/images/save.png")));
				break;
			case SETTINGS:
				images.put(icon, new Image(IconRessource.class.getResourceAsStream("/images/settings.png")));
				break;
			case TRASH:
				images.put(icon, new Image(IconRessource.class.getResourceAsStream("/images/trash.png")));
				break;
			case WARNING:
				images.put(icon, new Image(IconRessource.class.getResourceAsStream("/images/warning.png")));
				break;
			case EXPORT_DATA:
				images.put(icon, new Image(IconRessource.class.getResourceAsStream("/images/exportData.png")));
				break;
				
			default:
				break;
			}
		}
		return images.get(icon);
	}
}
