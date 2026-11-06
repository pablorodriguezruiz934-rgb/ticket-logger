package dao;

import entity.Faction;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FactionDAOImpl implements FactionDAO {

    @Override
    public List<Faction> listAllFacciones() throws SQLException {
        List<Faction> facciones = new ArrayList<>();
        String query = "SELECT * FROM factions";

        try (Connection connection = DatabaseConnectionManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String code = resultSet.getString("code");
                String name = resultSet.getString("name");
                facciones.add(new Faction(id, code, name));
            }
        }
        return facciones;
    }

    @Override
    public void insertFacciones(Faction faction) throws SQLException {
        String query = "INSERT INTO factions (code, name) VALUES (?, ?)";

        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, faction.getCode());
            preparedStatement.setString(2, faction.getName());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void updateFacciones(Faction faction) throws SQLException {
        String query = "UPDATE factions SET code = ?, name = ? WHERE id = ?";

        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, faction.getCode());
            preparedStatement.setString(2, faction.getName());
            preparedStatement.setInt(3, faction.getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void deleteFacciones(int id) throws SQLException {
        String query = "DELETE FROM factions WHERE id = ?";

        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public Faction getFaccionesById(int id) throws SQLException {
        String query = "SELECT * FROM factions WHERE id = ?";
        Faction faction = null;

        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String code = resultSet.getString("code");
                    String name = resultSet.getString("name");
                    faction = new Faction(id, code, name);
                }
            }
        }
        return faction;
    }

    @Override
    public boolean existsFaccionesByCode(String code) throws SQLException {
        String sql = "SELECT COUNT(*) FROM factions WHERE UPPER(code) = ?";

        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, code.toUpperCase());
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getInt(1) > 0;
            }
        }
    }

    @Override
    public boolean existsFaccionesByCodeAndNotId(String code, int id) throws SQLException {
        String sql = "SELECT COUNT(*) FROM factions WHERE UPPER(code) = ? AND id != ?";

        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, code.toUpperCase());
            statement.setInt(2, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getInt(1) > 0;
            }
        }
    }
}
