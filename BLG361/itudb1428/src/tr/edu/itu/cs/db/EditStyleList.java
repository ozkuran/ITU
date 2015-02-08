package tr.edu.itu.cs.db;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;


public class EditStyleList extends BasePage {
    List<Style> StyleEditList = new LinkedList<Style>();

    public EditStyleList() {
        // TODO Auto-generated constructor stub
        this.add(new TopMenu("topMenu"));
        Date now = new Date();
        Label dateTimeLabel = new Label("datetime", now.toString());
        this.add(dateTimeLabel);
        StyleClassCollection scc = new StyleClassCollection();
        scc.Search();
        StyleEditList = scc.getStyles();

        @SuppressWarnings({ "unchecked", "serial" })
        ListView EditListView = new ListView("StylesEditList", StyleEditList) {

            @Override
            protected void populateItem(ListItem arg0) {
                // TODO Auto-generated method stub
                final Style s = (Style) arg0.getModelObject();
                EditStyleLinker esl = new EditStyleLinker("StylesEdit", s);
                arg0.add(esl);
            }

        };
        this.add(EditListView);
    }
}
