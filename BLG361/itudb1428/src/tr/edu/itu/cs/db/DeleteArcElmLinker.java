package tr.edu.itu.cs.db;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;


public class DeleteArcElmLinker extends Link {
    ArcElm a;

    public DeleteArcElmLinker(String id, ArcElm a) {
        // TODO Auto-generated constructor stub
        super(id);
        this.a = a;
        Label DeleteStyleName = new Label("ArcElmDelete", a.getName());
        this.add(DeleteStyleName);
    }

    @Override
    public void onClick() {
        // TODO Auto-generated method stub
        ArcElmCollection aec = new ArcElmCollection();
        aec.deleteArcElm(a);
        this.setResponsePage(new ArcElmList());

    }
}
