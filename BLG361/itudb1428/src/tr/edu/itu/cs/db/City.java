package tr.edu.itu.cs.db;

public class City extends DatabaseManager {
    private Integer _id = null;
    // private String _countryid = null;
    private String _cityName = null;

    public City(String _name) {
        _cityName = _name;
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    /*
     * public String get_countryid() { return _countryid; }
     * 
     * public void set_countryid(String _countryid) { this._countryid =
     * _countryid; }
     */

    public String get_name() {
        return _cityName;
    }

    public void set_name(String _name) {
        _cityName = _name;
    }
}
