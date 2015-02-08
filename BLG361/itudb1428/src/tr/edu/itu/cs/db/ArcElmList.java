package tr.edu.itu.cs.db;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;


@SuppressWarnings("serial")
public class ArcElmList extends BasePage {
    List<ArcElm> aeList = new LinkedList<ArcElm>();

    public ArcElmList() {
        this.add(new TopMenu("topMenu"));
        Date now = new Date();
        Label dateTimeLabel = new Label("datetime", now.toString());
        this.add(dateTimeLabel);
        ArcElmCollection aec = new ArcElmCollection();
        aec.Search();
        aeList = aec.getArcElms();

        // Style w = null;
        // w.setName("stil");
        // scc.addStyle(w);

        @SuppressWarnings("unchecked")
        ListView StyleListView = new ListView("ArcElmsList", aeList) {

            @Override
            protected void populateItem(ListItem arg0) {
                // TODO Auto-generated method stub
                final ArcElm a = (ArcElm) arg0.getModelObject();
                arg0.add(new Label("ArcElms", a.getName()+" (id:"+a.getId()+") "));
                Link ListInf = new Link("Inf") {
                    @Override
                    public void onClick() {
                        this.setResponsePage(new ArcElmInformation(a));
                    }
                };
                Link ListBld = new Link("Bld") {
                    @Override
                    public void onClick() {
                        this.setResponsePage(new ArcElmBuildingsList(a));
                    }
                };
                arg0.add(ListInf);
                arg0.add(ListBld);
            }
        };
        this.add(StyleListView);
    }
}
