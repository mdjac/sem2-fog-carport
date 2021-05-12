package business.entities;

public class Carport {
private int id;
private String carportBeklædning;
private int CarportBredde;
private int CarportHøjde;
private int CarportLængde;
private String RedskabsskurBeklædning;
private int RedskabsskurBredde;
private int RedskabsskurLængde;
private int TagHældning;
private String TagMateriale;
private RoofType RoofType;

    public Carport(String carportBeklædning, int carportBredde, int carportHøjde, int carportLængde, String redskabsskurBeklædning, int redskabsskurBredde, int redskabsskurLængde, int tagHældning, String tagMateriale, RoofType roofType) {
        this.carportBeklædning = carportBeklædning;
        CarportBredde = carportBredde;
        CarportHøjde = carportHøjde;
        CarportLængde = carportLængde;
        RedskabsskurBeklædning = redskabsskurBeklædning;
        RedskabsskurBredde = redskabsskurBredde;
        RedskabsskurLængde = redskabsskurLængde;
        TagHældning = tagHældning;
        TagMateriale = tagMateriale;
        RoofType = roofType;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getCarportBeklædning() {
        return carportBeklædning;
    }

    public int getCarportBredde() {
        return CarportBredde;
    }

    public int getCarportHøjde() {
        return CarportHøjde;
    }

    public int getCarportLængde() {
        return CarportLængde;
    }

    public String getRedskabsskurBeklædning() {
        return RedskabsskurBeklædning;
    }

    public int getRedskabsskurBredde() {
        return RedskabsskurBredde;
    }

    public int getRedskabsskurLængde() {
        return RedskabsskurLængde;
    }

    public int getTagHældning() {
        return TagHældning;
    }

    public String getTagMateriale() {
        return TagMateriale;
    }

    public String getRoofType() {
        return RoofType.toString();
    }
}
