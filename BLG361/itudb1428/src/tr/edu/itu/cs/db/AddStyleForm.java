package tr.edu.itu.cs.db;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.PropertyModel;


@SuppressWarnings({ "serial", "rawtypes" })
public class AddStyleForm extends Form {

    private final NewStyleInput newstyleinput = new NewStyleInput();

    @SuppressWarnings("unchecked")
    public AddStyleForm(String id) {
        super(id);
        // TODO Auto-generated constructor stub
        // this.add(new TextField("inname"));
        // this.add(new TextField("ininfo"));

        final TextArea<String> name = new TextArea<String>("inname",
                new PropertyModel(newstyleinput, "Stylename"));

        final TextArea<String> info = new TextArea<String>("ininfo",
                new PropertyModel(newstyleinput, "Styleinfo"));

        add(name);
        add(info);
        /*
         * add(new TextArea("inname", new PropertyModel(newstyleinput,
         * "Stylename"))); add(new TextArea("ininfo", new
         * PropertyModel(newstyleinput, "Styleinfo")));
         */
    }

    @Override
    protected void onSubmit() {
        // TODO Auto-generated method stub

        Style s = new Style(newstyleinput.Stylename, newstyleinput.Styleinfo);
        StyleClassCollection scc = new StyleClassCollection();
        scc.addStyle(s);
        this.setResponsePage(new StylesList());

    }

}
