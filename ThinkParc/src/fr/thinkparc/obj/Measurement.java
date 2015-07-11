package fr.thinkparc.obj;

/* ======================================================================== *
 *																			*
 * @filename:		Measurement.java										*
 * @description:	Measurement object.										*
 *																			*
 * @author(s): 		Joey BRONNER											*
 * @contact(s):		joeybronner@gmail.com									*
 * @creation: 		01/07/2015												*
 * @remarks:		-														*
 * 																			*
 * @rights:			Think-Parc Software ©, 2015.							*
 *																			*
 *																			*
 * Date       | Developer      | Changes description						* 
 * ------------------------------------------------------------------------ *
 * 01/07/2015 | J.BRONNER      | Creation									*
 * ------------------------------------------------------------------------ *
 * JJ/MM/AAAA | ...			   | ...			 							*
 * =========================================================================*/

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
