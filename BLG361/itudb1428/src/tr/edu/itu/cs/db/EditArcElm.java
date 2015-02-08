package tr.edu.itu.cs.db;

import java.util.Date;

import org.apache.wicket.markup.html.basic.Label;


public class EditArcElm extends BasePage {
    public EditArcElm(ArcElm a) {
        // TODO Auto-generated constructor stub
        this.add(new TopMenu("topMenu"));
        Date now = new Date();
        Label dateTimeLabel = new Label("datetime", now.toString());
        this.add(dateTimeLabel);
        /*
         * Label NameLebel = new Label("inname", s.getName());
         * this.add(NameLebel); Label InfLebel = new Label("ininfo",
         * s.getDef()); this.add(InfLebel);
         */
        this.add(new EditArcElmForm("ArcElmForm", a));
    }
}
