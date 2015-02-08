package tr.edu.itu.cs.db;

import java.util.Date;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;


public class BuildingTypeClassList extends BasePage {
    public BuildingTypeClassList() {
        this.add(new TopMenu("topMenu"));
        Date now = new Date();
        Label dateTimeLabel = new Label("datetime", now.toString());
        this.add(dateTimeLabel);
        BuildingTypeClassCollection btcc = new BuildingTypeClassCollection();
        List<BuildingTypeClass> buildingTypeClasses = btcc
                .getBuildingTypeClasses();

        ListView buildingTypeClassListView = new ListView(
                "buildingTypeClassList", buildingTypeClasses) {
            @Override
            protected void populateItem(ListItem item) {
                final BuildingTypeClass buildingTypeClass = (BuildingTypeClass) item
                        .getModelObject();
                item.add(new Label("buildingTypeClassName", buildingTypeClass
                        .getName().toString()));
                item.add(removeLink("remove", item));

                Link editLink = new Link("edit") {
                    @Override
                    public void onClick() {
                        setResponsePage(new BuildingTypeClassEdit(
                                buildingTypeClass));
                    }
                };
                item.add(editLink);
            }
        };
        this.add(buildingTypeClassListView);
    }
}
