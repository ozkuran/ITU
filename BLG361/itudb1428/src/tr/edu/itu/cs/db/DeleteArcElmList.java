package tr.edu.itu.cs.db;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;


public class DeleteArcElmList extends BasePage {
    List<ArcElm> ArcElmDeleteList = new LinkedList<ArcElm>();

    public DeleteArcElmList() {
        // TODO Auto-generated constructor stub
        this.add(new TopMenu("topMenu"));
        Date now = new Date();
        Label dateTimeLabel = new Label("datetime", now.toString());
        this.add(dateTimeLabel);
        ArcElmCollection aec = new ArcElmCollection();
        aec.Search();
        ArcElmDeleteList = aec.getArcElms();

        @SuppressWarnings("unchecked")
        ListView DeleteListView = new ListView("ArcElmDeleteList",
                ArcElmDeleteList) {

            @Override
            protected void populateItem(
                    @SuppressWarnings("rawtypes") ListItem arg0) {
                // TODO Auto-generated method stub
                final ArcElm a = (ArcElm) arg0.getModelObject();
                DeleteArcElmLinker dal = new DeleteArcElmLinker("ArcElmDelete",
                        a);
                // arg0.add(new Label("StylesDelete", s.getName()));
                arg0.add(dal);

            }

        };
        this.add(DeleteListView);
    }
}
