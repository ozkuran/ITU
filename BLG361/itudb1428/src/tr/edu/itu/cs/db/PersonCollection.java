package tr.edu.itu.cs.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;


public class PersonCollection implements IPersonCollection {
    private Connection _db;

    public PersonCollection() {
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
    public List<Person> getPersons() {
        List<Person> persons = new LinkedList<Person>();
        try {
            String query = "SELECT id, Name, PersonTypeId FROM Person";
            Statement statement = this._db.createStatement();
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                Integer id = results.getInt("Person.id");
                String name = results.getString("Person.Name");
                Integer type = results.getInt("PersonTypeId");
                Person person = new Person(name);
                person.setId(id);
                person.setName(name);
                person.setType(type);
                persons.add(person);
            }
            results.close();
            statement.close();
        } catch (SQLException e) {
            throw new UnsupportedOperationException(e.getMessage());
        }
        return persons;
    }

    @Override
    public void addPerson(Person person) {
        try {
            String query = "INSERT INTO Person (id, Name) VALUES (?, ?)";
            PreparedStatement statement = this._db.prepareStatement(query);
            statement.setString(1, person.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new UnsupportedOperationException(e.getMessage());
        }
    }

    @Override
    public void deletePerson(Person person) {
        try {
            String query = "DELETE FROM Person WHERE (ID = ?)";
            PreparedStatement statement = this._db.prepareStatement(query);
            statement.setInt(1, person.getId());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            throw new UnsupportedOperationException(e.getMessage());
        }
    }

    @Override
    public void updatePerson(Person person) {
        try {
            String query = "UPDATE Person SET Name = ? WHERE (id = ?)";
            PreparedStatement statement = this._db.prepareStatement(query);
            statement.setString(1, person.getName());
            statement.setInt(2, person.getId());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            throw new UnsupportedOperationException(e.getMessage());
        }
    }
}
