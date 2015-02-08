package tr.edu.itu.cs.db;

import java.util.Date;

import org.apache.wicket.markup.html.basic.Label;


public class ArcElmInformation extends BasePage {
    public ArcElmInformation(ArcElm a) {
        // TODO Auto-generated constructor stub
        this.add(new TopMenu("topMenu"));
        Date now = new Date();
        Label dateTimeLabel = new Label("datetime", now.toString());
        this.add(dateTimeLabel);
        Label StyleNameLabel = new Label("ArcElmName", a.getName());
        this.add(StyleNameLabel);
        Label StyleInfLabel = new Label("ArcElmInf", a.getDef());
        this.add(StyleInfLabel);
    }

}
