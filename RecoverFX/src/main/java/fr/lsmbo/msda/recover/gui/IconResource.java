package fr.lsmbo.msda.recover.gui;

import java.util.HashMap;
import javafx.scene.image.Image;

/**
 * Add an icon resource
 * 
 * @author Aromdhani
 *
 */
@SuppressWarnings("restriction")
public class IconResource {
	private static HashMap<ICON, Image> images = new HashMap<ICON, Image>();

	/**
	 * Enum type that indicates the name of the icon.
	 */
	public enum ICON {
		APPLYFILTER, CONSOLE, EXPORT, EXPORT_DATA, EDIT, HELP, INFORMATION, LOAD, CROSS, TICK, SAVE, RECOVER,
		IDENTIFIEDSPECTRA, TRASH, WARNING, RESET, FLAG, EXIT, RELOAD, FILTER;
	}

	/**
	 * Return an image
	 * 
	 * @param icon the icon to load.
	 * @return Image
	 */
	public static Image getImage(ICON icon) {
		if (!images.containsKey(icon)) {
			switch (icon) {
			case APPLYFILTER:
				images.put(icon, new Image(IconResource.class.getResourceAsStream("/images/apply-filter.png")));
				break;
			case EXPORT:
				images.put(icon, new Image(IconResource.class.getResourceAsStream("/images/export.png")));
				break;
			case EDIT:
				images.put(icon, new Image(IconResource.class.getResourceAsStream("/images/edit-parsing-rules.png")));
				break;
			case HELP:
				images.put(icon, new Image(IconResource.class.getResourceAsStream("/images/help.png")));
				break;
			case LOAD:
				images.put(icon, new Image(IconResource.class.getResourceAsStream("/images/load.png")));
				break;
			case CROSS:
				images.put(icon, new Image(IconResource.class.getResourceAsStream("/images/cross.png")));
				break;
			case INFORMATION:
				images.put(icon, new Image(IconResource.class.getResourceAsStream("/images/information.png")));
				break;
			case TICK:
				images.put(icon, new Image(IconResource.class.getResourceAsStream("/images/tick.png")));
				break;
			case SAVE:
				images.put(icon, new Image(IconResource.class.getResourceAsStream("/images/save.png")));
				break;
			case WARNING:
				images.put(icon, new Image(IconResource.class.getResourceAsStream("/images/warning.png")));
				break;
			case EXPORT_DATA:
				images.put(icon, new Image(IconResource.class.getResourceAsStream("/images/export-batch.png")));
				break;
			case RESET:
				images.put(icon, new Image(IconResource.class.getResourceAsStream("/images/arrowcircle.png")));
				break;
			case FLAG:
				images.put(icon, new Image(IconResource.class.getResourceAsStream("/images/flag.png")));
				break;
			case IDENTIFIEDSPECTRA:
				images.put(icon, new Image(IconResource.class.getResourceAsStream("/images/identified-spectra.png")));
				break;
			case RECOVER:
				images.put(icon, new Image(IconResource.class.getResourceAsStream("/images/recover.png")));
				break;
			case EXIT:
				images.put(icon, new Image(IconResource.class.getResourceAsStream("/images/exit.png")));
				break;
			case CONSOLE:
				images.put(icon, new Image(IconResource.class.getResourceAsStream("/images/console.png")));
				break;
			case RELOAD:
				images.put(icon, new Image(IconResource.class.getResourceAsStream("/images/reload.png")));
				break;
			case FILTER:
				images.put(icon, new Image(IconResource.class.getResourceAsStream("/images/view-actual-filters.png")));
				break;
			default:
				break;
			}
		}
		return images.get(icon);
	}
}