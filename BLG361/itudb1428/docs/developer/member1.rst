Parts Implemented by Mahmut Ali ÖZKURAN
=======================================

BasePage Class
--------------

BasePage class could be used for include properties that included all pages in the application.


Methods
^^^^^^^

* **public BasePage():** Default constructor method. 
* **public BasePage(final PageParameters params):** Constructor with parameters method.
          
          
  ..  code-block:: java  
  
      package tr.edu.itu.cs.db;
      
      import org.apache.wicket.markup.html.WebPage;
      import org.apache.wicket.request.mapper.parameter.PageParameters;
      
      
      public class BasePage extends WebPage {
          public BasePage() {
              super();
      
          }
      
          public BasePage(final PageParameters params) {
              super(params);
          }
      
      }


BuildingTypeClass Class
-----------------------

BuildingTypeClass class is definition of BuildingType Obejcts. It has constructor, setter and getter methods for elements of the object. 

Variables
^^^^^^^^^

* **Integer _id:** Identificatipon valuse of the object.
* **String _buildingTypeClassName:** Name of the object.


Methods
^^^^^^^

* **public BuildingTypeClass():** Constructor method which creates an empty object.
* **public BuildingTypeClass(String name):** Constructor method which creates an object with given Name. 
* **void setId(Integer id):** Sets object id.
* **Integer getId():** Gets object id.
* **void setName(String name):** Sets object name.
* **String getName():** Gets object name.
 
 
  ..  code-block:: java  

      package tr.edu.itu.cs.db;
      
      public class BuildingTypeClass {
      
          private Integer _id = null;
          private String _buildingTypeClassName = null;
      
          public BuildingTypeClass() {
      
          }
      
          public BuildingTypeClass(String name) {
              this.setName(name);
          }
      
          public void setId(Integer id) {
              this._id = id;
          }
      
          public Integer getId() {
              return this._id;
          }
      
          public void setName(String name) {
              this._buildingTypeClassName = name;
          }
      
          public String getName() {
              return this._buildingTypeClassName;
          }
      }


IBuildingTypeClassCollection Interface
--------------------------------------

IBuildingTypeClassCollection is definition of the Interface for the BuildingTypeClassCollection class. It defines what methods are needed for classes are which implemented from IBuildingTypeClassCollection.


Methods
^^^^^^^

* **getBuildingTypeClasses():** which loads elements of collection from Database.
* **addBuildingTypeClass(BuildingTypeClass buildingTypeClass):** adds given buildingTypeClass to collection.
* **deleteBuildingTypeClass(BuildingTypeClass buildingTypeClass):** deletes given buildingTypeClass from collection.
* **updateBuildingTypeClass(BuildingTypeClass buildingTypeClass):** updates given buildingTypeClass in the collection.



  ..  code-block:: java 
  
  
      package tr.edu.itu.cs.db;
      
      import java.util.List;
      
      
      public interface IBuildingTypeClassCollection {
          public List<BuildingTypeClass> getBuildingTypeClasses();
      
          public void addBuildingTypeClass(BuildingTypeClass buildingTypeClass);
      
          public void deleteBuildingTypeClass(BuildingTypeClass buildingTypeClass);
      
          public void updateBuildingTypeClass(BuildingTypeClass buildingTypeClass);
      }



BuildingTypeClassCollection Class
---------------------------------

BuildingTypeClassCollection class is definition of Collection of BuildingType Objects. It has constructor, setter and getter methods for all elements of the object.

Variables
^^^^^^^^^

* **Connection _db:** Connection to database.

Methods
^^^^^^^

* **BuildingTypeClassCollection():** Constructor method which makes connection to database and assigns this coonnection to *_db* variable. In any problem it throws SQLException.
* **getBuildingTypeClasses():** Loads elements of collection from Database.
* **addBuildingTypeClass(BuildingTypeClass buildingTypeClass):** Adds given buildingTypeClass to collection.
* **deleteBuildingTypeClass(BuildingTypeClass buildingTypeClass):** Deletes given buildingTypeClass from collection.
* **updateBuildingTypeClass(BuildingTypeClass buildingTypeClass):** Updates given buildingTypeClass in the collection.



  ..  code-block:: java  
      
      
      package tr.edu.itu.cs.db;

      import java.sql.Connection;
      import java.sql.DriverManager;
      import java.sql.PreparedStatement;
      import java.sql.ResultSet;
      import java.sql.SQLException;
      import java.sql.Statement;
      import java.util.LinkedList;
      import java.util.List;
      
      
      public class BuildingTypeClassCollection implements
              IBuildingTypeClassCollection {
      
          private Connection _db;
      
          public BuildingTypeClassCollection() {
              try {
                  Class.forName("com.mysql.jdbc.Driver");
              } catch (java.lang.ClassNotFoundException e) {
                  System.out.println(e.getMessage());
              }
              try {
                  this._db = DriverManager
                          .getConnection(
                                  "************",
                                  "************", "*********");
              } catch (java.sql.SQLException e) {
                  System.out.println(e.getMessage());
              }
          }
      
          @Override
          public List<BuildingTypeClass> getBuildingTypeClasses() {
              List<BuildingTypeClass> buildingTypeClasses = new LinkedList<BuildingTypeClass>();
              try {
                  String query = "SELECT id, BuildingTypeClassName FROM BuildingTypeClass";
                  Statement statement = this._db.createStatement();
                  ResultSet results = statement.executeQuery(query);
                  while (results.next()) {
                      Integer id = results.getInt("id");
                      String name = results.getString("BuildingTypeClassName");
                      BuildingTypeClass buildingTypeClass = new BuildingTypeClass(
                              name);
                      buildingTypeClass.setId(id);
                      buildingTypeClass.setName(name);
                      buildingTypeClasses.add(buildingTypeClass);
                  }
                  results.close();
                  statement.close();
              } catch (SQLException e) {
                  throw new UnsupportedOperationException(e.getMessage());
              }
              return buildingTypeClasses;
          }
      
          @Override
          public void addBuildingTypeClass(BuildingTypeClass buildingTypeClass) {
              try {
                  String query = "INSERT INTO BuildingTypeClass (BuildingTypeClassName) VALUES (?)";
                  PreparedStatement statement = this._db.prepareStatement(query);
                  statement.setString(1, buildingTypeClass.getName());
                  statement.executeUpdate();
              } catch (SQLException e) {
                  throw new UnsupportedOperationException(e.getMessage());
              }
          }
      
          @Override
          public void deleteBuildingTypeClass(BuildingTypeClass buildingTypeClass) {
              try {
                  String query = "DELETE FROM BuildingTypeClass WHERE (id = ?)";
                  PreparedStatement statement = this._db.prepareStatement(query);
                  statement.setInt(1, buildingTypeClass.getId());
                  statement.executeUpdate();
                  statement.close();
              } catch (SQLException e) {
                  throw new UnsupportedOperationException(e.getMessage());
              }
          }
      
          @Override
          public void updateBuildingTypeClass(BuildingTypeClass buildingTypeClass) {
              try {
                  String query = "UPDATE BuildingTypeClass SET BuildingTypeClassName = ? WHERE (id = ?)";
                  PreparedStatement statement = this._db.prepareStatement(query);
                  statement.setString(1, buildingTypeClass.getName());
                  statement.setInt(2, buildingTypeClass.getId());
                  statement.executeUpdate();
                  statement.close();
              } catch (SQLException e) {
                  throw new UnsupportedOperationException(e.getMessage());
              }
          }
      
      }
      

