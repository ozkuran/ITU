package tr.edu.itu.cs.db;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;


public class DeleteBuildingList extends BasePage {
    List<Building> BuildingDeleteList = new LinkedList<Building>();

    public DeleteBuildingList() {
        // TODO Auto-generated constructor stub
        this.add(new TopMenu("topMenu"));
        Date now = new Date();
        Label dateTimeLabel = new Label("datetime", now.toString());
        this.add(dateTimeLabel);
        BuildingCollection bc = new BuildingCollection();
        BuildingDeleteList = bc.getAllBuildings();

        @SuppressWarnings({ "unchecked", "serial" })
        ListView EditListView = new ListView("BuildingDeleteList",
                BuildingDeleteList) {

            @Override
            protected void populateItem(ListItem arg0) {
                // TODO Auto-generated method stub
                final Building b = (Building) arg0.getModelObject();
                DeleteBuildingLinker esl = new DeleteBuildingLinker(
                        "BuildingDelete", b);
                arg0.add(esl);
            }

        };
        this.add(EditListView);

    }
}
