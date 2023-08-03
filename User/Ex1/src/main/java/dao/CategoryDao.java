package dao;

import model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao extends DatabaseConnection{

    private static final String SELECT_ALL_CATEGORY_TYPES = "SELECT * FROM category;";
    private static final String FIND_BY_ID = "SELECT * FROM category where id = ?;";


    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CATEGORY_TYPES);
            System.out.println("findAll: " + preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int idCate = rs.getInt("id");
                String name = rs.getString("name");
                Category category = new Category(idCate, name);
                categories.add(category);
            }
        } catch (SQLException sqlException) {
            printSQLException(sqlException);
        }
        return categories;
    }

    public Category findById(int id) {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setInt(1, id);
            System.out.println("findById: " + preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int idCate = rs.getInt("id");
                String name = rs.getString("name");
                Category category = new Category(idCate, name);
                return category;
            }
        } catch (SQLException sqlException) {
            printSQLException(sqlException);
        }
        return null;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
