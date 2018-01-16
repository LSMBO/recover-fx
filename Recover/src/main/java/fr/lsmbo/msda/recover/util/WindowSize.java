package fr.lsmbo.msda.recover.util;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

/**
 * 
 * @author aromdhani
 *
 */

public class WindowSize {

	// get window size
	private static Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

	// main panel prefered size
	public static final double prefWidth = (primaryScreenBounds.getWidth() * 0.75);
	public static final double prefHeight = (primaryScreenBounds.getHeight() * 0.90);
	public static final double minWidth = (primaryScreenBounds.getWidth() * 0.25);
	public static final double minHeight = (primaryScreenBounds.getHeight() * 0.25);
	// main panel prefered size
	public static final Double mainPanePreferHeight = prefHeight - 30;
	public static final Double mainPanePreferWidth = prefHeight - 20;
	// popup prefered size
	public static final double popupPrefHeight = (prefHeight - 200);
	public static final double popupPrefWidth = (prefWidth - 100);
	// popup minsize
	public static final double popupMinHeight = (prefHeight / 2);
	public static final double popupMinWidth = (prefWidth / 2);
	// buttons size
	public static final Double BUTTON_WITDH = 100D;

}