BuildingTypeClassAdd Class
--------------------------

BuildingTypeClassAdd class is definition of BuildingType Object add page. It has only constructor *BuildingTypeClassAdd()* which have initializes page.

Methods
^^^^^^^

* **public BuildingTypeClassAdd():** Constructor method which initializes *BuildingTypeClass* addition page and adds form to input new *BuildingTypeClass*.

      
  ..  code-block:: java       
      
      package tr.edu.itu.cs.db;
      
      import java.util.Date;
      
      import org.apache.wicket.markup.html.basic.Label;
      
      
      public class BuildingTypeClassAdd extends BasePage {
      
          public BuildingTypeClassAdd() {
              // TODO Auto-generated constructor stub
              this.add(new TopMenu("topMenu"));
              Date now = new Date();
              Label dateTimeLabel = new Label("datetime", now.toString());
              this.add(dateTimeLabel);
              this.add(new BuildingTypeClassAddForm("BuildingTypeAddForm"));
          }
      }
  
BuildingTypeClassAddForm Class
------------------------------

BuildingTypeClassAddForm class is definition of a Form based on *org.apache.wicket.markup.html.form.Form* which used to add new BuildingTypeClass. It has only constructor *BuildingTypeClassAddForm(String id)* which have initializes page and void *onSubmit()* method to process given input.

Variables
^^^^^^^^^

* **BuildingTypeClassObject buildingType:** Temporary variable which used for store given data in submit. 


Methods
^^^^^^^

* **public BuildingTypeClassAddForm(String id):** Constructor method which initializes *BuildingTypeClassAddForm* form and add *TextArea* for input *BuildingType* variable.
* **void onSubmit():** Method which processes given data in Form and submits it to *BuildingTypeClassCollection* which saves that data to Database.
  
      
  ..  code-block:: java       
      
      
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
      
      
BuildingTypeClassEdit Class
---------------------------

BuildingTypeClassEdit class is definition of BuildingType Object edit page. It has only constructor *BuildingTypeClassEdit()* which have initializes page.

Methods
^^^^^^^

* **public BuildingTypeClassEdit():** Constructor method which initializes *BuildingTypeClass* edit page and adds form to edit given *BuildingTypeClass*.

      
  ..  code-block:: java       
      
      package tr.edu.itu.cs.db;
      
      import java.util.Date;
      
      import org.apache.wicket.markup.html.basic.Label;
      
      
      public class BuildingTypeClassEdit extends BasePage {
      
          public BuildingTypeClassEdit(BuildingTypeClass btc) {
      
              // TODO Auto-generated constructor stub
              this.add(new TopMenu("topMenu"));
              Date now = new Date();
              Label dateTimeLabel = new Label("datetime", now.toString());
              this.add(dateTimeLabel);
              this.add(new BuildingTypeClassEditForm("BuildingTypeClassEditForm", btc));
          }
      }

  
BuildingTypeClassEditForm Class
-------------------------------

BuildingTypeClassEditForm class is definition of a Form based on *org.apache.wicket.markup.html.form.Form* which used to edit BuildingTypeClass. It has only constructor *BuildingTypeClassEditForm(String id, BuildingTypeClass btc)* which have initializes page and void *onSubmit()* method to process given input.

Variables
^^^^^^^^^

* **BuildingTypeClassObject buildingType:** Temporary variable which used for store given data in submit. 


Methods
^^^^^^^

* **public BuildingTypeClassEditForm(String id):** Constructor method which initializes *BuildingTypeClassEditForm* form and add *TextArea* for input *BuildingType* variable.
* **void onSubmit():** Method which processes given data in Form and submits it to *BuildingTypeClassCollection* which saves that data to Database.
  
      
  ..  code-block:: java       
      
      
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

      
BuildingTypeClassList Class
---------------------------

BuildingTypeClassList class is definition of a page which lists BuildingTypeClasses. It has only constructor *BuildingTypeClassList()* which have initializes page.


Methods
^^^^^^^

* **public BuildingTypeClassList():** Constructor method which initializes *BuildingTypeClassList* form.

      
  ..  code-block:: java       
      
      
      package tr.edu.itu.cs.db;
      
      import java.util.Date;
      import java.util.List;
      
      import org.apache.wicket.markup.html.basic.Label;
      import org.apache.wicket.markup.html.link.Link;
      import org.apache.wicket.markup.html.list.ListItem;
      import org.apache.wicket.markup.html.list.ListView;
      
      
      public class BuildingTypeClassList extends BasePage {
          public BuildingTypeClassList() {
              this.add(new TopMenu("topMenu"));
              Date now = new Date();
              Label dateTimeLabel = new Label("datetime", now.toString());
              this.add(dateTimeLabel);
              BuildingTypeClassCollection btcc = new BuildingTypeClassCollection();
              List<BuildingTypeClass> buildingTypeClasses = btcc
                      .getBuildingTypeClasses();
      
              ListView buildingTypeClassListView = new ListView(
                      "buildingTypeClassList", buildingTypeClasses) {
                  @Override
                  protected void populateItem(ListItem item) {
                      final BuildingTypeClass buildingTypeClass = (BuildingTypeClass) item
                              .getModelObject();
                      item.add(new Label("buildingTypeClassName", buildingTypeClass
                              .getName().toString()));
                      item.add(removeLink("remove", item));
      
                      Link editLink = new Link("edit") {
                          @Override
                          public void onClick() {
                              setResponsePage(new BuildingTypeClassEdit(
                                      buildingTypeClass));
                          }
                      };
                      item.add(editLink);
                  }
              };
              this.add(buildingTypeClassListView);
          }
      }


