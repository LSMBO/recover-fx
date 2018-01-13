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
		HELP, LOAD, CROSS;
	}
	/**
	 * 
	 * @param icon
	 * @return Image 
	 */
	public static Image getImage(ICON icon) {
		if (!images.containsKey(icon)) {
			switch (icon) {
			case HELP:
				images.put(icon, new Image(IconRessource.class.getResourceAsStream("/images/help.png")));
				break;
			case LOAD:
				images.put(icon, new Image(IconRessource.class.getResourceAsStream("/images/load.png")));
				break;
			case CROSS:
				images.put(icon, new Image(IconRessource.class.getResourceAsStream("/images/cross.png")));
				break;
			default:
				break;
			}
		}
		return images.get(icon);
	}
}
