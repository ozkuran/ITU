package tr.edu.itu.cs.db;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;


public class EditBuildingList extends BasePage {
    List<Building> BuildingEditList = new LinkedList<Building>();

    public EditBuildingList() {
        // TODO Auto-generated constructor stub
        this.add(new TopMenu("topMenu"));
        Date now = new Date();
        Label dateTimeLabel = new Label("datetime", now.toString());
        this.add(dateTimeLabel);
        BuildingCollection bc = new BuildingCollection();
        BuildingEditList = bc.getAllBuildings();

        @SuppressWarnings({ "unchecked", "serial" })
        ListView EditListView = new ListView("BuildingEditList",
                BuildingEditList) {

            @Override
            protected void populateItem(ListItem arg0) {
                // TODO Auto-generated method stub
                final Building b = (Building) arg0.getModelObject();
                EditBuildingLinker esl = new EditBuildingLinker("BuildingEdit",
                        b);
                arg0.add(esl);
            }

        };
        this.add(EditListView);
    }
}
