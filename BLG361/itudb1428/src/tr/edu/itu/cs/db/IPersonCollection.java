package tr.edu.itu.cs.db;

import java.util.List;


public interface IPersonCollection {
    public List<Person> getPersons();

    public void addPerson(Person person);

    public void deletePerson(Person person);

    public void updatePerson(Person person);
}
