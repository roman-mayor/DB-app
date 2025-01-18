package by.roman_mayorov.dao;

import by.roman_mayorov.entity.Client;
import by.roman_mayorov.utils.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClientDao {
    private static final ClientDao INSTANCE = new ClientDao();

    private final static String SAVE_SQL =
            """
            INSERT INTO clients (first_name, last_name, age)  
            VALUES (?,?,?)
            """;

    private final static String SQL_DELETE =
            """
            DELETE FROM clients WHERE id = ?;
            """;

    public Client save(Client client){
        try(var connection = ConnectionManager.open();
            var statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, client.getFirstName());
            statement.setString(2, client.getLastName());
            statement.setInt(3, client.getAge());

            ResultSet resultSet = statement.getGeneratedKeys();

            if(resultSet.next()) client.setId(resultSet.getLong("id"));

            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return client;
    }

    public boolean delete(Long id) {
        try (var connection = ConnectionManager.open();
             var statement = connection.prepareStatement(SQL_DELETE)){
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ClientDao getInstance() {
        return INSTANCE;
    }

    private ClientDao(){}
}
