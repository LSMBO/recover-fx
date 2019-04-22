/*
 * 
 */
package fr.lsmbo.msda.recover.gui.util;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.lsmbo.msda.recover.gui.Session;
import javafx.stage.FileChooser;

/**
 * 
 * @author Aromdhani
 * 
 */
public class JavaFxUtils {
	private static final Logger logger = LogManager.getLogger("JavaFxUtils");
	public static String BLUE_HYPERLINK = "-fx-color:#66CCFF;";
	public static String BLUE = "-fx-text-fill: mediumblue;";
	public static String RED = "-fx-text-fill: red;";
	public static String GREY = "-fx-text-fill: grey;";
	public static String ORANGE = "-fx-text-fill: orange;";
	public static String GREEN = "-fx-text-fill: green;";
	public static String ITALIC = "-fx-font-style: italic;";
	public static String TITLE = "-fx-text-fill: #4C6B87;-fx-font-family:serif; -fx-font-size:15px;";
	public static String RED_ITALIC = ITALIC + RED;
	public static String ORANGE_ITALIC = ITALIC + ORANGE;
	public static String GREEN_ITALIC = ITALIC + GREEN;
}