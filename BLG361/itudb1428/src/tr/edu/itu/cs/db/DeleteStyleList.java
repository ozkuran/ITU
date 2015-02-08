package tr.edu.itu.cs.db;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;


@SuppressWarnings("serial")
public class DeleteStyleList extends BasePage {
    List<Style> StyleDeleteList = new LinkedList<Style>();

    public DeleteStyleList() {
        this.add(new TopMenu("topMenu"));
        Date now = new Date();
        Label dateTimeLabel = new Label("datetime", now.toString());
        this.add(dateTimeLabel);
        StyleClassCollection scc = new StyleClassCollection();
        scc.Search();
        StyleDeleteList = scc.getStyles();

        @SuppressWarnings("unchecked")
        ListView DeleteListView = new ListView("StylesDeleteList",
                StyleDeleteList) {

            @Override
            protected void populateItem(
                    @SuppressWarnings("rawtypes") ListItem arg0) {
                // TODO Auto-generated method stub
                final Style s = (Style) arg0.getModelObject();
                DeleteStyleLinker dsl = new DeleteStyleLinker("StylesDelete", s);
                // arg0.add(new Label("StylesDelete", s.getName()));
                arg0.add(dsl);

            }

        };
        this.add(DeleteListView);

    }

}
