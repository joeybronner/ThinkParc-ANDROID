package fr.thinkparc.obj;

/* ======================================================================== *
 *																			*
 * @filename:		Maintenance.java										*
 * @description:	Maintenance object to define a maintenance vehicle		*
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

public class Maintenance {

	protected String id_vehicle;
	protected String nr_plate;

	@Override
	public String toString() {
		return getNr_plate();
	}

	public String getId_vehicle() {
		return id_vehicle;
	}
	public void setId_vehicle(String id_vehicle) {
		this.id_vehicle = id_vehicle;
	}
	public String getNr_plate() {
		return nr_plate;
	}
	public void setNr_plate(String nr_plate) {
		this.nr_plate = nr_plate;
	}
}