BuildingTypeClassObject Class
-----------------------------

BuildingTypeClassObject class is an object definition which used for serialized operations.


Variable
^^^^^^^^

* **String BuildingTypeName:** A string variable to store *BuildingTypeName* of a *BuildingTypeClass* object.

      
  ..  code-block:: java       
      
      
      package tr.edu.itu.cs.db;
      
      import java.io.Serializable;
      
      
      public class BuildingTypeClassObject implements Serializable {
          public String BuildingTypeName = "";
      }

      
Country Class
-------------

Country class is definition of Country Objects. It has constructor, setter and getter methods for elements of the object. 

Variables
^^^^^^^^^

* **Integer _id:** Identificatipon valuse of the object.
* **String _countryName:** Name of the object.


Methods
^^^^^^^

* **public Country():** Constructor method which creates an empty object.
* **public Country(String name):** Constructor method which creates an object with given Name. 
* **void setId(Integer id):** Sets object id.
* **Integer getId():** Gets object id.
* **void setName(String name):** Sets object name.
* **String getName():** Gets object name.
 
 
  ..  code-block:: java  

      package tr.edu.itu.cs.db;
      
      public class Country extends DatabaseManager {
      
          private Integer _id = null;
          private String _countryName = null;
      
          public Country() {
      
          }
      
          public Country(String name) {
              this.setName(name);
          }
      
          public void setId(Integer id) {
              this._id = id;
          }
      
          public Integer getId() {
              return this._id;
          }
      
          public void setName(String name) {
              this._countryName = name;
          }
      
          public String getName() {
              return this._countryName;
          }
      }
            
            
            
CountryAdd Class
----------------

CountryAdd class is definition of *Country* Object add page. It has only constructor *CountryAdd()* which have initializes page.

Methods
^^^^^^^

* **public CountryAdd():** Constructor method which initializes *CountryAdd* addition page and adds form to input new *Country*.
  
      
  ..  code-block:: java       
      
      
      package tr.edu.itu.cs.db;
      
      import java.util.Date;
      
      import org.apache.wicket.markup.html.basic.Label;
      
      
      public class CountryAdd extends BasePage {
      
          public CountryAdd() {
              // TODO Auto-generated constructor stub
              this.add(new TopMenu("topMenu"));
              Date now = new Date();
              Label dateTimeLabel = new Label("datetime", now.toString());
              this.add(dateTimeLabel);
              this.add(new CountryAddForm("CountryAddForm"));
          }
      }
      

CountryAddForm Class
--------------------

CountryAddForm class is definition of a Form based on *org.apache.wicket.markup.html.form.Form* which used to add new *Country*. It has only constructor *CountryAddForm(String id)* which have initializes page and void *onSubmit()* method to process given input.

Variables
^^^^^^^^^

* **BuildingTypeClassObject buildingType:** Temporary variable which used for store given data in submit. 


Methods
^^^^^^^

* **public CountryAddForm(String id):** Constructor method which initializes *BuildingTypeClassAddForm* form and add *TextArea* for input *BuildingType* variable.
* **void onSubmit():** Method which processes given data in Form and submits it to *CountryCollection* which saves that data to Database.
  
      
  ..  code-block:: java       
      
      
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

ICountryCollection Interface
----------------------------

*ICountryCollection* is definition of the Interface for the *CountryCollection* class. It defines what methods are needed for classes are which implemented from *ICountryCollection*.


Methods
^^^^^^^

* **List<Country> getCountries():** which loads elements of collection from Database.
* **void addCountry(Country country):** adds given *Country* to collection.
* **void deleteCountry(Country country):** deletes given *Country* from collection.
* **public void updateCountry(Country country)** updates given *Country* in the collection.



  ..  code-block:: java 
  
  
      package tr.edu.itu.cs.db;
      
      import java.util.List;
      
      
      public interface ICountryCollection {
          public List<Country> getCountries();
      
          public void addCountry(Country country);
      
          public void deleteCountry(Country country);
      
          public void updateCountry(Country country);
      }


CountryCollection Class
-----------------------

CountryCollection class is definition of Collection of *Country* Objects. It has constructor, setter and getter methods for all elements of the object.

Variables
^^^^^^^^^

* **Connection _db:** Connection to database.

Methods
^^^^^^^

