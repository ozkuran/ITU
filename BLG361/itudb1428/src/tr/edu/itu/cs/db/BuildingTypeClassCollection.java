package tr.edu.itu.cs.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;


public class BuildingTypeClassCollection implements
        IBuildingTypeClassCollection {

    private Connection _db;

    public BuildingTypeClassCollection() {
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

    @Override
    public List<BuildingTypeClass> getBuildingTypeClasses() {
        List<BuildingTypeClass> buildingTypeClasses = new LinkedList<BuildingTypeClass>();
        try {
            String query = "SELECT id, BuildingTypeClassName FROM BuildingTypeClass";
            Statement statement = this._db.createStatement();
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                Integer id = results.getInt("id");
                String name = results.getString("BuildingTypeClassName");
                BuildingTypeClass buildingTypeClass = new BuildingTypeClass(
                        name);
                buildingTypeClass.setId(id);
                buildingTypeClass.setName(name);
                buildingTypeClasses.add(buildingTypeClass);
            }
            results.close();
            statement.close();
        } catch (SQLException e) {
            throw new UnsupportedOperationException(e.getMessage());
        }
        return buildingTypeClasses;
    }

    @Override
    public void addBuildingTypeClass(BuildingTypeClass buildingTypeClass) {
        try {
            String query = "INSERT INTO BuildingTypeClass (BuildingTypeClassName) VALUES (?)";
            PreparedStatement statement = this._db.prepareStatement(query);
            statement.setString(1, buildingTypeClass.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new UnsupportedOperationException(e.getMessage());
        }
    }

    @Override
    public void deleteBuildingTypeClass(BuildingTypeClass buildingTypeClass) {
        try {
            String query = "DELETE FROM BuildingTypeClass WHERE (id = ?)";
            PreparedStatement statement = this._db.prepareStatement(query);
            statement.setInt(1, buildingTypeClass.getId());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            throw new UnsupportedOperationException(e.getMessage());
        }
    }

    @Override
    public void updateBuildingTypeClass(BuildingTypeClass buildingTypeClass) {
        try {
            String query = "UPDATE BuildingTypeClass SET BuildingTypeClassName = ? WHERE (id = ?)";
            PreparedStatement statement = this._db.prepareStatement(query);
            statement.setString(1, buildingTypeClass.getName());
            statement.setInt(2, buildingTypeClass.getId());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            throw new UnsupportedOperationException(e.getMessage());
        }
    }

}
