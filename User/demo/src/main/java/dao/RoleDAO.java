package dao;

import model.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDAO extends DatabaseConnection{
    private final String SELECT_ROLES = "SELECT * FROM `roles`";

    public List<model.Role> getRoles(){
        List<Role> roles = new ArrayList<>();
        try (Connection connection = getConnection();

             // Step 2: truyền câu lênh mình muốn chạy nằm ở trong này (SELECT_USERS)
             PreparedStatement preparedStatement = connection
                     .prepareStatement(SELECT_ROLES)) {
            System.out.println(preparedStatement);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                model.Role role = new model.Role();
                role.setId(rs.getLong("id"));
                role.setName( rs.getString("name"));
                roles.add(role);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return roles;
    }
}