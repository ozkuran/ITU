package tr.edu.itu.cs.db;

public class PersonType extends DatabaseManager {

    private Integer _id = null;
    private String _personTypeName = null;

    public PersonType() {

    }

    public PersonType(String name) {
        this.setName(name);
    }

    public void setId(Integer id) {
        this._id = id;
    }

    public Integer getId() {
        return this._id;
    }

    public void setName(String name) {
        this._personTypeName = name;
    }

    public String getName() {
        return this._personTypeName;
    }
}
