package tr.edu.itu.cs.db;

import java.io.Serializable;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;


@SuppressWarnings({ "serial", "rawtypes" })
public class PersonEditForm extends Form implements Serializable {

    private final PersonObject personO = new PersonObject();
    Person personT = new Person();

    @SuppressWarnings("unchecked")
    public PersonEditForm(String id, Person person) {
        super(id);
        // CompoundPropertyModel model = new CompoundPropertyModel(btc);
        // this.setModelObject(model);
        personO.personName = person.getName();

        final TextField<String> name = new TextField<String>("inputPersonName",
                new PropertyModel(personO, "personName"));
        add(name);

        personT.setId(person.getId());
    }

    @Override
    protected void onSubmit() {
        Person person = (Person) this.getModelObject();
        PersonCollection personC = new PersonCollection();
        personT.setName(personO.personName);
        personC.updatePerson(personT);
        this.setResponsePage(new PersonList());
    }

}
