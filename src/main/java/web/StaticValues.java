package web;

import business.entities.*;
import business.exceptions.UserException;
import business.persistence.Database;
import business.services.MaterialFacade;
import business.services.StandardCalcValuesFacade;
import business.services.StandardCarportFacade;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class StaticValues {
    public static TreeMap<Integer, Carport> standardCarports = new TreeMap<>();
    public static TreeMap<Integer, TreeMap<Integer, Material>> materialMap = new TreeMap<>();
    public static TreeMap<Integer, TreeMap<Integer, Material>> categoryFormOptions = new TreeMap<>();
    public static TreeMap<String, MinMax> allowedMeasurements = new TreeMap<>();
    public static TreeMap<Integer, TreeMap<Integer, Material>> materialVariantMap = new TreeMap<>();
    public static TreeMap<String,MinMax> calculatorRequiredMaterialWidth = new TreeMap<>();
    public static TreeMap<String, MinMax> raftersDistance = new TreeMap<>();
    public static TreeMap<String,MinMax> postDistances = new TreeMap<>();
    public static TreeMap<String,MinMax> priceCalculatorValues = new TreeMap<>();
    //Used to get SVG Values by carportId
    public static TreeMap<Integer, SvgValues> svgValuesTreeMap = new TreeMap<>();

    public StandardCalcValuesFacade standardCalcValuesFacade;
    public MaterialFacade materialFacade;
    public StandardCarportFacade standardCarportFacade;

    public void setGlobalValues (Database database) throws UserException {
        materialFacade = new MaterialFacade(database);
        standardCalcValuesFacade = new StandardCalcValuesFacade(database);
        standardCarportFacade = new StandardCarportFacade(database);

        System.out.println("All static values collected from: "+database.getURL());

        //Used to collect all material from the database
            materialMap = materialFacade.getAllMaterials();


        //Used to populate the dropdown options on the form based on what FOG decides to offer in database
        categoryFormOptions = getCategoryFormOptions(materialMap);

        //Used to set the standard carports from DB for display and ordering on website

            standardCarports = standardCarportFacade.getStandardCarports();


        //Used to make it possible for fog employee to modify BOM with other variants from the same materialID
        materialVariantMap = getMaterialVariantMap(materialMap);

        //Used to set which dimensions we allow for creation of new carports. eg. max length of the carport.

            allowedMeasurements = standardCalcValuesFacade.getAllowedMeasurements();


        //Used to set which width we require for specific materials (used in calculations of BOM) eg. we want a post to be a specific width as it cant be to slim

            calculatorRequiredMaterialWidth = standardCalcValuesFacade.getCalculatorRequiredMaterialWidth();


        //Used to set which min and max distance we allow for rafters (different for flat roof and roof with tilt).

            raftersDistance = standardCalcValuesFacade.getRaftersDistance();

        //Used to set post distances needed in calculations, eg. max distance between posts

            postDistances = standardCalcValuesFacade.getPostDistances();


        //Used for order price calculation

            priceCalculatorValues = standardCalcValuesFacade.getPriceCalculatorValues();

    }

    public TreeMap<Integer, TreeMap<Integer, Material>> getCategoryFormOptions(TreeMap<Integer, TreeMap<Integer, Material>> inputTreeMap){
        TreeMap<Integer, TreeMap<Integer, Material>> categoryFormOptions = new TreeMap<>();
        TreeMap<Integer, Material> formOption;
        for (Map.Entry<Integer, TreeMap<Integer, Material>> tmp : inputTreeMap.entrySet()) {
            for (Material tmp1 : tmp.getValue().values()) {
                if (!categoryFormOptions.containsKey(tmp.getKey())) {
                    formOption = new TreeMap<>();
                    formOption.put(tmp1.getMaterialsId(), tmp1);
                    categoryFormOptions.put(tmp1.getMaterialsCategoryId(), formOption);
                } else {
                    //If categori id exist then we add the material to the treeMap under the existing categoriID
                    formOption = categoryFormOptions.get(tmp.getKey());
                    //Check om materialeID eksistere i formoption listen, hvis ikke tilføj

                    if (!formOption.containsKey(tmp1.getMaterialsId())) {
                        formOption.put(tmp1.getMaterialsId(), tmp1);
                    }
                }
            }
        }
        return categoryFormOptions;
    }

    public TreeMap<Integer, TreeMap<Integer, Material>> getMaterialVariantMap(TreeMap<Integer, TreeMap<Integer, Material>> inputMap){
        TreeMap<Integer, TreeMap<Integer, Material>> materialVariantMap = new TreeMap<>();
        for (Map.Entry<Integer,Material> tmp: inputMap.get(5).entrySet()) {
            int materialId = tmp.getValue().getMaterialsId();
            if(!materialVariantMap.containsKey(materialId)){
                materialVariantMap.put(materialId,Material.getMaterialVariantsFromMaterialId(materialId));
            }
        }
        return materialVariantMap;
    }



}
