package tr.edu.itu.cs.db;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;


public class EditArcElmLinker extends Link {
    ArcElm a;

    public EditArcElmLinker(String id, ArcElm a) {
        // TODO Auto-generated constructor stub
        super(id);
        this.a = a;
        // TODO Auto-generated constructor stub
        Label EditStyleName = new Label(id, a.getName());
        this.add(EditStyleName);
    }

    @Override
    public void onClick() {
        // TODO Auto-generated method stub
        this.setResponsePage(new EditArcElm(a));
    }
}