* **CountryCollection():** Constructor method which makes connection to database and assigns this coonnection to *_db* variable. In any problem it throws SQLException.
* **List<Country> getCountries():** which loads elements of collection from Database.
* **void addCountry(Country country):** adds given *Country* to collection.
* **void deleteCountry(Country country):** deletes given *Country* from collection.
* **public void updateCountry(Country country)** updates given *Country* in the collection.



  ..  code-block:: java  
      
      
      package tr.edu.itu.cs.db;
      
      import java.sql.Connection;
      import java.sql.DriverManager;
      import java.sql.PreparedStatement;
      import java.sql.ResultSet;
      import java.sql.SQLException;
      import java.sql.Statement;
      import java.util.LinkedList;
      import java.util.List;
      
      
      public class CountryCollection implements ICountryCollection {
          private Connection _db;
      
          public CountryCollection() {
              try {
                  Class.forName("com.mysql.jdbc.Driver");
              } catch (java.lang.ClassNotFoundException e) {
                  System.out.println(e.getMessage());
              }
              try {
                  this._db = DriverManager
                          .getConnection(
                                  "*********",
                                  "*********", "*******");
              } catch (java.sql.SQLException e) {
                  System.out.println(e.getMessage());
              }
          }
      
          @Override
          public List<Country> getCountries() {
              List<Country> countries = new LinkedList<Country>();
              try {
                  String query = "SELECT id, CountryName FROM Country";
                  Statement statement = this._db.createStatement();
                  ResultSet results = statement.executeQuery(query);
                  while (results.next()) {
                      Integer id = results.getInt("id");
                      String name = results.getString("CountryName");
                      Country country = new Country(name);
                      country.setId(id);
                      country.setName(name);
                      countries.add(country);
                  }
                  results.close();
                  statement.close();
              } catch (SQLException e) {
                  throw new UnsupportedOperationException(e.getMessage());
              }
              return countries;
          }
      
          @Override
          public void addCountry(Country country) {
              try {
                  String query = "INSERT INTO Country (id, CountryName) VALUES (?, ?)";
                  PreparedStatement statement = this._db.prepareStatement(query);
                  statement.setString(1, country.getName());
                  statement.executeUpdate();
              } catch (SQLException e) {
                  throw new UnsupportedOperationException(e.getMessage());
              }
          }
      
          @Override
          public void deleteCountry(Country country) {
              try {
                  String query = "DELETE FROM Country WHERE (ID = ?)";
                  PreparedStatement statement = this._db.prepareStatement(query);
                  statement.setInt(1, country.getId());
                  statement.executeUpdate();
                  statement.close();
              } catch (SQLException e) {
                  throw new UnsupportedOperationException(e.getMessage());
              }
          }
      
          @Override
          public void updateCountry(Country country) {
              try {
                  String query = "UPDATE Country SET countryName = ? WHERE (id = ?)";
                  PreparedStatement statement = this._db.prepareStatement(query);
                  statement.setString(1, country.getName());
                  statement.setInt(2, country.getId());
                  statement.executeUpdate();
                  statement.close();
              } catch (SQLException e) {
                  throw new UnsupportedOperationException(e.getMessage());
              }
          }
      }

CountryEdit Class
-----------------

CountryEdit class is definition of Country Object edit page. It has only constructor *CountryEdit()* which have initializes page.

Methods
^^^^^^^

* **public CountryEdit():** Constructor method which initializes *CountryEdit* edit page and adds form to edit given *Country*.

      
  ..  code-block:: java       
      
      package tr.edu.itu.cs.db;
      
      import java.util.Date;
      
      import org.apache.wicket.markup.html.basic.Label;
      
      
      public class CountryEdit extends BasePage {
          public CountryEdit(Country country) {
      
              // TODO Auto-generated constructor stub
              this.add(new TopMenu("topMenu"));
              Date now = new Date();
              Label dateTimeLabel = new Label("datetime", now.toString());
              this.add(dateTimeLabel);
              this.add(new CountryEditForm("CountryEditForm", country));
          }
      
      }


  
CountryEditForm Class
---------------------

CountryEditForm class is definition of a Form based on *org.apache.wicket.markup.html.form.Form* which used to edit *Country*. It has only constructor *CountryEditForm(String id, Country country)* which have initializes page and void *onSubmit()* method to process given input.

Variables
^^^^^^^^^

* **CountryObject countryO:** Temporary variable which used for store given data in submit. 


Methods
^^^^^^^

* **public CountryEditForm(String id):** Constructor method which initializes *CountryEditForm* form and add *TextArea* for input *Country* variable.
* **void onSubmit():** Method which processes given data in Form and submits it to *CountryCollection* which saves that data to Database.
  
      
  ..  code-block:: java       
      
      
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

      
CountryList Class
-----------------

CountryList class is definition of a page which lists Countries. It has only constructor *CountryList()* which have initializes page.


Methods
^^^^^^^

* **public CountryList():** Constructor method which initializes *CountryList* form.

      
  ..  code-block:: java       
      
      
      package tr.edu.itu.cs.db;
      
      import java.util.Date;
      import java.util.List;
      
      import org.apache.wicket.markup.html.basic.Label;
      import org.apache.wicket.markup.html.link.Link;
      import org.apache.wicket.markup.html.list.ListItem;
      import org.apache.wicket.markup.html.list.ListView;
      
      
      public class CountryList extends BasePage {
          public CountryList() {
              this.add(new TopMenu("topMenu"));
              Date now = new Date();
              Label dateTimeLabel = new Label("datetime", now.toString());
              this.add(dateTimeLabel);
              CountryCollection cc = new CountryCollection();
              List<Country> countries = cc.getCountries();
      
              ListView countryListView = new ListView("countryList", countries) {
                  @Override
                  protected void populateItem(ListItem item) {
                      final Country country = (Country) item.getModelObject();
                      item.add(new Label("countryName", country.getName().toString()));
                      item.add(removeLink("remove", item));
      
                      Link editLink = new Link("edit") {
                          @Override
                          public void onClick() {
                              setResponsePage(new CountryEdit(country));
                          }
                      };
                      item.add(editLink);
                  }
              };
              this.add(countryListView);
          }
      }



CountryObject Class
-------------------

CountryObject class is an object definition which used for serialized operations.


Variable
^^^^^^^^

* **String countryName:** A string variable to store *CountryName* of a *Country* object.

      
  ..  code-block:: java       
      
      
      package tr.edu.itu.cs.db;
      
      public class CountryObject {
          public String countryName = "";
      }


IPersonTypeCollection Interface
-------------------------------

IPersonTypeCollection is definition of the Interface for the *PersonTypeCollection* class. It defines what methods are needed for classes are which implemented from IPersonTypeCollection.


Methods
^^^^^^^

* **public List<PersonType> getPersonTypes():** which loads elements of collection from Database.
* **public void addPersonType(PersonType personType):** adds given *personType* to collection.
* **public void deletePersonType(PersonType personType):** deletes given *personType* from collection.
* **public void updatePersonType(PersonType personType):** updates given *personType* in the collection.



  ..  code-block:: java 
  
      package tr.edu.itu.cs.db;
      
      import java.util.List;
      
      
      public interface IPersonTypeCollection {
          public List<PersonType> getPersonTypes();
      
          public void addPersonType(PersonType personType);
      
          public void deletePersonType(PersonType personType);
      
          public void updatePersonType(PersonType personType);
      
          public String getPersonTypeById(Integer id);
      }


