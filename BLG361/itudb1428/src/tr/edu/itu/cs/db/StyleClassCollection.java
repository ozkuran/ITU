package tr.edu.itu.cs.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;


public class StyleClassCollection {
    private Connection _db;
    List<Style> Styles = new LinkedList<Style>();
    List<Building> Buildings = new LinkedList<Building>();

    public List<Style> getStyles() {
        return Styles;
    }

    public StyleClassCollection() {
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

    public List<Style> getStylesFromDb(String qry) {
        String query = qry;
        try {
            Statement statement = this._db.createStatement();
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                Integer id = results.getInt("ID");
                String name = results.getString("stylename");
                String def = results.getString("info");
                Style s = new Style(id, name, def);
                Styles.add(s);
            }
            results.close();
            statement.close();
        } catch (SQLException e) {
            throw new UnsupportedOperationException(e.getMessage());
        }
        return Styles;
    }

    public List<Style> Search() {
        return getStylesFromDb("SELECT *  FROM styles");
    }

    public List<Style> Search(int id) { // search by id
        return getStylesFromDb("SELECT  *  FROM styles WHERE ID="
                + Integer.toString(id));
    }

    public List<Style> Search(String nm) { // search by name
        return getStylesFromDb("SELECT  *  FROM styles WHERE name=" + nm);
    }

    public List<Building> SearchBuilding(Style s) { // search by name
        String query = "SELECT  BuildingName  FROM building WHERE StyleId="
                + Integer.toString(s.getId());

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

    public void addStyle(Style s) {
        try {
            String query = "INSERT INTO styles (stylename,info) VALUES (?,?)";
            PreparedStatement statement = this._db.prepareStatement(query);
            statement.setString(1, s.getName());
            statement.setString(2, s.getDef());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            throw new UnsupportedOperationException(e.getMessage());
        }
    }

    public void deleteStyle(Style s) {
        try {
            String query = "DELETE FROM styles WHERE (ID = ?)";
            PreparedStatement statement = this._db.prepareStatement(query);
            statement.setInt(1, s.getId());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            throw new UnsupportedOperationException(e.getMessage());
        }
    }

    public void updateStyle(Style s) {
        try {
            String query = "UPDATE styles SET stylename = ?,info = ?  WHERE (ID = ?)";
            PreparedStatement statement = this._db.prepareStatement(query);
            statement.setString(1, s.getName());
            statement.setString(2, s.getDef());
            statement.setInt(3, s.getId());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            throw new UnsupportedOperationException(e.getMessage());
        }
    }

}
