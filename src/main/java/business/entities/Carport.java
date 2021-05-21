package business.entities;

import business.exceptions.UserException;
import web.FrontController;
import web.StaticValues;

public class Carport {
    //The reason for Integer at some of them, is to make them possible to be null for prettier print on site.
private int id;
private Material carportMaterial;
private int carportWidth;
private int carportHeight;
private int carportLength;
private Material shedMaterial;
private Integer shedWidth;
private Integer shedLength;
private Integer roofTilt;
private Material roofMaterial;
private RoofType roofType;
private Integer standardCarportId;

    public Carport(Material carportMaterial, int carportWidth, int carportHeight, int carportLength, RoofType roofType, Material roofMaterial) {
        this.carportMaterial = carportMaterial;
        this.carportWidth = carportWidth;
        this.carportHeight = carportHeight;
        this.carportLength = carportLength;
        this.roofType = roofType;
        this.roofMaterial = roofMaterial;
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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

    public void setShedMaterial(Material shedMaterial) {
        this.shedMaterial = shedMaterial;
    }

    public void setRoofMaterial(Material roofMaterial) {
        this.roofMaterial = roofMaterial;
    }

    public Material getCarportMaterial() {
        return carportMaterial;
    }

    public Material getShedMaterial() {
        return shedMaterial;
    }

    public Material getRoofMaterial() {
        return roofMaterial;
    }

    public String getRoofType() {
        return roofType.toString();
    }

    public Integer getStandardCarportId() {
        return standardCarportId;
    }

    public void setStandardCarportId(Integer standardCarportId) {
        this.standardCarportId = standardCarportId;
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

    public static Material findCarportMaterialFromId(int materialId){
        return StaticValues.categoryFormOptions.get(1).get(materialId);
    }
    public static Material findShedMaterialFromId(int materialId){
        return StaticValues.categoryFormOptions.get(3).get(materialId);
    }
    public static Material findRoofMaterialFromId(int materialId, RoofType roofType) throws UserException {
        if (roofType == RoofType.Fladt_Tag) {
            //Fladt tag materiale category id == 2
            return StaticValues.categoryFormOptions.get(2).get(materialId);
        } else if (roofType == RoofType.Tag_Med_Rejsning) {
            //Tag med rejsning materiale category id == 4
            return StaticValues.categoryFormOptions.get(4).get(materialId);
        } else {
            throw new UserException("Failed when trying to find Roof Material as RoofType doesn't exist");
        }
    }

    public boolean acceptableMeasurements(){
        //Check if the carports dimensions is okay
        boolean acceptableMeasurements = false;
        if(StaticValues.allowedMeasurements.get("carportLængde").between(this.getCarportLength()) && StaticValues.allowedMeasurements.get("carportBredde").between(this.getCarportWidth()) && StaticValues.allowedMeasurements.get("carportHøjde").between(this.getCarportHeight())){
            //Carport dimensions is okay
            acceptableMeasurements = true;
            //Check for tilted roof, to see if tilt is acceptable
            if(this.getRoofTilt() != null){
                acceptableMeasurements = StaticValues.allowedMeasurements.get("tagHældning").between(this.getRoofTilt());
            }
            //Check if shed is choosen and that boolean isnt false already from the rooftilt check
            if(acceptableMeasurements == true && this.getShedMaterial() != null){
                //Check if the shed dimensions is okay
                if(StaticValues.allowedMeasurements.get("redskabsskurLængde").between(this.getShedLength()) && StaticValues.allowedMeasurements.get("redskabsskurBredde").between(this.getShedWidth())){
                    acceptableMeasurements = true;
                }
                else{
                    acceptableMeasurements = false;
                }
            }
        }
        return acceptableMeasurements;
    }

    public void setCarportWidth(int carportWidth) {
        this.carportWidth = carportWidth;
    }

    public void setCarportHeight(int carportHeight) {
        this.carportHeight = carportHeight;
    }

    public void setCarportLength(int carportLength) {
        this.carportLength = carportLength;
    }
}
