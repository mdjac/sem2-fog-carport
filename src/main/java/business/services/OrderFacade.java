package business.services;

import business.entities.Option;
import business.exceptions.UserException;
import business.persistence.Database;
import business.persistence.OptionMapper;

import java.util.TreeMap;

public class OrderFacade {
    private OptionMapper optionMapper;

    public OrderFacade(Database database) {
        this.optionMapper = new OptionMapper(database);
    }

}
