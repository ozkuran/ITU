package tr.edu.itu.cs.db;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.PropertyModel;


@SuppressWarnings({ "serial", "rawtypes" })
public class PersonTypeAddForm extends Form {

    private final PersonTypeObject personType = new PersonTypeObject();

    @SuppressWarnings("unchecked")
    public PersonTypeAddForm(String id) {
        super(id);
        add(new TextArea("inputPersonType", new PropertyModel(personType,
                "inputPersonType")));
    }

    @Override
    protected void onSubmit() {
        // TODO Auto-generated method stub
        PersonType pt = new PersonType(personType.personTypeName);
        PersonTypeCollection ptc = new PersonTypeCollection();
        ptc.addPersonType(pt);
        this.setResponsePage(new PersonTypeList());

    }

}
