package tr.edu.itu.cs.db;

import java.util.Date;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;


public class PersonTypeList extends BasePage {
    public PersonTypeList() {
        this.add(new TopMenu("topMenu"));
        Date now = new Date();
        Label dateTimeLabel = new Label("datetime", now.toString());
        this.add(dateTimeLabel);
        PersonTypeCollection ptc = new PersonTypeCollection();
        List<PersonType> personTypes = ptc.getPersonTypes();

        ListView personTypeListView = new ListView("personTypeList",
                personTypes) {
            @Override
            protected void populateItem(ListItem item) {
                final PersonType personType = (PersonType) item
                        .getModelObject();
                item.add(new Label("personTypeName", personType.getName()
                        .toString()));
                item.add(removeLink("remove", item));

                Link editLink = new Link("edit") {
                    @Override
                    public void onClick() {
                        setResponsePage(new PersonTypeEdit(personType));
                    }
                };
                item.add(editLink);
            }
        };
        this.add(personTypeListView);
    }
}
