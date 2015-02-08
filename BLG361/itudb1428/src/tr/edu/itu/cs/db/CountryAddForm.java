package tr.edu.itu.cs.db;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.PropertyModel;


@SuppressWarnings({ "serial", "rawtypes" })
public class CountryAddForm extends Form {

    private final CountryObject countryO = new CountryObject();

    @SuppressWarnings("unchecked")
    public CountryAddForm(String id) {
        super(id);
        add(new TextArea("inputCountryName", new PropertyModel(countryO,
                "countryName")));
    }

    @Override
    protected void onSubmit() {
        // TODO Auto-generated method stub
        Country country = new Country(countryO.countryName);
        CountryCollection countryC = new CountryCollection();
        countryC.addCountry(country);
        this.setResponsePage(new CountryList());

    }

}
