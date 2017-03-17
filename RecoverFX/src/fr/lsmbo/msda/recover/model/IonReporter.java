package fr.lsmbo.msda.recover.model;

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

	public String toString() {
		return "Ion reporter : " + getName() + " ; m/z : " + getMoz() + " ; tolerance : " + getTolerance();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getMoz() {
		return moz;
	}

	public void setMoz(Float moz) {
		this.moz = moz;
	}

	public Float getTolerance() {
		return tolerance;
	}

	public void setTolerance(Float tolerance) {
		this.tolerance = tolerance;
	}

}
