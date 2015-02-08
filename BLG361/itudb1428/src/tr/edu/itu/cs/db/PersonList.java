package tr.edu.itu.cs.db;

import java.util.Date;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;


public class PersonList extends BasePage {
    public PersonList() {
        this.add(new TopMenu("topMenu"));
        Date now = new Date();
        Label dateTimeLabel = new Label("datetime", now.toString());
        this.add(dateTimeLabel);
        PersonCollection pc = new PersonCollection();
        final PersonTypeCollection ptc = new PersonTypeCollection();
        List<Person> persons = pc.getPersons();
        // List<PersonType> personTypes = ptc.getPersonTypes();

        ListView personListView = new ListView("personList", persons) {
            @Override
            protected void populateItem(ListItem item) {
                final Person person = (Person) item.getModelObject();
                item.add(new Label("personName", person.getName().toString()));
                item.add(new Label("personType", ptc.getPersonTypeById(
                        person.getType()).toString()));
                item.add(removeLink("remove", item));

                Link editLink = new Link("edit") {
                    @Override
                    public void onClick() {
                        setResponsePage(new PersonEdit(person));
                    }
                };
                item.add(editLink);
            }
        };
        this.add(personListView);
    }
}
