package business.services;

import business.entities.Option;
import business.exceptions.UserException;
import business.persistence.Database;
import business.persistence.OptionMapper;

import java.util.TreeMap;

public class OptionFacade {
    private OptionMapper optionMapper;

    public OptionFacade(Database database) {
        this.optionMapper = new OptionMapper(database);
    }

    public TreeMap<Integer, Option> getAllOptions() throws UserException {
        return optionMapper.getAllOptions();
    }
}
