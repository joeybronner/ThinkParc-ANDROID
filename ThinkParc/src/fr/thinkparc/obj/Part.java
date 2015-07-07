package fr.thinkparc.obj;

/* ======================================================================== *
 *																			*
 * @filename:		Part.java												*
 * @description:	Part object to store reference values					*
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
