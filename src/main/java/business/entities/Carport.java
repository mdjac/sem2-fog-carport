package business.entities;

public class Carport {
private int id;
private String carportBeklædning;
private String CarportBredde;
private String CarportHøjde;
private String CarportLængde;
private String RedskabsskurBeklædning;
private String RedskabsskurBredde;
private String RedskabsskurLængde;
private String TagHældning;
private String TagMateriale;
private String TagType;

    public Carport(String carportBeklædning, String carportBredde, String carportHøjde, String carportLængde, String redskabsskurBeklædning, String redskabsskurBredde, String redskabsskurLængde, String tagHældning, String tagMateriale, String tagType) {
        this.carportBeklædning = carportBeklædning;
        CarportBredde = carportBredde;
        CarportHøjde = carportHøjde;
        CarportLængde = carportLængde;
        RedskabsskurBeklædning = redskabsskurBeklædning;
        RedskabsskurBredde = redskabsskurBredde;
        RedskabsskurLængde = redskabsskurLængde;
        TagHældning = tagHældning;
        TagMateriale = tagMateriale;
        TagType = tagType;
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

    public String getCarportBredde() {
        return CarportBredde;
    }

    public String getCarportHøjde() {
        return CarportHøjde;
    }

    public String getCarportLængde() {
        return CarportLængde;
    }

    public String getRedskabsskurBeklædning() {
        return RedskabsskurBeklædning;
    }

    public String getRedskabsskurBredde() {
        return RedskabsskurBredde;
    }

    public String getRedskabsskurLængde() {
        return RedskabsskurLængde;
    }

    public String getTagHældning() {
        return TagHældning;
    }

    public String getTagMateriale() {
        return TagMateriale;
    }

    public String getTagType() {
        return TagType;
    }
}
