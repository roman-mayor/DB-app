package by.roman_mayorov;

import by.roman_mayorov.utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcRunner {
    public static void main(String[] args){
        System.out.println(getBookByAuthorName("ЛЕВ ТОЛСТОЙ"));
        System.out.println(getAllById(13L));
    }

    public static List<String> getBookByAuthorName(String authorName){
        String sql = """
                SELECT book_name FROM info WHERE author = ?;
                """;
        List<String> books = new ArrayList<>();

        try(Connection connection = ConnectionManager.open();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, authorName);

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                books.add(resultSet.getString("book_name"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return books;
    }

    public static List<String> getAllById(Long id){
        String sql = """
                SELECT * FROM info WHERE id = ?;
                """;
        List<String> result = new ArrayList<>();
        try(Connection connection = ConnectionManager.open();
            var statement = connection.prepareStatement(sql)){
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                result.add(resultSet.getString("book_name") + " " + resultSet.getString("author"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }
}
