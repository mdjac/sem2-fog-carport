package business.entities;

public class User
{

    public User(String email, String role, String address, String telephone, int zip) {
        this.email = email;
        this.role = role;
        this.address = address;
        this.telephone = telephone;
        this.zip = zip;
    }

    private int id; // just used to demo retrieval of autogen keys in UserMapper
    private String email;
    private String role;
    private String address;
    private String telephone;
    private int zip;

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

    public String getAddress() {
        return address;
    }

    public String getTelephone() {
        return telephone;
    }

    public int getZip() {
        return zip;
    }
}
