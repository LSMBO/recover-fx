package fr.lsmbo.msda.recover.gui.view.model;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/***
 * This class wraps the UPN filter properties.
 * 
 * @author Aromdhani
 *
 */
public final class RecoverViewUPNProperty {

	private FloatProperty emergence = new SimpleFloatProperty((float) 1.0);
	private IntegerProperty minUPN = new SimpleIntegerProperty(0);
	private IntegerProperty maxUPN = new SimpleIntegerProperty(0);
	private StringProperty mode = new SimpleStringProperty();

	/**
	 * Default constructor
	 */
	public RecoverViewUPNProperty() {
	}

	/**
	 * @param emergence
	 * @param minUPN
	 * @param maxUPN
	 * @param mode
	 */
	public RecoverViewUPNProperty(Float emergence, Integer minUPN, Integer maxUPN, String mode) {
		super();
		this.emergence.set(emergence);
		this.minUPN.set(minUPN);
		this.maxUPN.set(maxUPN);
		this.mode.set(mode);
	}

	/**
	 * @return the emergence
	 */
	public final FloatProperty getEmergence() {
		return emergence;
	}

	/**
	 * @param emergence
	 *            the emergence to set
	 */
	public void setEmergenceProperty(FloatProperty emergence) {
		this.emergence = emergence;
	}

	/**
	 * @param emergence
	 *            the emergence to set
	 */
	public void setEmergence(Float emergence) {
		this.emergence.setValue(emergence);
	}

	/**
	 * @return the minUPN
	 */
	public final IntegerProperty getMinUPN() {
		return minUPN;
	}

	/**
	 * @param minUPN
	 *            the minUPN to set
	 */
	public void setMinUPNProperty(IntegerProperty minUPN) {
		this.minUPN = minUPN;
	}

	/**
	 * @param minUPN
	 *            the minUPN to set
	 */
	public void setMinUPN(Integer minUPN) {
		this.minUPN.setValue(minUPN);
	}

	/**
	 * @return the maxUPN
	 */
	public final IntegerProperty getMaxUPN() {
		return maxUPN;
	}

	/**
	 * @param maxUPN
	 *            the maxUPN to set
	 */
	public void setMaxUPNProperty(IntegerProperty maxUPN) {
		this.maxUPN = maxUPN;
	}

	/**
	 * @param maxUPN
	 *            the maxUPN to set
	 */
	public void setMaxUPN(Integer maxUPN) {
		this.maxUPN.setValue(maxUPN);
	}

	/**
	 * @return the mode
	 */
	public final StringProperty getMode() {
		return mode;
	}

	/**
	 * @param mode
	 *            the mode to set
	 */
	public void setModeProperty(StringProperty mode) {
		this.mode = mode;
	}

	/**
	 * @param mode
	 *            the mode to set
	 */
	public void setMode(String mode) {
		this.mode.setValue(mode);
	}

	@Override
	public String toString() {
		StringBuilder filterProperties = new StringBuilder();
		filterProperties.append("###Parameters used for low intensity threshold filter:").append("\n")
				.append("###Emergence: ").append(getEmergence().get()).append(" ; ").append("minimum UPN: ")
				.append(getMinUPN().get()).append(" ; ").append("maximum UPN: ").append(getMaxUPN().get()).append(" ; ")
				.append("Baseline calculate with: ").append(getMode().get()).append("\n");
		return filterProperties.toString();

	}
}
