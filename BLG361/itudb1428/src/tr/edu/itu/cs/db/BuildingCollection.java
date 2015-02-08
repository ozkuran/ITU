package tr.edu.itu.cs.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;


public class BuildingCollection {
    private Connection _db;
    private List<Building> Buildings = new LinkedList<Building>();
    private List<BuildingListWithPropertiesObject> buildingListWithPropertiesList = new LinkedList<BuildingListWithPropertiesObject>();

    public BuildingCollection() {
        // TODO Auto-generated constructor stub
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (java.lang.ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            this._db = DriverManager
                    .getConnection(
                            "jdbc:mysql://us-cdbr-iron-east-01.cleardb.net:3306/ad_31f6324e3b30ec5",
                            "b0383e8cfd185f", "4a16a365");
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public List<Building> getAllBuildings() {
        try {
            Statement statement = this._db.createStatement();
            ResultSet results = statement
                    .executeQuery("SELECT * FROM building");
            while (results.next()) {
                int id = results.getInt("id");
                int BuildingTypeId = results.getInt("BuildingTypeId");
                int StyleId = results.getInt("StyleId");
                int CityId = results.getInt("CityId");
                int Architectural_element_Id = results
                        .getInt("Architectural_element_Id");
                int Agesid = results.getInt("Agesid");
                double Latitude = results.getDouble("Latitude");
                double Longtitude = results.getDouble("Longtitude");
                String BuildingName = results.getString("BuildingName");
                String Adress = results.getString("Address");
                Building b = new Building(id, BuildingTypeId, StyleId, CityId,
                        Architectural_element_Id, Agesid, Latitude, Longtitude,
                        BuildingName, Adress);
                Buildings.add(b);
            }
            results.close();
            statement.close();
        } catch (SQLException e) {
            throw new UnsupportedOperationException(e.getMessage());
        }
        return Buildings;
    }

    public List<BuildingListWithPropertiesObject> getAllBuildingsPropList() {
        String query = "SELECT DISTINCT BuildingName,BuildingTypename,Address,CityName,Latitude,Longtitude,stylename,architectural_elements.name,age"
                + " FROM building LEFT OUTER JOIN buildingtype ON building.BuildingTypeId=buildingtype.id LEFT OUTER JOIN city ON CityId=city.id LEFT OUTER JOIN  styles ON StyleId=styles.ID LEFT OUTER JOIN architectural_elements "
                + "ON architectural_element_Id=architectural_elements.ID LEFT OUTER JOIN ages ON agesid=ages.id";
        try {
            Statement statement = this._db.createStatement();
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                BuildingListWithPropertiesObject b = new BuildingListWithPropertiesObject();
                b.BuildingName = results.getString("BuildingName");
                b.BuildingType = results.getString("BuildingTypename");
                b.Adress = results.getString("Address");
                b.City = results.getString("CityName");
                b.Style = results.getString("stylename");
                b.architectural_elements = results
                        .getString("architectural_elements.name");
                b.age = results.getString("age");
                b.Latitude = results.getDouble("Latitude");
                b.Longtitude = results.getDouble("Longtitude");

                buildingListWithPropertiesList.add(b);
            }
            results.close();
            statement.close();
        } catch (SQLException e) {
            throw new UnsupportedOperationException(e.getMessage());
        }
        return buildingListWithPropertiesList;
    }

    void AddBuilding(Building b) {
        try {
            String query = "INSERT INTO building (BuildingName,BuildingTypeId,Address,CityId,Latitude,Longtitude,StyleId,architectural_element_Id,agesid) VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement statement = this._db.prepareStatement(query);
            statement.setString(1, b.getBuildingName());
            statement.setInt(2, b.getBuildingTypeId());
            statement.setString(3, b.getAdress());
            statement.setInt(4, b.getCityId());
            statement.setDouble(5, b.getLatitude());
            statement.setDouble(6, b.getLongtitude());
            statement.setInt(7, b.getStyleId());
            statement.setInt(8, b.getArchitectural_element_Id());
            statement.setInt(9, b.getAgesid());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            throw new UnsupportedOperationException(e.getMessage());
        }
    }

    void UpdateBuilding(Building b) {
        try {
            String query = "UPDATE  building SET BuildingName=?,BuildingTypeId=?,Address=?,CityId=?,Latitude=?,Longtitude=?,StyleId=?,architectural_element_Id=?,agesid=? WHERE id= ?";
            PreparedStatement statement = this._db.prepareStatement(query);
            statement.setString(1, b.getBuildingName());
            statement.setInt(2, b.getBuildingTypeId());
            statement.setString(3, b.getAdress());
            statement.setInt(4, b.getCityId());
            statement.setDouble(5, b.getLatitude());
            statement.setDouble(6, b.getLongtitude());
            statement.setInt(7, b.getStyleId());
            statement.setInt(8, b.getArchitectural_element_Id());
            statement.setInt(9, b.getAgesid());
            statement.setInt(10, b.getid());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            throw new UnsupportedOperationException(e.getMessage());
        }
    }

    void DeleteBuilding(Building b) {
        try {
            String query = "DELETE FROM  building WHERE id=?";
            PreparedStatement statement = this._db.prepareStatement(query);
            statement.setInt(1, b.getid());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            throw new UnsupportedOperationException(e.getMessage());
        }
    }

}
