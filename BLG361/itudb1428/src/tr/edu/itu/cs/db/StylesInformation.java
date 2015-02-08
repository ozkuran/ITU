package tr.edu.itu.cs.db;

import java.util.Date;

import org.apache.wicket.markup.html.basic.Label;


public class StylesInformation extends BasePage {
    public StylesInformation(Style s) {
        this.add(new TopMenu("topMenu"));
        Date now = new Date();
        Label dateTimeLabel = new Label("datetime", now.toString());
        this.add(dateTimeLabel);
        Label StyleNameLabel = new Label("StyleName", s.getName());
        this.add(StyleNameLabel);
        Label StyleInfLabel = new Label("StyleInf", s.getDef());
        this.add(StyleInfLabel);
    }

}
