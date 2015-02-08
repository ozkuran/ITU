package tr.edu.itu.cs.db;

public class Person extends DatabaseManager {

    private Integer _id = null;
    private String _personName = null;
    private Integer _personType = null;

    public Person() {

    }

    public Person(String name) {
        this.setName(name);
    }

    public void setId(Integer id) {
        this._id = id;
    }

    public Integer getId() {
        return this._id;
    }

    public void setName(String name) {
        this._personName = name;
    }

    public String getName() {
        return this._personName;
    }

    public void setType(Integer personType) {
        this._personType = personType;
    }

    public Integer getType() {
        return this._personType;
    }
}
