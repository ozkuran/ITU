package tr.edu.itu.cs.db;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;


public class TopMenu extends Panel {
    public TopMenu(String id) {
        super(id);

        Link homePageLink = new Link("home") {
            @Override
            public void onClick() {
                this.setResponsePage(new HomePage());
            }
        };
        this.add(homePageLink);

        Link countryListPage = new Link("listCountries") {
            @Override
            public void onClick() {
                this.setResponsePage(new CountryList());
            }
        };
        this.add(countryListPage);
        /*
         * Link cityListPage = new Link("listCities") {
         * 
         * @Override public void onClick() { // TODO Auto-generated method stub
         * // this.setResponsePage(new CityList()); }
         * 
         * }; this.add(cityListPage);
         */

        Link ListArcElm = new Link("listAE") {
            @Override
            public void onClick() {
                this.setResponsePage(new ArcElmList());
            }
        };
        this.add(ListArcElm);

        Link AddArcElm = new Link("addAE") {
            @Override
            public void onClick() {
                this.setResponsePage(new AddArcElm());
            }
        };
        this.add(AddArcElm);

        Link EditArcElm = new Link("EditAE") {
            @Override
            public void onClick() {
                this.setResponsePage(new EditArcElmList());
            }
        };
        this.add(EditArcElm);

        Link DeleteArcElm = new Link("DeleteAE") {
            @Override
            public void onClick() {
                this.setResponsePage(new DeleteArcElmList());
            }
        };
        this.add(DeleteArcElm);

        Link ListStyles = new Link("listStyles") {
            @Override
            public void onClick() {
                this.setResponsePage(new StylesList());
            }
        };
        this.add(ListStyles);

        Link AddStyle = new Link("addStyle") {
            @Override
            public void onClick() {
                this.setResponsePage(new AddStyle());
            }
        };
        this.add(AddStyle);

        Link DeleteStyle = new Link("DeleteStyle") {
            @Override
            public void onClick() {
                this.setResponsePage(new DeleteStyleList());
            }
        };
        this.add(DeleteStyle);

        Link EditStyle = new Link("EditStyle") {
            @Override
            public void onClick() {
                this.setResponsePage(new EditStyleList());
            }
        };
        this.add(EditStyle);

        Link AddBuilding = new Link("AddBuilding") {
            @Override
            public void onClick() {
                this.setResponsePage(new AddBuilding());
            }
        };
        this.add(AddBuilding);

        Link ListBuilding = new Link("ListBuilding") {
            @Override
            public void onClick() {
                this.setResponsePage(new BuildingListWithPropertiesPage());
            }
        };
        this.add(ListBuilding);

        Link EditBuilding = new Link("UpdateBuilding") {
            @Override
            public void onClick() {
                this.setResponsePage(new EditBuildingList());
            }
        };
        this.add(EditBuilding);

        Link DeleteBuilding = new Link("DeleteBuilding") {
            @Override
            public void onClick() {
                this.setResponsePage(new DeleteBuildingList());
            }
        };
        this.add(DeleteBuilding);

        Link buildingTypeClassListPage = new Link("listBuildingTypeClasses") {
            @Override
            public void onClick() {
                this.setResponsePage(new BuildingTypeClassList());
            }
        };
        this.add(buildingTypeClassListPage);

        Link buildingTypeClassAddPage = new Link("addBuildingTypeClasses") {
            @Override
            public void onClick() {
                this.setResponsePage(new BuildingTypeClassAdd());
            }
        };
        this.add(buildingTypeClassAddPage);

        Link personTypeListPage = new Link("listPersonTypes") {
            @Override
            public void onClick() {
                this.setResponsePage(new PersonTypeList());
            }
        };
        this.add(personTypeListPage);

        Link personListPage = new Link("listPersons") {
            @Override
            public void onClick() {
                this.setResponsePage(new PersonList());
            }
        };
        this.add(personListPage);

        Link addCountryPage = new Link("addCountry") {
            @Override
            public void onClick() {
                this.setResponsePage(new CountryAdd());
            }
        };
        this.add(addCountryPage);

        Link addPersonTypePage = new Link("addPersonType") {
            @Override
            public void onClick() {
                this.setResponsePage(new PersonTypeAdd());
            }
        };
        this.add(addPersonTypePage);

    }

}
