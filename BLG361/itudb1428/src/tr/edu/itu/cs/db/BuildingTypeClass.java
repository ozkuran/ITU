package tr.edu.itu.cs.db;

public class BuildingTypeClass {

    private Integer _id = null;
    private String _buildingTypeClassName = null;

    public BuildingTypeClass() {

    }

    public BuildingTypeClass(String name) {
        this.setName(name);
    }

    public void setId(Integer id) {
        this._id = id;
    }

    public Integer getId() {
        return this._id;
    }

    public void setName(String name) {
        this._buildingTypeClassName = name;
    }

    public String getName() {
        return this._buildingTypeClassName;
    }
}
