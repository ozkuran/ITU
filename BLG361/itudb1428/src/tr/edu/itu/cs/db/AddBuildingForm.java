package tr.edu.itu.cs.db;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.PropertyModel;


public class AddBuildingForm extends Form {

    // private final BuildingListWithPropertiesObject b=new
    // BuildingListWithPropertiesObject();

    private class temp {
        String buildingname;
        int BuildingTypeId;
        String Address;
        int CityId;
        double Latitude;
        double Longtitude;
        int StyleId;
        int Architectural_element_Id;
        int Agesid;
    }

    temp c = new temp();

    @SuppressWarnings("unused")
    public AddBuildingForm(String id) {
        super(id);
        // TODO Auto-generated constructor stub
        @SuppressWarnings("unchecked")
        final TextArea<Integer> bti = new TextArea<Integer>("bti",
                new PropertyModel(c, "BuildingTypeId"));

        @SuppressWarnings("unchecked")
        final TextArea<Integer> cityid = new TextArea<Integer>("cityid",
                new PropertyModel(c, "CityId"));

        @SuppressWarnings("unchecked")
        final TextArea<Integer> StyleId = new TextArea<Integer>("StyleId",
                new PropertyModel(c, "StyleId"));
        @SuppressWarnings("unchecked")
        final TextArea<Integer> Architectural_element_Id = new TextArea<Integer>(
                "aei", new PropertyModel(c, "Architectural_element_Id"));
        @SuppressWarnings("unchecked")
        final TextArea<Integer> Agesid = new TextArea<Integer>("ageid",
                new PropertyModel(c, "Agesid"));

        @SuppressWarnings("rawtypes")
        final TextArea<Double> Latitude = new TextArea<Double>("Latitude",
                new PropertyModel(c, "Latitude"));

        @SuppressWarnings("unchecked")
        final TextArea<Double> Longtitude = new TextArea<Double>("Longtitude",
                new PropertyModel(c, "Longtitude"));

        @SuppressWarnings("unchecked")
        final TextArea<String> buildingname = new TextArea<String>("bn",
                new PropertyModel(c, "buildingname"));

        @SuppressWarnings("unchecked")
        final TextArea<String> Address = new TextArea<String>("address",
                new PropertyModel(c, "Address"));

        add(bti);
        add(cityid);
        add(StyleId);
        add(Architectural_element_Id);
        add(Agesid);
        add(Latitude);
        add(Longtitude);
        add(buildingname);
        add(Address);
    }

    protected void onSubmit() {
        // TODO Auto-generated method stub

        Building b = new Building(c.BuildingTypeId, c.StyleId, c.CityId,
                c.Architectural_element_Id, c.Agesid, c.Latitude, c.Longtitude,
                c.buildingname, c.Address);
        BuildingCollection bc = new BuildingCollection();
        bc.AddBuilding(b);
        this.setResponsePage(new BuildingListWithPropertiesPage());

    }

}
