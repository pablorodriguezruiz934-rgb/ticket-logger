package dao;

import entity.Faction;

import java.sql.SQLException;
import java.util.List;
public interface FactionDAO {
    List<Faction> listAllFacciones() throws SQLException;
    void insertFacciones(Faction faction) throws SQLException;
    void updateFacciones(Faction faction) throws SQLException;
    void deleteFacciones(int id) throws SQLException;
    Faction getFaccionesById(int id) throws SQLException;
    boolean existsFaccionesByCode(String code) throws SQLException;
    boolean existsFaccionesByCodeAndNotId(String code, int id) throws SQLException;
}
