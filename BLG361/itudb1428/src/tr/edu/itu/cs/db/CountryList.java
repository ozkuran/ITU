package tr.edu.itu.cs.db;

import java.util.Date;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;


public class CountryList extends BasePage {
    public CountryList() {
        this.add(new TopMenu("topMenu"));
        Date now = new Date();
        Label dateTimeLabel = new Label("datetime", now.toString());
        this.add(dateTimeLabel);
        CountryCollection cc = new CountryCollection();
        List<Country> countries = cc.getCountries();

        ListView countryListView = new ListView("countryList", countries) {
            @Override
            protected void populateItem(ListItem item) {
                final Country country = (Country) item.getModelObject();
                item.add(new Label("countryName", country.getName().toString()));
                item.add(removeLink("remove", item));

                Link editLink = new Link("edit") {
                    @Override
                    public void onClick() {
                        setResponsePage(new CountryEdit(country));
                    }
                };
                item.add(editLink);
            }
        };
        this.add(countryListView);
    }
}
