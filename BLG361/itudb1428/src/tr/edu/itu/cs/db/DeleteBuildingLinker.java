package tr.edu.itu.cs.db;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;


public class DeleteBuildingLinker extends Link {
    Building b;

    public DeleteBuildingLinker(String id, Building b) {
        // TODO Auto-generated constructor stub
        super(id);
        this.b = b;
        Label DeleteStyleName = new Label("BuildingDelete", b.getBuildingName());
        this.add(DeleteStyleName);
    }

    @Override
    public void onClick() {
        // TODO Auto-generated method stub
        BuildingCollection bc = new BuildingCollection();
        bc.DeleteBuilding(b);
        this.setResponsePage(new BuildingListWithPropertiesPage());
    }

}
