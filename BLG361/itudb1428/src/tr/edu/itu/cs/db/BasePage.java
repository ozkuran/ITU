package tr.edu.itu.cs.db;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;


public class BasePage extends WebPage {
    public BasePage() {
        super();

    }

    public BasePage(final PageParameters params) {
        super(params);
    }

}
