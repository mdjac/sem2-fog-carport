package business.entities;

public class User
{

    public User(String email, String role, String address, String telephone, int zip, String city, String name) {
        this.email = email;
        this.role = role;
        this.address = address;
        this.telephone = telephone;
        this.zip = zip;
        this.city = city;
        this.name = name;
    }

    private int id; // just used to demo retrieval of autogen keys in UserMapper
    private String email;
    private String role;
    private String address;
    private String telephone;
    private int zip;
    private String city;
    private String name;

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

    public String getCity() {
        return city;
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

    public String getName() {
        return name;
    }
}
