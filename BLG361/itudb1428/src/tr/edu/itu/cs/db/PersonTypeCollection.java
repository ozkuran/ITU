package tr.edu.itu.cs.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;


public class PersonTypeCollection implements IPersonTypeCollection {
    private Connection _db;

    public PersonTypeCollection() {
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
    public List<PersonType> getPersonTypes() {
        List<PersonType> personTypes = new LinkedList<PersonType>();
        try {
            String query = "SELECT id, PersonTypeName FROM PersonType";
            Statement statement = this._db.createStatement();
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                Integer id = results.getInt("id");
                String name = results.getString("PersonTypeName");
                PersonType personType = new PersonType(name);
                personType.setId(id);
                personType.setName(name);
                personTypes.add(personType);
            }
            results.close();
            statement.close();
        } catch (SQLException e) {
            throw new UnsupportedOperationException(e.getMessage());
        }
        return personTypes;
    }

    @Override
    public String getPersonTypeById(Integer id) {
        String personTypeName = new String();
        try {
            String query = "SELECT id, PersonTypeName FROM PersonType WHERE (id = "
                    + id.toString() + ")";
            Statement statement = this._db.createStatement();
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                personTypeName = results.getString("PersonTypeName");
            }
            results.close();
            statement.close();
        } catch (SQLException e) {
            throw new UnsupportedOperationException(e.getMessage());
        }
        return personTypeName;
    }

    @Override
    public void addPersonType(PersonType personType) {
        try {
            String query = "INSERT INTO PersonType (id, PersonTypeName) VALUES (?, ?)";
            PreparedStatement statement = this._db.prepareStatement(query);
            statement.setString(1, personType.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new UnsupportedOperationException(e.getMessage());
        }
    }

    @Override
    public void deletePersonType(PersonType personType) {
        try {
            String query = "DELETE FROM PersonType WHERE (ID = ?)";
            PreparedStatement statement = this._db.prepareStatement(query);
            statement.setInt(1, personType.getId());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            throw new UnsupportedOperationException(e.getMessage());
        }
    }

    @Override
    public void updatePersonType(PersonType personType) {
        try {
            String query = "UPDATE PersonType SET PersonTypeName = ? WHERE (id = ?)";
            PreparedStatement statement = this._db.prepareStatement(query);
            statement.setString(1, personType.getName());
            statement.setInt(2, personType.getId());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            throw new UnsupportedOperationException(e.getMessage());
        }
    }
}