PersonType Class
----------------

PersonType class is definition of PersonType Objects. It has constructor, setter and getter methods for elements of the object. 

Variables
^^^^^^^^^

* **Integer _id:** Identificatipon valuse of the object.
* **String _personTypeName:** Name of the object.


Methods
^^^^^^^

* **public PersonType():** Constructor method which creates an empty object.
* **public PersonType(String name):** Constructor method which creates an object with given Name. 
* **void setId(Integer id):** Sets object id.
* **Integer getId():** Gets object id.
* **void setName(String name):** Sets object name.
* **String getName():** Gets object name.
 
 
  ..  code-block:: java  

      package tr.edu.itu.cs.db;
      
      public class PersonType extends DatabaseManager {
      
          private Integer _id = null;
          private String _personTypeName = null;
      
          public PersonType() {
      
          }
      
          public PersonType(String name) {
              this.setName(name);
          }
      
          public void setId(Integer id) {
              this._id = id;
          }
      
          public Integer getId() {
              return this._id;
          }
      
          public void setName(String name) {
              this._personTypeName = name;
          }
      
          public String getName() {
              return this._personTypeName;
          }
      }
            
            
            
PersonTypeAdd Class
-------------------

PersonTypeAdd class is definition of *PersonType* Object add page. It has only constructor *PersonTypeAdd()* which have initializes page.

Methods
^^^^^^^

* **public PersonTypeAdd():** Constructor method which initializes *PersonTypeAdd* addition page and adds form to input new *PersonType*.
  
      
  ..  code-block:: java       
      
      
      package tr.edu.itu.cs.db;
      
      import java.util.Date;
      
      import org.apache.wicket.markup.html.basic.Label;
      
      
      public class PersonTypeAdd extends BasePage {
      
          public PersonTypeAdd() {
              // TODO Auto-generated constructor stub
              this.add(new TopMenu("topMenu"));
              Date now = new Date();
              Label dateTimeLabel = new Label("datetime", now.toString());
              this.add(dateTimeLabel);
              this.add(new PersonTypeAddForm("PersonTypeAddForm"));
          }
      }
      

PersonTypeAddForm Class
-----------------------

PersonTypeAddForm class is definition of a Form based on *org.apache.wicket.markup.html.form.Form* which used to add new *PersonType*. It has only constructor *PersonTypeAddForm(String id)* which have initializes page and void *onSubmit()* method to process given input.

Variables
^^^^^^^^^

* **BuildingTypeClassObject buildingType:** Temporary variable which used for store given data in submit. 


Methods
^^^^^^^

* **public PersonTypeAddForm(String id):** Constructor method which initializes *BuildingTypeClassAddForm* form and add *TextArea* for input *BuildingType* variable.
* **void onSubmit():** Method which processes given data in Form and submits it to *PersonTypeCollection* which saves that data to Database.
  
      
  ..  code-block:: java       
      
      
      package tr.edu.itu.cs.db;
      
      import org.apache.wicket.markup.html.form.Form;
      import org.apache.wicket.markup.html.form.TextArea;
      import org.apache.wicket.model.PropertyModel;
      
      
      @SuppressWarnings({ "serial", "rawtypes" })
      public class PersonTypeAddForm extends Form {
      
          private final PersonTypeObject personTypeO = new PersonTypeObject();
      
          @SuppressWarnings("unchecked")
          public PersonTypeAddForm(String id) {
              super(id);
              add(new TextArea("inputPersonTypeName", new PropertyModel(personTypeO,
                      "personTypeName")));
          }
      
          @Override
          protected void onSubmit() {
              // TODO Auto-generated method stub
              PersonType personType = new PersonType(personTypeO.personTypeName);
              PersonTypeCollection personTypeC = new PersonTypeCollection();
              personTypeC.addPersonType(personType);
              this.setResponsePage(new PersonTypeList());
      
          }
      
      }

IPersonTypeCollection Interface
-------------------------------

*IPersonTypeCollection* is definition of the Interface for the *PersonTypeCollection* class. It defines what methods are needed for classes are which implemented from *IPersonTypeCollection*.


Methods
^^^^^^^

* **List<PersonType> getPersonTypes():** which loads elements of collection from Database.
* **void addPersonType(PersonType personType):** adds given *PersonType* to collection.
* **void deletePersonType(PersonType personType):** deletes given *PersonType* from collection.
* **public void updatePersonType(PersonType personType)** updates given *PersonType* in the collection.



  ..  code-block:: java 
  
  
      package tr.edu.itu.cs.db;
      
      import java.util.List;
      
      
      public interface IPersonTypeCollection {
          public List<PersonType> getPersonTypes();
      
          public void addPersonType(PersonType personType);
      
          public void deletePersonType(PersonType personType);
      
          public void updatePersonType(PersonType personType);
      }


PersonTypeCollection Class
--------------------------

PersonTypeCollection class is definition of Collection of *PersonType* Objects. It has constructor, setter and getter methods for all elements of the object.

Variables
^^^^^^^^^

* **Connection _db:** Connection to database.

Methods
^^^^^^^

