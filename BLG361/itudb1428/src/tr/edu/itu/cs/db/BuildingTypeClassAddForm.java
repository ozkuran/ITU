package tr.edu.itu.cs.db;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.PropertyModel;


@SuppressWarnings({ "serial", "rawtypes" })
public class BuildingTypeClassAddForm extends Form {

    private final BuildingTypeClassObject buildingType = new BuildingTypeClassObject();

    @SuppressWarnings("unchecked")
    public BuildingTypeClassAddForm(String id) {
        super(id);
        add(new TextArea("inputBuildingType", new PropertyModel(buildingType,
                "inputBuildingType")));
    }

    @Override
    protected void onSubmit() {
        // TODO Auto-generated method stub
        BuildingTypeClass btc = new BuildingTypeClass(
                buildingType.BuildingTypeName);
        BuildingTypeClassCollection btcc = new BuildingTypeClassCollection();
        btcc.addBuildingTypeClass(btc);
        this.setResponsePage(new BuildingTypeClassList());

    }

}
