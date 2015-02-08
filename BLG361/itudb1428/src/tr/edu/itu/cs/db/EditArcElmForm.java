package tr.edu.itu.cs.db;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.PropertyModel;


public class EditArcElmForm extends Form {
    private final NewStyleInput newstyleinput = new NewStyleInput();
    ArcElm tempa = new ArcElm();

    public EditArcElmForm(String id, ArcElm a) {
        super(id);
        // TODO Auto-generated constructor stub
        newstyleinput.Stylename = a.getName();
        newstyleinput.Styleinfo = a.getDef();

        final TextArea<String> name = new TextArea<String>("inname",
                new PropertyModel(newstyleinput, "Stylename"));

        final TextArea<String> info = new TextArea<String>("ininfo",
                new PropertyModel(newstyleinput, "Styleinfo"));

        add(name);
        add(info);

        tempa.setId(a.getId());

    }

    @Override
    protected void onSubmit() {
        // TODO Auto-generated method stub
        tempa.setName(newstyleinput.Stylename);
        tempa.setDef(newstyleinput.Styleinfo);
        ArcElmCollection aec = new ArcElmCollection();
        aec.updateArcElm(tempa);
        this.setResponsePage(new ArcElmList());

    }

}