* **PersonTypeCollection():** Constructor method which makes connection to database and assigns this coonnection to *_db* variable. In any problem it throws SQLException.
* **List<PersonType> getPersonTypes():** which loads elements of collection from Database.
* **void addPersonType(PersonType personType):** adds given *PersonType* to collection.
* **void deletePersonType(PersonType personType):** deletes given *PersonType* from collection.
* **public void updatePersonType(PersonType personType)** updates given *PersonType* in the collection.



  ..  code-block:: java  
      
      
      package tr.edu.itu.cs.db;
      
      import java.sql.Connection;
      import java.sql.DriverManager;
      import java.sql.PreparedStatement;
      import java.sql.ResultSet;
      import java.sql.SQLException;
      import java.sql.Statement;
      import java.util.LinkedList;
      import java.util.List;
      
      
      public class PersonTypeCollection implements IPersonTypeCollection {
          private Connection _db;
      
          public PersonTypeCollection() {
              try {
                  Class.forName("com.mysql.jdbc.Driver");
              } catch (java.lang.ClassNotFoundException e) {
                  System.out.println(e.getMessage());
              }
              try {
                  this._db = DriverManager
                          .getConnection(
                                  "*********",
                                  "*********", "*******");
              } catch (java.sql.SQLException e) {
                  System.out.println(e.getMessage());
              }
          }
      
          @Override
          public List<PersonType> getPersonTypes() {
              List<PersonType> personTypes = new LinkedList<PersonType>();
              try {
                  String query = "SELECT id, PersonTypeName FROM PersonType";
                  Statement statement = this._db.createStatement();
                  ResultSet results = statement.executeQuery(query);
                  while (results.next()) {
                      Integer id = results.getInt("id");
                      String name = results.getString("PersonTypeName");
                      PersonType personType = new PersonType(name);
                      personType.setId(id);
                      personType.setName(name);
                      personTypes.add(personType);
                  }
                  results.close();
                  statement.close();
              } catch (SQLException e) {
                  throw new UnsupportedOperationException(e.getMessage());
              }
              return personTypes;
          }
      
          @Override
          public void addPersonType(PersonType personType) {
              try {
                  String query = "INSERT INTO PersonType (id, PersonTypeName) VALUES (?, ?)";
                  PreparedStatement statement = this._db.prepareStatement(query);
                  statement.setString(1, personType.getName());
                  statement.executeUpdate();
              } catch (SQLException e) {
                  throw new UnsupportedOperationException(e.getMessage());
              }
          }
      
          @Override
          public void deletePersonType(PersonType personType) {
              try {
                  String query = "DELETE FROM PersonType WHERE (ID = ?)";
                  PreparedStatement statement = this._db.prepareStatement(query);
                  statement.setInt(1, personType.getId());
                  statement.executeUpdate();
                  statement.close();
              } catch (SQLException e) {
                  throw new UnsupportedOperationException(e.getMessage());
              }
          }
      
          @Override
          public void updatePersonType(PersonType personType) {
              try {
                  String query = "UPDATE PersonType SET personTypeName = ? WHERE (id = ?)";
                  PreparedStatement statement = this._db.prepareStatement(query);
                  statement.setString(1, personType.getName());
                  statement.setInt(2, personType.getId());
                  statement.executeUpdate();
                  statement.close();
              } catch (SQLException e) {
                  throw new UnsupportedOperationException(e.getMessage());
              }
          }
      }

PersonTypeEdit Class
--------------------

PersonTypeEdit class is definition of PersonType Object edit page. It has only constructor *PersonTypeEdit()* which have initializes page.

Methods
^^^^^^^

* **public PersonTypeEdit():** Constructor method which initializes *PersonTypeEdit* edit page and adds form to edit given *PersonType*.

      
  ..  code-block:: java       
      
      package tr.edu.itu.cs.db;
      
      import java.util.Date;
      
      import org.apache.wicket.markup.html.basic.Label;
      
      
      public class PersonTypeEdit extends BasePage {
          public PersonTypeEdit(PersonType personType) {
      
              // TODO Auto-generated constructor stub
              this.add(new TopMenu("topMenu"));
              Date now = new Date();
              Label dateTimeLabel = new Label("datetime", now.toString());
              this.add(dateTimeLabel);
              this.add(new PersonTypeEditForm("PersonTypeEditForm", personType));
          }
      
      }


  
PersonTypeEditForm Class
------------------------

PersonTypeEditForm class is definition of a Form based on *org.apache.wicket.markup.html.form.Form* which used to edit *PersonType*. It has only constructor *PersonTypeEditForm(String id, PersonType personType)* which have initializes page and void *onSubmit()* method to process given input.

Variables
^^^^^^^^^

* **PersonTypeObject personTypeO:** Temporary variable which used for store given data in submit. 


Methods
^^^^^^^

* **public PersonTypeEditForm(String id):** Constructor method which initializes *PersonTypeEditForm* form and add *TextArea* for input *PersonType* variable.
* **void onSubmit():** Method which processes given data in Form and submits it to *PersonTypeCollection* which saves that data to Database.
  
      
  ..  code-block:: java       
      
      
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
                      "inputPersonTypeName", new PropertyModel(personTypeO, "personTypeName"));
              add(name);
      
              personTypeT.setId(personType.getId());
          }
      
          @Override
          protected void onSubmit() {
              PersonType personType = (PersonType) this.getModelObject();
              PersonTypeCollection personTypeC = new PersonTypeCollection();
              personTypeT.setName(personTypeO.personTypeName);
              personTypeC.updatePersonType(personTypeT);
              this.setResponsePage(new BuildingTypeClassList());
          }
      
      }

      
PersonTypeList Class
--------------------

PersonTypeList class is definition of a page which lists PersonTypes. It has only constructor *PersonTypeList()* which have initializes page.


Methods
^^^^^^^

* **public PersonTypeList():** Constructor method which initializes *PersonTypeList* form.

      
  ..  code-block:: java       
      
      
      package tr.edu.itu.cs.db;
      
      import java.util.Date;
      import java.util.List;
      
      import org.apache.wicket.markup.html.basic.Label;
      import org.apache.wicket.markup.html.link.Link;
      import org.apache.wicket.markup.html.list.ListItem;
      import org.apache.wicket.markup.html.list.ListView;
      
      
      public class PersonTypeList extends BasePage {
          public PersonTypeList() {
              this.add(new TopMenu("topMenu"));
              Date now = new Date();
              Label dateTimeLabel = new Label("datetime", now.toString());
              this.add(dateTimeLabel);
              PersonTypeCollection cc = new PersonTypeCollection();
              List<PersonType> personTypes = cc.getPersonTypes();
      
              ListView personTypeListView = new ListView("personTypeList", personTypes) {
                  @Override
                  protected void populateItem(ListItem item) {
                      final PersonType personType = (PersonType) item.getModelObject();
                      item.add(new Label("personTypeName", personType.getName().toString()));
                      item.add(removeLink("remove", item));
      
                      Link editLink = new Link("edit") {
                          @Override
                          public void onClick() {
                              setResponsePage(new PersonTypeEdit(personType));
                          }
                      };
                      item.add(editLink);
                  }
              };
              this.add(personTypeListView);
          }
      }



PersonTypeObject Class
----------------------

PersonTypeObject class is an object definition which used for serialized operations.


Variable
^^^^^^^^

