package tr.edu.itu.cs.db;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;


public class EditArcElmList extends BasePage {
    List<ArcElm> ArcElmEditList = new LinkedList<ArcElm>();

    public EditArcElmList() {
        // TODO Auto-generated constructor stub
        this.add(new TopMenu("topMenu"));
        Date now = new Date();
        Label dateTimeLabel = new Label("datetime", now.toString());
        this.add(dateTimeLabel);
        ArcElmCollection aec = new ArcElmCollection();
        aec.Search();
        ArcElmEditList = aec.getArcElms();

        @SuppressWarnings({ "unchecked", "serial" })
        ListView EditListView = new ListView("ArcElmEditList", ArcElmEditList) {

            @Override
            protected void populateItem(ListItem arg0) {
                // TODO Auto-generated method stub
                final ArcElm a = (ArcElm) arg0.getModelObject();
                EditArcElmLinker esl = new EditArcElmLinker("ArcElmEdit", a);
                arg0.add(esl);
            }

        };
        this.add(EditListView);
    }
}
