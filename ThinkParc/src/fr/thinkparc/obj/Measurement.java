package fr.thinkparc.obj;

public class Measurement {

	private String id_measurement;
	private String measurement;
	private String symbol;
	
	public String getId_measurement() {
		return id_measurement;
	}
	public void setId_measurement(String id_measurement) {
		this.id_measurement = id_measurement;
	}
	public String getMeasurement() {
		return measurement;
	}
	public void setMeasurement(String measurement) {
		this.measurement = measurement;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	@Override
	public String toString() {
		return getMeasurement();
	}
}
