package business.services;

import business.entities.User;
import business.persistence.Database;
import business.persistence.UserMapper;
import business.exceptions.UserException;
import business.utilities.Encryption;

public class UserFacade
{
    UserMapper userMapper;

    public UserFacade(Database database)
    {
        userMapper = new UserMapper(database);
    }

    public User login(String email, String password) throws UserException
    {
        String encryptedPassword = Encryption.encryptThisString(password);
        return userMapper.login(email, encryptedPassword);
    }

    public User createUser(String email, String password) throws UserException
    {
        User user = new User(email, "customer");
        String encryptedPassword = Encryption.encryptThisString(password);
        userMapper.createUser(user,encryptedPassword);
        return user;
    }

}
