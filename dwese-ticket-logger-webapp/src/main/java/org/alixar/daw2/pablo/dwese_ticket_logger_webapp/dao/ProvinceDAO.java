package org.alixar.daw2.pablo.dwese_ticket_logger_webapp.dao;

import org.alixar.daw2.pablo.dwese_ticket_logger_webapp.entity.Province;
import java.util.List;

public interface ProvinceDAO {
    List<Province> listAllProvinces() ;
    void insertProvince(Province province);
    void updateProvince(Province province);
    void deleteProvince(int id);
    Province getProvinceById(int id);
    boolean existsProvinceByCode(String code);
    boolean existsProvinceByCodeAndNotId(String code, int id);
}
