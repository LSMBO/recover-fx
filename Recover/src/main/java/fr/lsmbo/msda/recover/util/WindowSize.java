package fr.lsmbo.msda.recover.util;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

/**
 * 
 * @author aromdhani
 *
 */

public class WindowSize {
	private static Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
	public static final double prefWidth = (primaryScreenBounds.getWidth() * 0.75);
	public static final double prefHeight = (primaryScreenBounds.getHeight() * 0.90);
	public static final double minWidth = (primaryScreenBounds.getWidth() * 0.25);
	public static final double minHeight = (primaryScreenBounds.getHeight() * 0.25);
}
