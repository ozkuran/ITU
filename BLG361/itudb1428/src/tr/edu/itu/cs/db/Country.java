package tr.edu.itu.cs.db;

public class Country extends DatabaseManager {

    private Integer _id = null;
    private String _countryName = null;

    public Country() {

    }

    public Country(String name) {
        this.setName(name);
    }

    public void setId(Integer id) {
        this._id = id;
    }

    public Integer getId() {
        return this._id;
    }

    public void setName(String name) {
        this._countryName = name;
    }

    public String getName() {
        return this._countryName;
    }
}
