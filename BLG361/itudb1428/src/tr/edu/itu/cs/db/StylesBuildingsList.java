package tr.edu.itu.cs.db;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;


public class StylesBuildingsList extends BasePage {
    List<Building> BldList = new LinkedList<Building>();

    @SuppressWarnings("unchecked")
    public StylesBuildingsList(Style s) {
        this.add(new TopMenu("topMenu"));
        Date now = new Date();
        Label dateTimeLabel = new Label("datetime", now.toString());
        this.add(dateTimeLabel);
        Label Hdrlbl = new Label("stylehdr", s.getName());
        this.add(Hdrlbl);

        StyleClassCollection scc = new StyleClassCollection();

        BldList = scc.SearchBuilding(s);

        @SuppressWarnings("unused")
        ListView StyleBldsListView = new ListView("BldList", BldList) {

            @Override
            protected void populateItem(ListItem arg0) {
                // TODO Auto-generated method stub
                Building b = (Building) arg0.getModelObject();
                arg0.add(new Label("Blds", b.getBuildingName()));
            }

        };
        this.add(StyleBldsListView);
    }
}
