package tr.edu.itu.cs.db;

import java.util.List;


public interface IBuildingTypeClassCollection {
    public List<BuildingTypeClass> getBuildingTypeClasses();

    public void addBuildingTypeClass(BuildingTypeClass buildingTypeClass);

    public void deleteBuildingTypeClass(BuildingTypeClass buildingTypeClass);

    public void updateBuildingTypeClass(BuildingTypeClass buildingTypeClass);
}
