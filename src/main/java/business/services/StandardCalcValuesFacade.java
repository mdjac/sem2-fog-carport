package business.services;

import business.entities.MinMax;
import business.exceptions.UserException;
import business.persistence.Database;
import business.persistence.StandardCalcValuesMapper;

import java.util.TreeMap;

public class StandardCalcValuesFacade {
    StandardCalcValuesMapper standardCalcValuesMapper;

    public StandardCalcValuesFacade(Database database) {
        this.standardCalcValuesMapper = new StandardCalcValuesMapper(database);
    }

    public TreeMap<String, MinMax> getAllowedMeasurements() throws UserException {
        return standardCalcValuesMapper.getAllowedMeasurements();
    }

    public TreeMap<String, MinMax> getCalculatorRequiredMaterialWidth() throws UserException {
        return standardCalcValuesMapper.getCalculatorRequiredMaterialWidth();
    }

    public TreeMap<String, MinMax> getRaftersDistance() throws UserException {
        return standardCalcValuesMapper.getRaftersDistance();
    }
    public TreeMap<String, MinMax> getPostDistances() throws UserException {
        return standardCalcValuesMapper.getPostDistances();
    }

    public TreeMap<String, MinMax> getPriceCalculatorValues() throws UserException {
        return standardCalcValuesMapper.getPriceCalculatorValues();
    }
}
