package fr.lsmbo.msda.recover.model;

import javafx.scene.control.ChoiceBox;

public enum ComparisonTypes {
	EQUALS_TO, NOT_EQUALS_TO, GREATER_THAN, GREATER_OR_EQUAL, LOWER_THAN, LOWER_OR_EQUAL;

	public static ComparisonTypes getComparator(ChoiceBox<String> string) {
		if (string.getValue().equalsIgnoreCase("=")) {
			return ComparisonTypes.EQUALS_TO;
		}

		if (string.getValue().equalsIgnoreCase("!=")) {
			return ComparisonTypes.NOT_EQUALS_TO;
		}
		if (string.getValue().equalsIgnoreCase(">")) {
			return ComparisonTypes.GREATER_THAN;
		}
		if (string.getValue().equalsIgnoreCase(">=")) {
			return ComparisonTypes.GREATER_OR_EQUAL;
		}
		if (string.getValue().equalsIgnoreCase("<")) {
			return ComparisonTypes.LOWER_THAN;
		}
		if (string.getValue().equalsIgnoreCase("<=")) {
			return ComparisonTypes.LOWER_OR_EQUAL;
		}
		return null;
	}

}
