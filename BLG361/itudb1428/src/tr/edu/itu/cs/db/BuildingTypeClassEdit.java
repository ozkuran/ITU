package tr.edu.itu.cs.db;

import java.util.Date;

import org.apache.wicket.markup.html.basic.Label;


public class BuildingTypeClassEdit extends BasePage {

    public BuildingTypeClassEdit(BuildingTypeClass btc) {

        // TODO Auto-generated constructor stub
        this.add(new TopMenu("topMenu"));
        Date now = new Date();
        Label dateTimeLabel = new Label("datetime", now.toString());
        this.add(dateTimeLabel);
        this.add(new BuildingTypeClassEditForm("BuildingTypeClassEditForm", btc));
    }
}
