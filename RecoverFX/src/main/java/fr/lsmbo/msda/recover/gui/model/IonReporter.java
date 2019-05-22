/*
 * 
 */
package fr.lsmbo.msda.recover.gui.model;

// TODO maybe change the name because it seems protected by Thermo (or add "tm" ?)
public class IonReporter {

	private String name;
	private Float moz;
	private Float tolerance;

	public IonReporter(String name, Float moz, Float tolerance) {
		super();
		this.name = name;
		this.moz = moz;
		this.tolerance = tolerance;
	}

	public Float getMoz() {
		return moz;
	}

	public String getName() {
		return name;
	}

	public Float getTolerance() {
		return tolerance;
	}

	public void setMoz(Float moz) {
		this.moz = moz;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTolerance(Float tolerance) {
		this.tolerance = tolerance;
	}

	@Override
	public String toString() {
		return "Ion reporter :" + getName() + "; m/z :" + getMoz() + "; tolerance :" + getTolerance();
	}

}
