package fr.thinkparc.obj;

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
