package business.entities;

public class Carport {
    //The reason for Integer at some of them, is to make them possible to be null for prettier print on site.
private int id;
private String carportBeklædning;
private int CarportBredde;
private int CarportHøjde;
private int CarportLængde;
private String RedskabsskurBeklædning;
private Integer RedskabsskurBredde;
private Integer RedskabsskurLængde;
private Integer TagHældning;
private String TagMateriale;
private RoofType RoofType;

    public Carport(String carportBeklædning, int carportBredde, int carportHøjde, int carportLængde, RoofType roofType) {
        this.carportBeklædning = carportBeklædning;
        CarportBredde = carportBredde;
        CarportHøjde = carportHøjde;
        CarportLængde = carportLængde;
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

    public void setRedskabsskurBeklædning(String redskabsskurBeklædning) {
        RedskabsskurBeklædning = redskabsskurBeklædning;
    }

    public String getRedskabsskurBeklædning() {
        return RedskabsskurBeklædning;
    }

    public Integer getRedskabsskurBredde() {
        return RedskabsskurBredde;
    }

    public Integer getRedskabsskurLængde() {
        return RedskabsskurLængde;
    }

    public Integer getTagHældning() {
        return TagHældning;
    }

    public void setRedskabsskurBredde(Integer redskabsskurBredde) {
        RedskabsskurBredde = redskabsskurBredde;
    }

    public void setRedskabsskurLængde(Integer redskabsskurLængde) {
        RedskabsskurLængde = redskabsskurLængde;
    }

    public void setTagHældning(Integer tagHældning) {
        TagHældning = tagHældning;
    }

    public String getTagMateriale() {
        return TagMateriale;
    }

    public String getRoofType() {
        return RoofType.toString();
    }


    public void setTagMateriale(String tagMateriale) {
        TagMateriale = tagMateriale;
    }

    @Override
    public String toString() {
        return "Carport{" +
                "id=" + id +
                ", carportBeklædning='" + carportBeklædning + '\'' +
                ", CarportBredde=" + CarportBredde +
                ", CarportHøjde=" + CarportHøjde +
                ", CarportLængde=" + CarportLængde +
                ", RedskabsskurBeklædning='" + RedskabsskurBeklædning + '\'' +
                ", RedskabsskurBredde=" + RedskabsskurBredde +
                ", RedskabsskurLængde=" + RedskabsskurLængde +
                ", TagHældning=" + TagHældning +
                ", TagMateriale='" + TagMateriale + '\'' +
                ", RoofType=" + RoofType +
                '}';
    }
}
