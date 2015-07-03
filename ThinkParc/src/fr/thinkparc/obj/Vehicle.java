package fr.thinkparc.obj;

public class Vehicle {

	protected String date_add;
	protected String date_entryservice;
	protected String id_energy;
	protected String id_model;
	protected String id_kind;
	protected String id_category;
	protected String equipments;
	protected String id_state;
	protected String id_site;
	protected String id_currency;
	protected String commentary;
	protected String kind;
	protected String id_brand;
	protected String brand;
	protected String state;
	protected String category;
	protected String energy;
	protected String model;
	protected String site;
	protected String currency;
	protected String symbol;
	protected String id_vehicle;
	protected String nr_plate;
	protected String nr_serial;
	protected String mileage;
	protected String buyingprice;
	protected String date_buy;

	public Vehicle() {

	}

	@Override
	public String toString() {
		return getNr_plate();
	}

	public String getId_vehicle() {
		return this.id_vehicle;
	}


	public void setId_vehicle(String id_vehicle) {
		this.id_vehicle = id_vehicle;
	}


	public String getNr_plate() {
		return this.nr_plate;
	}


	public void setNr_plate(String nr_plate) {
		this.nr_plate = nr_plate;
	}


	public String getNr_serial() {
		return nr_serial;
	}


	public void setNr_serial(String nr_serial) {
		this.nr_serial = nr_serial;
	}


	public String getMileage() {
		return mileage;
	}


	public void setMileage(String mileage) {
		this.mileage = mileage;
	}


	public String getBuyingprice() {
		return buyingprice;
	}


	public void setBuyingprice(String buyingprice) {
		this.buyingprice = buyingprice;
	}


	public String getDate_buy() {
		return date_buy;
	}


	public void setDate_buy(String date_buy) {
		this.date_buy = date_buy;
	}


	public String getDate_add() {
		return date_add;
	}


	public void setDate_add(String date_add) {
		this.date_add = date_add;
	}


	public String getDate_entryservice() {
		return date_entryservice;
	}


	public void setDate_entryservice(String date_entryservice) {
		this.date_entryservice = date_entryservice;
	}


	public String getId_energy() {
		return id_energy;
	}


	public void setId_energy(String id_energy) {
		this.id_energy = id_energy;
	}


	public String getId_model() {
		return id_model;
	}


	public void setId_model(String id_model) {
		this.id_model = id_model;
	}


	public String getId_kind() {
		return id_kind;
	}


	public void setId_kind(String id_kind) {
		this.id_kind = id_kind;
	}


	public String getId_category() {
		return id_category;
	}


	public void setId_category(String id_category) {
		this.id_category = id_category;
	}


	public String getEquipments() {
		return equipments;
	}


	public void setEquipments(String equipments) {
		this.equipments = equipments;
	}


	public String getId_state() {
		return id_state;
	}


	public void setId_state(String id_state) {
		this.id_state = id_state;
	}


	public String getId_site() {
		return id_site;
	}


	public void setId_site(String id_site) {
		this.id_site = id_site;
	}


	public String getId_currency() {
		return id_currency;
	}


	public void setId_currency(String id_currency) {
		this.id_currency = id_currency;
	}


	public String getCommentary() {
		return commentary;
	}


	public void setCommentary(String commentary) {
		this.commentary = commentary;
	}


	public String getKind() {
		return kind;
	}


	public void setKind(String kind) {
		this.kind = kind;
	}


	public String getId_brand() {
		return id_brand;
	}


	public void setId_brand(String id_brand) {
		this.id_brand = id_brand;
	}


	public String getBrand() {
		return brand;
	}


	public void setBrand(String brand) {
		this.brand = brand;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public String getEnergy() {
		return energy;
	}


	public void setEnergy(String energy) {
		this.energy = energy;
	}


	public String getModel() {
		return model;
	}


	public void setModel(String model) {
		this.model = model;
	}


	public String getSite() {
		return site;
	}


	public void setSite(String site) {
		this.site = site;
	}


	public String getCurrency() {
		return currency;
	}


	public void setCurrency(String currency) {
		this.currency = currency;
	}


	public String getSymbol() {
		return symbol;
	}


	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

}
