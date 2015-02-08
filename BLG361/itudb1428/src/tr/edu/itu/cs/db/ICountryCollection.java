package tr.edu.itu.cs.db;

import java.util.List;


public interface ICountryCollection {
    public List<Country> getCountries();

    public void addCountry(Country country);

    public void deleteCountry(Country country);

    public void updateCountry(Country country);
}
