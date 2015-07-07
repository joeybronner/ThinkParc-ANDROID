package fr.thinkparc.obj;

public class Site {

    private String id_site;
    private String name;
    
	public String getId_site() {
		return id_site;
	}
	public void setId_site(String id_site) {
		this.id_site = id_site;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return getName();
	}
	
}
