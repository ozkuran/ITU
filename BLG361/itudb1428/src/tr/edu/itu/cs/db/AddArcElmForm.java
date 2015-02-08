package tr.edu.itu.cs.db;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.PropertyModel;


@SuppressWarnings("serial")
public class AddArcElmForm extends Form {

    private final NewStyleInput newstyleinput = new NewStyleInput();

    public AddArcElmForm(String id) {
        super(id);
        // TODO Auto-generated constructor stub
        final TextArea<String> name = new TextArea<String>("inname",
                new PropertyModel(newstyleinput, "Stylename"));

        final TextArea<String> info = new TextArea<String>("ininfo",
                new PropertyModel(newstyleinput, "Styleinfo"));

        add(name);
        add(info);
    }

    protected void onSubmit() {
        // TODO Auto-generated method stub

        ArcElm a = new ArcElm(newstyleinput.Stylename, newstyleinput.Styleinfo);
        ArcElmCollection aec = new ArcElmCollection();
        aec.addArcElm(a);
        this.setResponsePage(new ArcElmList());

    }

}
