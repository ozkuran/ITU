package tr.edu.itu.cs.db;

public class Building {
    private int id;
    private int BuildingTypeId;
    private int StyleId;
    private int CityId;
    private int Architectural_element_Id;
    private int Agesid;
    private double Latitude;
    private double Longtitude;
    private String BuildingName;
    private String Adress;

    public Building() {
    }

    public Building(int id, int BuildingTypeId, int StyleId, int CityId,
            int Architectural_element_Id, int Agesid, double Latitude,
            double Longtitude, String BuildingName, String Adress) {
        this.id = id;
        this.BuildingTypeId = BuildingTypeId;
        this.StyleId = StyleId;
        this.CityId = CityId;
        this.Architectural_element_Id = Architectural_element_Id;
        this.Agesid = Agesid;
        this.Latitude = Latitude;
        this.Longtitude = Longtitude;
        this.BuildingName = BuildingName;
        this.Adress = Adress;
    }

    public Building(int BuildingTypeId, int StyleId, int CityId,
            int Architectural_element_Id, int Agesid, double Latitude,
            double Longtitude, String BuildingName, String Adress) {
        this.BuildingTypeId = BuildingTypeId;
        this.StyleId = StyleId;
        this.CityId = CityId;
        this.Architectural_element_Id = Architectural_element_Id;
        this.Agesid = Agesid;
        this.Latitude = Latitude;
        this.Longtitude = Longtitude;
        this.BuildingName = BuildingName;
        this.Adress = Adress;
    }

    public int getid() {
        return id;
    }

    public int getBuildingTypeId() {
        return BuildingTypeId;
    }

    public int getCityId() {
        return CityId;
    }

    public int getStyleId() {
        return StyleId;
    }

    public int getArchitectural_element_Id() {
        return Architectural_element_Id;
    }

    public int getAgesid() {
        return Agesid;
    }

    public double getLatitude() {
        return Latitude;
    }

    public double getLongtitude() {
        return Longtitude;
    }

    public String getBuildingName() {
        return BuildingName;
    }

    public String getAdress() {
        return Adress;
    }

    void setid(int i) {
        id = i;
    }

    void setBuildingTypeId(int i) {
        BuildingTypeId = i;
    }

    void setCityId(int i) {
        CityId = i;
    }

    void setStyleId(int i) {
        StyleId = i;
    }

    void setArchitectural_element_Id(int i) {
        Architectural_element_Id = i;
    }

    void setAgesid(int i) {
        Agesid = i;
    }

    void setLatitude(double d) {
        Latitude = d;
    }

    void setLongtitude(double d) {
        Longtitude = d;
    }

    void setAdress(String s) {
        Adress = s;
    }

    void setBuildingName(String s) {
        BuildingName = s;
    }

}