* **String personTypeName:** A string variable to store *PersonTypeName* of a *PersonType* object.

      
  ..  code-block:: java       
      
      
      package tr.edu.itu.cs.db;
      
      public class PersonTypeObject {
          public String personTypeName = "";
      }

Person Class
------------

Person class is definition of Person Objects. It has constructor, setter and getter methods for elements of the object. 

Variables
^^^^^^^^^

* **Integer _id:** Identificatipon valuse of the object.
* **String _personName:** Name of the object.


Methods
^^^^^^^

* **public Person():** Constructor method which creates an empty object.
* **public Person(String name):** Constructor method which creates an object with given Name. 
* **void setId(Integer id):** Sets object id.
* **Integer getId():** Gets object id.
* **void setName(String name):** Sets object name.
* **String getName():** Gets object name.
 
 
  ..  code-block:: java  

      package tr.edu.itu.cs.db;
      
      public class Person extends DatabaseManager {
      
          private Integer _id = null;
          private String _personName = null;
      
          public Person() {
      
          }
      
          public Person(String name) {
              this.setName(name);
          }
      
          public void setId(Integer id) {
              this._id = id;
          }
      
          public Integer getId() {
              return this._id;
          }
      
          public void setName(String name) {
              this._personName = name;
          }
      
          public String getName() {
              return this._personName;
          }
      }
            
            
            
PersonAdd Class
---------------

PersonAdd class is definition of *Person* Object add page. It has only constructor *PersonAdd()* which have initializes page.

Methods
^^^^^^^

* **public PersonAdd():** Constructor method which initializes *PersonAdd* addition page and adds form to input new *Person*.
  
      
  ..  code-block:: java       
      
      
      package tr.edu.itu.cs.db;
      
      import java.util.Date;
      
      import org.apache.wicket.markup.html.basic.Label;
      
      
      public class PersonAdd extends BasePage {
      
          public PersonAdd() {
              // TODO Auto-generated constructor stub
              this.add(new TopMenu("topMenu"));
              Date now = new Date();
              Label dateTimeLabel = new Label("datetime", now.toString());
              this.add(dateTimeLabel);
              this.add(new PersonAddForm("PersonAddForm"));
          }
      }
      

PersonAddForm Class
-------------------

PersonAddForm class is definition of a Form based on *org.apache.wicket.markup.html.form.Form* which used to add new *Person*. It has only constructor *PersonAddForm(String id)* which have initializes page and void *onSubmit()* method to process given input.

Variables
^^^^^^^^^

* **BuildingClassObject building:** Temporary variable which used for store given data in submit. 


Methods
^^^^^^^

* **public PersonAddForm(String id):** Constructor method which initializes *BuildingClassAddForm* form and add *TextArea* for input *Building* variable.
* **void onSubmit():** Method which processes given data in Form and submits it to *PersonCollection* which saves that data to Database.
  
      
  ..  code-block:: java       
      
      
      package tr.edu.itu.cs.db;
      
      import org.apache.wicket.markup.html.form.Form;
      import org.apache.wicket.markup.html.form.TextArea;
      import org.apache.wicket.model.PropertyModel;
      
      
      @SuppressWarnings({ "serial", "rawtypes" })
      public class PersonAddForm extends Form {
      
          private final PersonObject personO = new PersonObject();
      
          @SuppressWarnings("unchecked")
          public PersonAddForm(String id) {
              super(id);
              add(new TextArea("inputPersonName", new PropertyModel(personO,
                      "personName")));
          }
      
          @Override
          protected void onSubmit() {
              // TODO Auto-generated method stub
              Person person = new Person(personO.personName);
              PersonCollection personC = new PersonCollection();
              personC.addPerson(person);
              this.setResponsePage(new PersonList());
      
          }
      
      }

IPersonCollection Interface
---------------------------

*IPersonCollection* is definition of the Interface for the *PersonCollection* class. It defines what methods are needed for classes are which implemented from *IPersonCollection*.


Methods
^^^^^^^

* **List<Person> getPersons():** which loads elements of collection from Database.
* **void addPerson(Person person):** adds given *Person* to collection.
* **void deletePerson(Person person):** deletes given *Person* from collection.
* **public void updatePerson(Person person)** updates given *Person* in the collection.



  ..  code-block:: java 
  
  
      package tr.edu.itu.cs.db;
      
      import java.util.List;
      
      
      public interface IPersonCollection {
          public List<Person> getPersons();
      
          public void addPerson(Person person);
      
          public void deletePerson(Person person);
      
          public void updatePerson(Person person);
      }


PersonCollection Class
----------------------

PersonCollection class is definition of Collection of *Person* Objects. It has constructor, setter and getter methods for all elements of the object.

Variables
^^^^^^^^^

* **Connection _db:** Connection to database.

Methods
^^^^^^^

