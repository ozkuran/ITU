package tr.edu.itu.cs.db;

import java.util.Date;

import org.apache.wicket.markup.html.basic.Label;


public class BuildingTypeClassAdd extends BasePage {

    public BuildingTypeClassAdd() {
        // TODO Auto-generated constructor stub
        this.add(new TopMenu("topMenu"));
        Date now = new Date();
        Label dateTimeLabel = new Label("datetime", now.toString());
        this.add(dateTimeLabel);
        this.add(new BuildingTypeClassAddForm("BuildingTypeAddForm"));
    }
}
