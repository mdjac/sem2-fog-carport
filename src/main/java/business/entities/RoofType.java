package business.entities;

public enum RoofType {
   Fladt_Tag("Fladt tag"),
    Tag_Med_Rejsning("Tag med rejsning");

   String name;

    RoofType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
