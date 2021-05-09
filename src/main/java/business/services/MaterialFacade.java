package business.services;

import business.entities.Material;
import business.exceptions.UserException;
import business.persistence.Database;
import business.persistence.MaterialMapper;

import java.util.List;
import java.util.TreeMap;

public class MaterialFacade {
    private MaterialMapper materialMapper;

    public MaterialFacade(Database database) {
        this.materialMapper = new MaterialMapper(database);
    }

    public TreeMap<Integer, TreeMap<Integer,Material>> getAllMaterials() throws UserException {
        return materialMapper.getAllMaterials();
    }
}
