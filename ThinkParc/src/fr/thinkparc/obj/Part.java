package fr.thinkparc.obj;

public class Part {

	protected String id_part;
	protected String reference;

	public String getId_part() {
		return id_part;
	}

	public void setId_part(String id_part) {
		this.id_part = id_part;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	@Override
	public String toString() {
		return getReference();
	}
}
