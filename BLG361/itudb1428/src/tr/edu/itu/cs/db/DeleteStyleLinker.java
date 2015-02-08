package tr.edu.itu.cs.db;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;


public class DeleteStyleLinker extends Link {
    Style s;

    public DeleteStyleLinker(String id, Style s) {
        super(id);
        this.s = s;
        Label DeleteStyleName = new Label("StylesDelete", s.getName());
        this.add(DeleteStyleName);
    }

    @Override
    public void onClick() {
        // TODO Auto-generated method stub
        StyleClassCollection scc = new StyleClassCollection();
        scc.deleteStyle(s);
        this.setResponsePage(new StylesList());
    }

}
