package fr.thinkparc.obj;

/* ======================================================================== *
 *																			*
 * @filename:		Document.java											*
 * @description:	Document object.										*
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

public class Document {

	private String id_file;
    private String path;
    private String date_upload;
    private String id_element;
    private String filetype;
    
	public String getId_file() {
		return id_file;
	}
	public void setId_file(String id_file) {
		this.id_file = id_file;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getDate_upload() {
		return date_upload;
	}
	public void setDate_upload(String date_upload) {
		this.date_upload = date_upload;
	}
	public String getId_element() {
		return id_element;
	}
	public void setId_element(String id_element) {
		this.id_element = id_element;
	}
	public String getFiletype() {
		return filetype;
	}
	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}
    
}