* **PersonCollection():** Constructor method which makes connection to database and assigns this coonnection to *_db* variable. In any problem it throws SQLException.
* **List<Person> getPersons():** which loads elements of collection from Database.
* **void addPerson(Person person):** adds given *Person* to collection.
* **void deletePerson(Person person):** deletes given *Person* from collection.
* **public void updatePerson(Person person)** updates given *Person* in the collection.



  ..  code-block:: java  
      
      
      package tr.edu.itu.cs.db;
      
      import java.sql.Connection;
      import java.sql.DriverManager;
      import java.sql.PreparedStatement;
      import java.sql.ResultSet;
      import java.sql.SQLException;
      import java.sql.Statement;
      import java.util.LinkedList;
      import java.util.List;
      
      
      public class PersonCollection implements IPersonCollection {
          private Connection _db;
      
          public PersonCollection() {
              try {
                  Class.forName("com.mysql.jdbc.Driver");
              } catch (java.lang.ClassNotFoundException e) {
                  System.out.println(e.getMessage());
              }
              try {
                  this._db = DriverManager
                          .getConnection(
                                  "*********",
                                  "*********", "*******");
              } catch (java.sql.SQLException e) {
                  System.out.println(e.getMessage());
              }
          }
      
          @Override
          public List<Person> getPersons() {
              List<Person> persons = new LinkedList<Person>();
              try {
                  String query = "SELECT id, PersonName FROM Person";
                  Statement statement = this._db.createStatement();
                  ResultSet results = statement.executeQuery(query);
                  while (results.next()) {
                      Integer id = results.getInt("id");
                      String name = results.getString("PersonName");
                      Person person = new Person(name);
                      person.setId(id);
                      person.setName(name);
                      persons.add(person);
                  }
                  results.close();
                  statement.close();
              } catch (SQLException e) {
                  throw new UnsupportedOperationException(e.getMessage());
              }
              return persons;
          }
      
          @Override
          public void addPerson(Person person) {
              try {
                  String query = "INSERT INTO Person (id, PersonName) VALUES (?, ?)";
                  PreparedStatement statement = this._db.prepareStatement(query);
                  statement.setString(1, person.getName());
                  statement.executeUpdate();
              } catch (SQLException e) {
                  throw new UnsupportedOperationException(e.getMessage());
              }
          }
      
          @Override
          public void deletePerson(Person person) {
              try {
                  String query = "DELETE FROM Person WHERE (ID = ?)";
                  PreparedStatement statement = this._db.prepareStatement(query);
                  statement.setInt(1, person.getId());
                  statement.executeUpdate();
                  statement.close();
              } catch (SQLException e) {
                  throw new UnsupportedOperationException(e.getMessage());
              }
          }
      
          @Override
          public void updatePerson(Person person) {
              try {
                  String query = "UPDATE Person SET personName = ? WHERE (id = ?)";
                  PreparedStatement statement = this._db.prepareStatement(query);
                  statement.setString(1, person.getName());
                  statement.setInt(2, person.getId());
                  statement.executeUpdate();
                  statement.close();
              } catch (SQLException e) {
                  throw new UnsupportedOperationException(e.getMessage());
              }
          }
      }

PersonEdit Class
----------------

PersonEdit class is definition of Person Object edit page. It has only constructor *PersonEdit()* which have initializes page.

Methods
^^^^^^^

* **public PersonEdit():** Constructor method which initializes *PersonEdit* edit page and adds form to edit given *Person*.

      
  ..  code-block:: java       
      
      package tr.edu.itu.cs.db;
      
      import java.util.Date;
      
      import org.apache.wicket.markup.html.basic.Label;
      
      
      public class PersonEdit extends BasePage {
          public PersonEdit(Person person) {
      
              // TODO Auto-generated constructor stub
              this.add(new TopMenu("topMenu"));
              Date now = new Date();
              Label dateTimeLabel = new Label("datetime", now.toString());
              this.add(dateTimeLabel);
              this.add(new PersonEditForm("PersonEditForm", person));
          }
      
      }


  
PersonEditForm Class
--------------------

PersonEditForm class is definition of a Form based on *org.apache.wicket.markup.html.form.Form* which used to edit *Person*. It has only constructor *PersonEditForm(String id, Person person)* which have initializes page and void *onSubmit()* method to process given input.

Variables
^^^^^^^^^

* **PersonObject personO:** Temporary variable which used for store given data in submit. 


Methods
^^^^^^^

* **public PersonEditForm(String id):** Constructor method which initializes *PersonEditForm* form and add *TextArea* for input *Person* variable.
* **void onSubmit():** Method which processes given data in Form and submits it to *PersonCollection* which saves that data to Database.
  
      
  ..  code-block:: java       
      
      
      package tr.edu.itu.cs.db;
      
      import java.io.Serializable;
      
      import org.apache.wicket.markup.html.form.Form;
      import org.apache.wicket.markup.html.form.TextField;
      import org.apache.wicket.model.PropertyModel;
      
      
      @SuppressWarnings({ "serial", "rawtypes" })
      public class PersonEditForm extends Form implements Serializable {
      
          private final PersonObject personO = new PersonObject();
          Person personT = new Person();
      
          @SuppressWarnings("unchecked")
          public PersonEditForm(String id, Person person) {
              super(id);
              // CompoundPropertyModel model = new CompoundPropertyModel(btc);
              // this.setModelObject(model);
              personO.personName = person.getName();
      
              final TextField<String> name = new TextField<String>(
                      "inputPersonName", new PropertyModel(personO, "personName"));
              add(name);
      
              personT.setId(person.getId());
          }
      
          @Override
          protected void onSubmit() {
              Person person = (Person) this.getModelObject();
              PersonCollection personC = new PersonCollection();
              personT.setName(personO.personName);
              personC.updatePerson(personT);
              this.setResponsePage(new BuildingClassList());
          }
      
      }

      
PersonList Class
----------------

PersonList class is definition of a page which lists Persons. It has only constructor *PersonList()* which have initializes page.


Methods
^^^^^^^

* **public PersonList():** Constructor method which initializes *PersonList* form.

      
  ..  code-block:: java       
      
      
      package tr.edu.itu.cs.db;
      
      import java.util.Date;
      import java.util.List;
      
      import org.apache.wicket.markup.html.basic.Label;
      import org.apache.wicket.markup.html.link.Link;
      import org.apache.wicket.markup.html.list.ListItem;
      import org.apache.wicket.markup.html.list.ListView;
      
      
      public class PersonList extends BasePage {
          public PersonList() {
              this.add(new TopMenu("topMenu"));
              Date now = new Date();
              Label dateTimeLabel = new Label("datetime", now.toString());
              this.add(dateTimeLabel);
              PersonCollection cc = new PersonCollection();
              List<Person> persons = cc.getPersons();
      
              ListView personListView = new ListView("personList", persons) {
                  @Override
                  protected void populateItem(ListItem item) {
                      final Person person = (Person) item.getModelObject();
                      item.add(new Label("personName", person.getName().toString()));
                      item.add(removeLink("remove", item));
      
                      Link editLink = new Link("edit") {
                          @Override
                          public void onClick() {
                              setResponsePage(new PersonEdit(person));
                          }
                      };
                      item.add(editLink);
                  }
              };
              this.add(personListView);
          }
      }



PersonObject Class
------------------

PersonObject class is an object definition which used for serialized operations.


Variable
^^^^^^^^

* **String personName:** A string variable to store *PersonName* of a *Person* object.

      
  ..  code-block:: java       
      
      
      package tr.edu.itu.cs.db;
      
      public class PersonObject {
          public String personName = "";
      }

      