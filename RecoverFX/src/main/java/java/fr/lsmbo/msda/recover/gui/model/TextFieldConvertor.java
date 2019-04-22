/*
 * 
 */
package fr.lsmbo.msda.recover.gui.model;

import javafx.scene.control.TextField;

public class TextFieldConvertor {

	public static Integer changeTextFieldToInteger(TextField string) {
		String valueOfTextField = string.getText();
		int integerOfTextField = Integer.parseInt(valueOfTextField);
		return integerOfTextField;
	}

	public static Float changeTextFieldToFloat(TextField string) {
		String valueOfTextField = string.getText();
		float floatOfTextField = Float.parseFloat(valueOfTextField);
		return floatOfTextField;
	}
}
