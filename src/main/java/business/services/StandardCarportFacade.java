package business.services;

import business.entities.Carport;
import business.entities.OrderLine;
import business.entities.RoofType;
import business.exceptions.UserException;
import business.persistence.Database;
import business.persistence.OrderLineMapper;
import business.persistence.StandardCarportMapper;

import java.sql.SQLException;
import java.util.TreeMap;

public class StandardCarportFacade {
    StandardCarportMapper standardCarportMapper;

    public StandardCarportFacade(Database database){
        this.standardCarportMapper = new StandardCarportMapper(database);
    }

    public boolean insertStandardCarport (RoofType roofType, int roofMaterialId, int carportMaterialId, int carportLength, int carportWidth, int carportHeight, Integer roofTilt, Integer shedMaterialId, Integer shedLength, Integer shedWidth) throws UserException{
        return standardCarportMapper.insertStandardCarport(roofType,roofMaterialId,carportMaterialId,carportLength,carportWidth,carportHeight,roofTilt,shedMaterialId,shedLength,shedWidth);
    }

    public TreeMap<Integer, Carport> getStandardCarports() throws UserException {
        return standardCarportMapper.getStandardCarports();
    }
}
