package business.persistence;

import business.exceptions.UserException;
import business.entities.User;

import java.sql.*;

public class UserMapper
{
    private Database database;

    public UserMapper(Database database)
    {
        this.database = database;
    }


    public User updateUser(User user, String newpassword) throws UserException
    {
        try (Connection connection = database.connect())
        {
            String sql;
            //Password is not a part of user object so has to be checked seperately.
            if(newpassword != null){
                 sql = "UPDATE `users` SET `email` = ?, `password` = ?, telephone = ? WHERE (`id` = ?)";
            }else{
                 sql = "UPDATE `users` SET `email` = ?, telephone = ? WHERE (`id` = ?)";
            }
            try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
            {
                if(newpassword != null){
                    ps.setString(1,user.getEmail());
                    ps.setString(2, newpassword);
                    ps.setString(3,user.getTelephone());
                    ps.setInt(4,user.getId());
                }else{
                    ps.setString(1,user.getEmail());
                    ps.setString(2,user.getTelephone());
                    ps.setInt(3,user.getId());
                }
                int rowAffected = ps.executeUpdate();
                if (rowAffected != 1) {
                    throw new UserException("Error when updating user");
                }
                return user;
            }
            catch (SQLException ex)
            {
                throw new UserException(ex.getMessage());
            }
        }
        catch (SQLException ex)
        {
            throw new UserException(ex.getMessage());
        }
    }


    public void createUser(User user, String password) throws UserException
    {
        try (Connection connection = database.connect())
        {
            String sql = "INSERT INTO users (email, password, role, address, telephone, zip, city, name) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
            {
                ps.setString(1, user.getEmail());
                ps.setString(2, password);
                ps.setString(3, user.getRole());
                ps.setString(4,user.getAddress());
                ps.setString(5,user.getTelephone());
                ps.setInt(6,user.getZip());
                ps.setString(7, user.getCity());
                ps.setString(8,user.getName());
                ps.executeUpdate();
                ResultSet ids = ps.getGeneratedKeys();
                ids.next();
                int id = ids.getInt(1);
                user.setId(id);
            }
            catch (SQLException ex)
            {
                throw new UserException(ex.getMessage());
            }
        }
        catch (SQLException ex)
        {
            throw new UserException(ex.getMessage());
        }
    }

    public User login(String email, String password) throws UserException
    {
        try (Connection connection = database.connect())
        {
            String sql = "SELECT id, role, address, telephone, zip, city, name FROM users WHERE email=? AND password=?";

            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ps.setString(1, email);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();
                if (rs.next())
                {
                    String role = rs.getString("role");
                    int id = rs.getInt("id");
                    String address = rs.getString("address");
                    String telephone = rs.getString("telephone");
                    int zip = rs.getInt("zip");
                    String city = rs.getString("city");
                    String name = rs.getString("name");
                    User user = new User(email, role,address,telephone,zip,city,name);
                    user.setId(id);
                    return user;
                } else
                {
                    throw new UserException("Could not validate user");
                }
            }
            catch (SQLException ex)
            {
                throw new UserException(ex.getMessage());
            }
        }
        catch (SQLException ex)
        {
            throw new UserException("Connection to database could not be established");
        }
    }

}
