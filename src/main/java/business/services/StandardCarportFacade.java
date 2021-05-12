package business.services;

import business.entities.Carport;
import business.entities.OrderLine;
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

    public TreeMap<Integer, Carport> getStandardCarports() throws UserException {
        return standardCarportMapper.getStandardCarports();
    }
}
