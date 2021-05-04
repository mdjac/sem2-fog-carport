package business.entities;

public class User
{

    public User(String email, String role)
    {
        this.email = email;
        this.role = role;
    }

    private int id; // just used to demo retrieval of autogen keys in UserMapper
    private String email;
    private String role;

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getRole()
    {
        return role;
    }

    public void setRole(String role)
    {
        this.role = role;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

}
