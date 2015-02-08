package tr.edu.itu.cs.db;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.PropertyModel;


public class EditStyleForm extends Form {

    private final NewStyleInput newstyleinput = new NewStyleInput();
    Style temps = new Style();

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public EditStyleForm(String id, Style s) {
        super(id);
        // TODO Auto-generated constructor stub

        newstyleinput.Stylename = s.getName();
        newstyleinput.Styleinfo = s.getDef();

        final TextArea<String> name = new TextArea<String>("inname",
                new PropertyModel(newstyleinput, "Stylename"));

        final TextArea<String> info = new TextArea<String>("ininfo",
                new PropertyModel(newstyleinput, "Styleinfo"));

        add(name);
        add(info);

        // add(new TextArea("inname",
        // new PropertyModel(newstyleinput, "Stylename")));

        // add(new TextArea("ininfo",
        // new PropertyModel(newstyleinput, "Styleinfo")));
        temps.setId(s.getId());
    }

    @Override
    protected void onSubmit() {
        // TODO Auto-generated method stub
        temps.setName(newstyleinput.Stylename);
        temps.setDef(newstyleinput.Styleinfo);
        StyleClassCollection scc = new StyleClassCollection();
        scc.updateStyle(temps);
        this.setResponsePage(new StylesList());

    }

}
