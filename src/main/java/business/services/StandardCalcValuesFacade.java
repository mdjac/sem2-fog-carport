package business.services;

import business.entities.AllowedMinMax;
import business.exceptions.UserException;
import business.persistence.Database;
import business.persistence.StandardCalcValuesMapper;

import java.util.TreeMap;

public class StandardCalcValuesFacade {
    StandardCalcValuesMapper standardCalcValuesMapper;

    public StandardCalcValuesFacade(Database database) {
        this.standardCalcValuesMapper = new StandardCalcValuesMapper(database);
    }

    public TreeMap<String, AllowedMinMax> getAllowedMeasurements() throws UserException {
        return standardCalcValuesMapper.getAllowedMeasurements();
    }
}
