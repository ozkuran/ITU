package tr.edu.itu.cs.db;

import java.io.Serializable;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;


@SuppressWarnings({ "serial", "rawtypes" })
public class CountryEditForm extends Form implements Serializable {

    private final CountryObject countryO = new CountryObject();
    Country countryT = new Country();

    @SuppressWarnings("unchecked")
    public CountryEditForm(String id, Country country) {
        super(id);
        // CompoundPropertyModel model = new CompoundPropertyModel(btc);
        // this.setModelObject(model);
        countryO.countryName = country.getName();

        final TextField<String> name = new TextField<String>(
                "inputCountryName", new PropertyModel(countryO, "countryName"));
        add(name);

        countryT.setId(country.getId());
    }

    @Override
    protected void onSubmit() {
        Country country = (Country) this.getModelObject();
        CountryCollection countryC = new CountryCollection();
        countryT.setName(countryO.countryName);
        countryC.updateCountry(countryT);
        this.setResponsePage(new BuildingTypeClassList());
    }

}
