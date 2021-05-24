package business.entities;

public class SvgValues {
    Integer carportId;
    Integer carportWidth;
    Integer carportLenght;
    Integer carportHeight;

    //Spær
    Double spærMaterialeBredde;
    Double spærMaterialeHøjde;
    Double spærMellemrum;
    Integer spærAntal;
    Integer spærAntalTværgående;
    Double spærMellemrumTværgående;

    //Remme
    Double remMaterialeBredde;
    Double remMaterialeHøjde;

    //Tag
    Double tagMedRejsningHøjde;

    //Stolper
    Double stolpeAfstandFront;
    Double stolpeAfstandBag;
    Double stolpeAfstand;
    Double stolpeMaterialeBredde;
    Integer stolpeAntal; //Alle stolper inklusiv skur

    //Skur
    Integer skurBredde;
    Integer skurLængde;
    Integer skurStolpeAntalFrontBag; //De tæller de samme hjørner med
    Integer skurStolpeAntalSider;  //De tæller de samme hjørner med
    Double stolpeAfstandSkurSider;
    Double stolpeAfstandSkurforOgBag;

    public Double getTagMedRejsningHøjde() {
        return tagMedRejsningHøjde;
    }

    public void setTagMedRejsningHøjde(Double tagMedRejsningHøjde) {
        this.tagMedRejsningHøjde = tagMedRejsningHøjde;
    }

    public Double getSpærMaterialeHøjde() {
        return spærMaterialeHøjde;
    }

    public void setSpærMaterialeHøjde(Double spærMaterialeHøjde) {
        this.spærMaterialeHøjde = spærMaterialeHøjde;
    }

    public Double getRemMaterialeHøjde() {
        return remMaterialeHøjde;
    }

    public void setRemMaterialeHøjde(Double remMaterialeHøjde) {
        this.remMaterialeHøjde = remMaterialeHøjde;
    }

    public Integer getSkurStolpeAntalFrontBag() {
        return skurStolpeAntalFrontBag;
    }

    public void setSkurStolpeAntalFrontBag(Integer skurStolpeAntalFrontBag) {
        this.skurStolpeAntalFrontBag = skurStolpeAntalFrontBag;
    }

    public Integer getSkurStolpeAntalSider() {
        return skurStolpeAntalSider;
    }

    public void setSkurStolpeAntalSider(Integer skurStolpeAntalSider) {
        this.skurStolpeAntalSider = skurStolpeAntalSider;
    }

    public Integer getCarportHeight() {
        return carportHeight;
    }

    public void setCarportHeight(Integer carportHeight) {
        this.carportHeight = carportHeight;
    }

    public Integer getSpærAntal() {
        return spærAntal;
    }

    public Integer getSpærAntalTværgående() {
        return spærAntalTværgående;
    }

    public void setSpærAntalTværgående(Integer spærAntalTværgående) {
        this.spærAntalTværgående = spærAntalTværgående;
    }

    public Double getSpærMellemrumTværgående() {
        return spærMellemrumTværgående;
    }

    public void setSpærMellemrumTværgående(Double spærMellemrumTværgående) {
        this.spærMellemrumTværgående = spærMellemrumTværgående;
    }


    public Integer getCarportId() {
        return carportId;
    }

    public void setCarportId(Integer carportId) {
        this.carportId = carportId;
    }


    public void setSpærAntal(Integer spærAntal) {
        this.spærAntal = spærAntal;
    }

    public Integer getCarportWidth() {
        return carportWidth;
    }

    public void setCarportWidth(Integer carportWidth) {
        this.carportWidth = carportWidth;
    }

    public Integer getCarportLenght() {
        return carportLenght;
    }

    public void setCarportLenght(Integer carportLenght) {
        this.carportLenght = carportLenght;
    }

    public Double getSpærMaterialeBredde() {
        return spærMaterialeBredde;
    }

    public void setSpærMaterialeBredde(Double spærMaterialeBredde) {
        this.spærMaterialeBredde = spærMaterialeBredde;
    }

    public Double getSpærMellemrum() {
        return spærMellemrum;
    }

    public void setSpærMellemrum(Double spærMellemrum) {
        this.spærMellemrum = spærMellemrum;
    }

    public Double getRemMaterialeBredde() {
        return remMaterialeBredde;
    }

    public void setRemMaterialeBredde(Double remMaterialeBredde) {
        this.remMaterialeBredde = remMaterialeBredde;
    }

    public Double getStolpeAfstandFront() {
        return stolpeAfstandFront;
    }

    public void setStolpeAfstandFront(Double stolpeAfstandFront) {
        this.stolpeAfstandFront = stolpeAfstandFront;
    }

    public Double getStolpeAfstandBag() {
        return stolpeAfstandBag;
    }

    public void setStolpeAfstandBag(Double stolpeAfstandBag) {
        this.stolpeAfstandBag = stolpeAfstandBag;
    }

    public Double getStolpeAfstand() {
        return stolpeAfstand;
    }

    public void setStolpeAfstand(Double stolpeAfstand) {
        this.stolpeAfstand = stolpeAfstand;
    }

    public Double getStolpeMaterialeBredde() {
        return stolpeMaterialeBredde;
    }

    public void setStolpeMaterialeBredde(Double stolpeMaterialeBredde) {
        this.stolpeMaterialeBredde = stolpeMaterialeBredde;
    }

    public Integer getStolpeAntal() {
        return stolpeAntal;
    }

    public void setStolpeAntal(Integer stolpeAntal) {
        this.stolpeAntal = stolpeAntal;
    }

    public Integer getSkurBredde() {
        return skurBredde;
    }

    public void setSkurBredde(Integer skurBredde) {
        this.skurBredde = skurBredde;
    }

    public Integer getSkurLængde() {
        return skurLængde;
    }

    public void setSkurLængde(Integer skurLængde) {
        this.skurLængde = skurLængde;
    }

    public Double getStolpeAfstandSkurSider() {
        return stolpeAfstandSkurSider;
    }

    public void setStolpeAfstandSkurSider(Double stolpeAfstandSkurSider) {
        this.stolpeAfstandSkurSider = stolpeAfstandSkurSider;
    }

    public Double getStolpeAfstandSkurforOgBag() {
        return stolpeAfstandSkurforOgBag;
    }

    public void setStolpeAfstandSkurforOgBag(Double stolpeAfstandSkurforOgBag) {
        this.stolpeAfstandSkurforOgBag = stolpeAfstandSkurforOgBag;
    }
}
