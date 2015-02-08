package tr.edu.itu.cs.db;

import java.io.Serializable;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;


@SuppressWarnings({ "serial", "rawtypes" })
public class PersonTypeEditForm extends Form implements Serializable {

    private final PersonTypeObject personTypeO = new PersonTypeObject();
    PersonType personTypeT = new PersonType();

    @SuppressWarnings("unchecked")
    public PersonTypeEditForm(String id, PersonType personType) {
        super(id);
        // CompoundPropertyModel model = new CompoundPropertyModel(btc);
        // this.setModelObject(model);
        personTypeO.personTypeName = personType.getName();

        final TextField<String> name = new TextField<String>(
                "inputPersonTypeName", new PropertyModel(personTypeO,
                        "personTypeName"));
        add(name);

        personTypeT.setId(personType.getId());
    }

    @Override
    protected void onSubmit() {
        PersonType personType = (PersonType) this.getModelObject();
        PersonTypeCollection personTypeC = new PersonTypeCollection();
        personTypeT.setName(personTypeO.personTypeName);
        personTypeC.updatePersonType(personTypeT);
        this.setResponsePage(new PersonTypeList());
    }

}
