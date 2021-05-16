package business.entities;

public class Carport {
    //The reason for Integer at some of them, is to make them possible to be null for prettier print on site.
private int id;
private String carportMaterial;
private Integer carportMaterialId;
private int carportWidth;
private int carportHeight;
private int carportLength;
private String shedMaterial;
private Integer shedMaterialId;
private Integer shedWidth;
private Integer shedLength;
private Integer roofTilt;
private String roofMaterial;
private Integer roofMaterialId;
private RoofType roofType;

    public Carport(String carportMaterial, int carportWidth, int carportHeight, int carportLength, RoofType roofType) {
        this.carportMaterial = carportMaterial;
        this.carportWidth = carportWidth;
        this.carportHeight = carportHeight;
        this.carportLength = carportLength;
        this.roofType = roofType;
    }

    public Integer getCarportMaterialId() {
        return carportMaterialId;
    }

    public Integer getShedMaterialId() {
        return shedMaterialId;
    }

    public Integer getRoofMaterialId() {
        return roofMaterialId;
    }

    public void setCarportMaterialId(Integer carportMaterialId) {
        this.carportMaterialId = carportMaterialId;
    }

    public void setShedMaterialId(Integer shedMaterialId) {
        this.shedMaterialId = shedMaterialId;
    }

    public void setRoofMaterialId(Integer roofMaterialId) {
        this.roofMaterialId = roofMaterialId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getCarportMaterial() {
        return carportMaterial;
    }

    public int getCarportWidth() {
        return carportWidth;
    }

    public int getCarportHeight() {
        return carportHeight;
    }

    public int getCarportLength() {
        return carportLength;
    }

    public void setShedMaterial(String shedMaterial) {
        this.shedMaterial = shedMaterial;
    }

    public String getShedMaterial() {
        return shedMaterial;
    }

    public Integer getShedWidth() {
        return shedWidth;
    }

    public Integer getShedLength() {
        return shedLength;
    }

    public Integer getRoofTilt() {
        return roofTilt;
    }

    public void setShedWidth(Integer shedWidth) {
        this.shedWidth = shedWidth;
    }

    public void setShedLength(Integer shedLength) {
        this.shedLength = shedLength;
    }

    public void setRoofTilt(Integer roofTilt) {
        this.roofTilt = roofTilt;
    }

    public String getRoofMaterial() {
        return roofMaterial;
    }

    public String getRoofType() {
        return roofType.toString();
    }


    public void setRoofMaterial(String roofMaterial) {
        this.roofMaterial = roofMaterial;
    }

    @Override
    public String toString() {
        return "Carport{" +
                "id=" + id +
                ", carportBeklædning='" + carportMaterial + '\'' +
                ", CarportBredde=" + carportWidth +
                ", CarportHøjde=" + carportHeight +
                ", CarportLængde=" + carportLength +
                ", RedskabsskurBeklædning='" + shedMaterial + '\'' +
                ", RedskabsskurBredde=" + shedWidth +
                ", RedskabsskurLængde=" + shedLength +
                ", TagHældning=" + roofTilt +
                ", TagMateriale='" + roofMaterial + '\'' +
                ", RoofType=" + roofType +
                '}';
    }
}
