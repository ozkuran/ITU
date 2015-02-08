package tr.edu.itu.cs.db;

import java.io.Serializable;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;


@SuppressWarnings({ "serial", "rawtypes" })
public class BuildingTypeClassEditForm extends Form implements Serializable {

    private final BuildingTypeClassObject buildingType = new BuildingTypeClassObject();
    BuildingTypeClass btct = new BuildingTypeClass();

    @SuppressWarnings("unchecked")
    public BuildingTypeClassEditForm(String id, BuildingTypeClass btc) {
        super(id);
        // CompoundPropertyModel model = new CompoundPropertyModel(btc);
        // this.setModelObject(model);
        buildingType.BuildingTypeName = btc.getName();

        final TextField<String> name = new TextField<String>(
                "inputBuildingType", new PropertyModel(buildingType,
                        "BuildingTypeName"));
        add(name);

        btct.setId(btc.getId());
    }

    @Override
    protected void onSubmit() {
        BuildingTypeClass btc = (BuildingTypeClass) this.getModelObject();
        BuildingTypeClassCollection btcc = new BuildingTypeClassCollection();
        btct.setName(buildingType.BuildingTypeName);
        btcc.updateBuildingTypeClass(btct);
        this.setResponsePage(new BuildingTypeClassList());
    }

}
