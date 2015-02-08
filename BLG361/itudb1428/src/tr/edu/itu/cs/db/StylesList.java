package tr.edu.itu.cs.db;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;


@SuppressWarnings("serial")
public class StylesList extends BasePage {
    List<Style> StyleList = new LinkedList<Style>();

    public StylesList() {
        this.add(new TopMenu("topMenu"));
        Date now = new Date();
        Label dateTimeLabel = new Label("datetime", now.toString());
        this.add(dateTimeLabel);
        StyleClassCollection scc = new StyleClassCollection();
        scc.Search();
        StyleList = scc.getStyles();

        // Style w = null;
        // w.setName("stil");
        // scc.addStyle(w);

        @SuppressWarnings("unchecked")
        ListView StyleListView = new ListView("StylesList", StyleList) {

            @Override
            protected void populateItem(ListItem arg0) {
                // TODO Auto-generated method stub
                final Style s = (Style) arg0.getModelObject();
                arg0.add(new Label("Styles", s.getName() + " (id:" + s.getId()
                        + ") "));
                Link ListInf = new Link("Inf") {
                    @Override
                    public void onClick() {
                        this.setResponsePage(new StylesInformation(s));
                    }
                };
                Link ListBld = new Link("Bld") {
                    @Override
                    public void onClick() {
                        this.setResponsePage(new StylesBuildingsList(s));
                    }
                };
                arg0.add(ListInf);
                arg0.add(ListBld);
            }
        };
        this.add(StyleListView);
    }
}
