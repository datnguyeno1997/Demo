package dao;

import model.Role;
import model.enums.EGender;
import model.User;
import service.dto.PageableRequest;
import service.dto.enums.ESortType;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAO extends DatabaseConnection {
    private final String SELECT_ALL_USERS = "SELECT u.*,r.name role_name  FROM `users` u LEFT JOIN " +
            "`roles` r on u.role_id = r.id  WHERE u.`name` like '%s' OR u.`phone` LIKE '%s' Order BY %s %s";
    private final String UPDATE_USER = "UPDATE `users` SET `name` = ?, `phone` = ?, `avatar` = ?, `gender` = ?, `dob` = ?, `cover_picture` = ?, `role_id` = ? WHERE (`id` = ?);";

    private final String INSERT_USERS = "INSERT INTO `users` (`name`, `phone`, `email`, `avatar`, `gender`, `dob`, `cover_picture`, `role_id`) \n" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private final String FIND_BY_ID = "SELECT u.*,r.name role_name  FROM " +
            "`users` u LEFT JOIN `roles` r on u.role_id = r.id WHERE u.`id` = ?"; // show Edit

    private final String DELETE_BY_ID = "DELETE FROM `users` WHERE (`id` = ?)";


    public List<User> findAll(PageableRequest request) {
        List<User> users = new ArrayList<>();
        String search = request.getSearch();
        if(request.getSortField() == null){
            request.setSortField("id");
        }
        if(request.getSortType() == null){
            request.setSortType(ESortType.DESC);
        }
        if(search == null){
            search = "%%";
        }else {
            search = "%" + search + "%";
        }
        try (Connection connection = getConnection();

             // Step 2: truyền câu lênh mình muốn chạy nằm ở trong này (SELECT_USERS)
             PreparedStatement preparedStatement = connection
                     .prepareStatement(String.format(SELECT_ALL_USERS, search, search,
                             request.getSortField(), request.getSortType()))) {

            System.out.println(preparedStatement);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {

                users.add(getUserByResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void insertUser(User user){
        try (Connection connection = getConnection();

             // Step 2: truyền câu lênh mình muốn chạy nằm ở trong này (SELECT_USERS)
             PreparedStatement preparedStatement = connection
                     .prepareStatement(INSERT_USERS)) {
            System.out.println(preparedStatement);
            preparedStatement.setString(1,user.getName());
            preparedStatement.setString(2,user.getPhone());
            preparedStatement.setString(3,user.getEmail());
            preparedStatement.setString(4,user.getAvatar());
            preparedStatement.setString(5,user.getGender().toString());
            preparedStatement.setTimestamp(6,Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(7,user.getCoverPicture());
            preparedStatement.setLong(8, user.getRole().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateUser(User user) {
        try (Connection connection = getConnection();

             // Step 2: truyền câu lênh mình muốn chạy nằm ở trong này (SELECT_USERS)
             PreparedStatement preparedStatement = connection
                     .prepareStatement(UPDATE_USER)) {
            System.out.println(preparedStatement);
            preparedStatement.setString(1,user.getName());
            preparedStatement.setString(2,user.getPhone());
            preparedStatement.setString(3,user.getAvatar());
            preparedStatement.setString(4,user.getGender().toString());
            preparedStatement.setTimestamp(5,Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(6,user.getCoverPicture());
            preparedStatement.setLong(7, user.getRole().getId());
            preparedStatement.setLong(8,user.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<User> findById(Long id) {
        try (Connection connection = getConnection();

             // Step 2: truyền câu lênh mình muốn chạy nằm ở trong này (SELECT_USERS)
             PreparedStatement preparedStatement = connection
                     .prepareStatement(FIND_BY_ID)) {
            preparedStatement.setLong(1, id);
            System.out.println(preparedStatement);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return Optional.of(getUserByResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public void deleteById(Long id) {
        try (Connection connection = getConnection();

             // Step 2: truyền câu lênh mình muốn chạy nằm ở trong này (SELECT_USERS)
             PreparedStatement preparedStatement = connection
                     .prepareStatement(DELETE_BY_ID)) {
            preparedStatement.setLong(1, id);
            System.out.println(preparedStatement);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private User getUserByResultSet(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        String name = rs.getString("name");
        String phone = rs.getString("phone");
        String email = rs.getString("email");
        String avatar = rs.getString("avatar");
        String dob = rs.getString("dob");
        String coverPicture = rs.getString("cover_picture");
        String gender = rs.getString("gender");
        String roleName = rs.getString("role_name");
        Long roleId = rs.getLong("role_id");
        model.Role role = new Role(roleId, roleName);
        return new User(id, name, phone,email, avatar, EGender.valueOf(gender), Date.valueOf(dob), coverPicture, role);
    }

}