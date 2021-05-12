package business.services;
import business.entities.Carport;
import business.persistence.Database;
import business.persistence.StandardCarportMapper;
import java.sql.SQLException;
import java.util.TreeMap;

public class StandardCarportFacade {
    private StandardCarportMapper standardCarportMapper;

    public StandardCarportFacade(Database database) throws SQLException {
        this.standardCarportMapper = new StandardCarportMapper(database);
    }
    public TreeMap<Integer, TreeMap<Integer, Carport>> getStandardCarport() throws SQLException {
        return standardCarportMapper.getStandardCarport();
    }

}
