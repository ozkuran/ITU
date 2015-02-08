package tr.edu.itu.cs.db;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;


public class EditStyleLinker extends Link {
    Style s;

    public EditStyleLinker(String id, Style s) {
        super(id);
        this.s = s;
        // TODO Auto-generated constructor stub
        Label EditStyleName = new Label(id, s.getName());
        this.add(EditStyleName);
    }

    @Override
    public void onClick() {
        // TODO Auto-generated method stub
        this.setResponsePage(new EditStyle(s));
    }

}
