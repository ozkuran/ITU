package tr.edu.itu.cs.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;


public class CountryCollection implements ICountryCollection {
    private Connection _db;

    public CountryCollection() {
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
    public List<Country> getCountries() {
        List<Country> countries = new LinkedList<Country>();
        try {
            String query = "SELECT id, CountryName FROM Country";
            Statement statement = this._db.createStatement();
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                Integer id = results.getInt("id");
                String name = results.getString("CountryName");
                Country country = new Country(name);
                country.setId(id);
                country.setName(name);
                countries.add(country);
            }
            results.close();
            statement.close();
        } catch (SQLException e) {
            throw new UnsupportedOperationException(e.getMessage());
        }
        return countries;
    }

    @Override
    public void addCountry(Country country) {
        try {
            String query = "INSERT INTO Country (id, CountryName) VALUES (?, ?)";
            PreparedStatement statement = this._db.prepareStatement(query);
            statement.setString(1, country.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new UnsupportedOperationException(e.getMessage());
        }
    }

    @Override
    public void deleteCountry(Country country) {
        try {
            String query = "DELETE FROM Country WHERE (ID = ?)";
            PreparedStatement statement = this._db.prepareStatement(query);
            statement.setInt(1, country.getId());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            throw new UnsupportedOperationException(e.getMessage());
        }
    }

    @Override
    public void updateCountry(Country country) {
        try {
            String query = "UPDATE Country SET countryName = ? WHERE (id = ?)";
            PreparedStatement statement = this._db.prepareStatement(query);
            statement.setString(1, country.getName());
            statement.setInt(2, country.getId());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            throw new UnsupportedOperationException(e.getMessage());
        }
    }
}
