package org.alixar.daw2.pablo.gestion_electrica.dao;

import org.alixar.daw2.pablo.gestion_electrica.entity.Region;

import java.util.List;
public interface RegionDAO {
    List<Region> listAllRegions() ;
    void insertRegion(Region region);
    void updateRegion(Region region);
    void deleteRegion(Long id);
    Region getRegionById(Long id);
    boolean existsRegionByCode(String code);
    boolean existsRegionByCodeAndNotId(String code, Long id);
}
