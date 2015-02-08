package tr.edu.itu.cs.db;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;


public class BuildingListWithPropertiesPage extends BasePage {
    List<BuildingListWithPropertiesObject> blwp = new LinkedList<BuildingListWithPropertiesObject>();

    public BuildingListWithPropertiesPage() {
        // TODO Auto-generated constructor stub
        this.add(new TopMenu("topMenu"));
        Date now = new Date();
        Label dateTimeLabel = new Label("datetime", now.toString());
        this.add(dateTimeLabel);
        BuildingCollection bc = new BuildingCollection();
        blwp = bc.getAllBuildingsPropList();

        @SuppressWarnings({ "unused", "unchecked" })
        ListView StyleBldsListView = new ListView("BuildingsList", blwp) {

            @Override
            protected void populateItem(ListItem arg0) {
                // TODO Auto-generated method stub
                BuildingListWithPropertiesObject b = (BuildingListWithPropertiesObject) arg0
                        .getModelObject();
                arg0.add(new Label("BuildingName", b.BuildingName));
                arg0.add(new Label("BuildingType", b.BuildingType));
                arg0.add(new Label("Adress", b.Adress));
                arg0.add(new Label("City", b.City));
                arg0.add(new Label("Style", b.Style));
                arg0.add(new Label("architectural_elements",
                        b.architectural_elements));
                arg0.add(new Label("age", b.age));
                arg0.add(new Label("Latitude", b.Latitude));
                arg0.add(new Label("Longtitude", b.Longtitude));
            }

        };

        this.add(StyleBldsListView);
    }
}
