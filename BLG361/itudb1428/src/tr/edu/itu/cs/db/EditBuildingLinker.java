package tr.edu.itu.cs.db;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;


public class EditBuildingLinker extends Link {

    Building b;

    public EditBuildingLinker(String id, Building b) {
        super(id);
        // TODO Auto-generated constructor stub
        this.b = b;
        Label EditBuildingName = new Label(id, b.getBuildingName());
        this.add(EditBuildingName);
    }

    @Override
    public void onClick() {
        // TODO Auto-generated method stub
        this.setResponsePage(new EditBuilding(b));
    }

}
