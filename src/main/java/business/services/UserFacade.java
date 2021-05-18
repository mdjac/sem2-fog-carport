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

    public User createUser(String email, String password, String address, String telephone, int zip, String city, String name) throws UserException
    {
        User user = new User(email,"customer",address,telephone,zip,city,name);
        String encryptedPassword = Encryption.encryptThisString(password);
        userMapper.createUser(user,encryptedPassword);
        return user;
    }

    public User updateUser(User user, String newpassword) throws UserException{
        return userMapper.updateUser(user,newpassword);
    }

}
