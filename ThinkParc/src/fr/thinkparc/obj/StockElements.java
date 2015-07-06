package fr.thinkparc.obj;

public class StockElements {
    
    private String siteName;
    private String quantity;
    private String rack;
    private String position;
    private String bay;
     
    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }
     
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
     
    public void setRack(String rack) {
        this.rack = rack;
    }
    
    public void setPosition(String position) {
        this.position = position;
    }
    
    public void setBay(String bay){
        this.bay = bay;
    }
     
    public String getSiteName(){
        return this.siteName;
    }
     
    public String getQuantity(){
        return this.quantity;
    }
 
    public String getRack(){
        return this.rack;
    }    
    
    public String getPosition(){
        return this.position;
    }    
    
    public String getBay(){
        return this.bay;
    }    
}
