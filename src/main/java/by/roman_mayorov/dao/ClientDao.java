package by.roman_mayorov.dao;

import by.roman_mayorov.dto.ClientFilter;
import by.roman_mayorov.entity.Client;
import by.roman_mayorov.utils.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class ClientDao {
    private static final ClientDao INSTANCE = new ClientDao();

    private final static String SAVE_SQL =
            """
            INSERT INTO clients (first_name, last_name, age)  
            VALUES (?,?,?)
            """;

    private final static String SQL_DELETE =
            """
            DELETE FROM clients WHERE id = ?
            """;

    private final static String SQL_FIND_ALL =
            """
             SELECT first_name, last_name, age, id FROM clients
             """;

    private final static String SQL_FIND_BY_ID =
            """
             SELECT first_name, last_name, age, id FROM clients WHERE id = ?
             """;

    private final static String SQL_UPDATE =
            """
            UPDATE clients SET first_name = ?, last_name = ?, age = ? WHERE id = ? 
            """;

    public Client save(Client client){
        try(var connection = ConnectionManager.open();
            var statement = connection.prepareStatement(SAVE_SQL, RETURN_GENERATED_KEYS)){
            statement.setString(1, client.getFirstName());
            statement.setString(2, client.getLastName());
            statement.setInt(3, client.getAge());
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();

            if(resultSet.next()) client.setId(resultSet.getLong("id"));

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

    public List<Client> findAll(ClientFilter filter) {
        List<Object> params = new ArrayList<>();
        List<String> whereSQL = new ArrayList<>();

        if (filter.getAge() != 0){
            params.add(filter.getAge());
            whereSQL.add("age >= ?");
        }

        if (filter.getFirst_name() != null){
            params.add(filter.getFirst_name() + "%");
            whereSQL.add("first_name ILIKE ?");
        }

        params.add(filter.getLimit());
        var where = whereSQL.stream().collect(Collectors
                .joining(" AND ",
                        " WHERE ",
                        " LIMIT ? "));

        String sql = SQL_FIND_ALL + where;

        List<Client> clients = new ArrayList<>();
        try (var connection = ConnectionManager.open();
             var statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.size(); i++) {
                statement.setObject(i+1, params.get(i));
            }

            System.out.println(statement);

            var resultSet =  statement.executeQuery();

            while(resultSet.next()){
                clients.add(
                        buildClient(resultSet));
            }
            return clients;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }


    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        try (var connection = ConnectionManager.open();
             var statement = connection.prepareStatement(SQL_FIND_ALL)){
            var resultSet =  statement.executeQuery();
            while(resultSet.next()){
                clients.add(
                        buildClient(resultSet));
            }
            return clients;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public Optional<Client> findById(Long id) {
        try(var connection = ConnectionManager.open();
        var statement = connection.prepareStatement(SQL_FIND_BY_ID)){
            statement.setLong(1, id);
            var resultSet =  statement.executeQuery();
            Client client = null;
            if (resultSet.next()) {
                client = buildClient(resultSet);
            }
            return Optional.ofNullable(client);
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void update(Client client) {
        try(var connection = ConnectionManager.open();
            var statement = connection.prepareStatement(SQL_UPDATE)){
            statement.setString(1, client.getFirstName());
            statement.setString(2, client.getLastName());
            statement.setInt(3, client.getAge());
            statement.setLong(4, client.getId());
            statement.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private static Client buildClient(ResultSet resultSet) throws SQLException {
        return new Client(
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getInt("age"),
                resultSet.getLong("id"));
    }

    public static ClientDao getInstance() {
        return INSTANCE;
    }

    private ClientDao(){}
}
