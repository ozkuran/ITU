package tr.edu.itu.cs.db;

import java.util.List;


public interface IPersonTypeCollection {
    public List<PersonType> getPersonTypes();

    public void addPersonType(PersonType personType);

    public void deletePersonType(PersonType personType);

    public void updatePersonType(PersonType personType);

    public String getPersonTypeById(Integer id);
}
