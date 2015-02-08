package tr.edu.itu.cs.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;


public class ArcElmCollection {
    private Connection _db;
    List<ArcElm> ArcElms = new LinkedList<ArcElm>();
    List<Building> Buildings = new LinkedList<Building>();

    public List<ArcElm> getArcElms() {
        return ArcElms;
    }

    public ArcElmCollection() {
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

    public List<ArcElm> getArcElmsFromDb(String qry) {
        String query = qry;
        try {
            Statement statement = this._db.createStatement();
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                Integer id = results.getInt("ID");
                String name = results.getString("name");
                String def = results.getString("info");
                ArcElm a = new ArcElm(id, name, def);
                ArcElms.add(a);
            }
            results.close();
            statement.close();
        } catch (SQLException e) {
            throw new UnsupportedOperationException(e.getMessage());
        }
        return ArcElms;
    }

    public List<ArcElm> Search() {
        return getArcElmsFromDb("SELECT *  FROM architectural_elements");
    }

    public List<ArcElm> Search(int id) { // search by id
        return getArcElmsFromDb("SELECT  *  FROM architectural_elements WHERE ID="
                + Integer.toString(id));
    }

    public List<ArcElm> Search(String nm) { // search by name
        return getArcElmsFromDb("SELECT  *  FROM architectural_elements WHERE name="
                + nm);
    }

    public List<Building> SearchBuilding(ArcElm a) { // search by name
        String query = "SELECT  BuildingName  FROM building WHERE architectural_element_Id="
                + Integer.toString(a.getId());

        try {
            Statement statement = this._db.createStatement();
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                String name = results.getString("BuildingName");
                Building b = new Building();
                b.setBuildingName(name);
                Buildings.add(b);
            }
            results.close();
            statement.close();
        } catch (SQLException e) {
            throw new UnsupportedOperationException(e.getMessage());
        }
        return Buildings;
    }

    public void addArcElm(ArcElm a) {
        try {
            String query = "INSERT INTO architectural_elements (name,info) VALUES (?,?)";
            PreparedStatement statement = this._db.prepareStatement(query);
            statement.setString(1, a.getName());
            statement.setString(2, a.getDef());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            throw new UnsupportedOperationException(e.getMessage());
        }
    }

    public void deleteArcElm(ArcElm a) {
        try {
            String query = "DELETE FROM architectural_elements WHERE (ID = ?)";
            PreparedStatement statement = this._db.prepareStatement(query);
            statement.setInt(1, a.getId());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            throw new UnsupportedOperationException(e.getMessage());
        }
    }

    public void updateArcElm(ArcElm a) {
        try {
            String query = "UPDATE architectural_elements SET name = ?,info = ?  WHERE (ID = ?)";
            PreparedStatement statement = this._db.prepareStatement(query);
            statement.setString(1, a.getName());
            statement.setString(2, a.getDef());
            statement.setInt(3, a.getId());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            throw new UnsupportedOperationException(e.getMessage());
        }
    }

}
