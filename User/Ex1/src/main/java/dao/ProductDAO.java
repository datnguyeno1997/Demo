package dao;


import model.Product;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDAO extends DatabaseConnection {
    private final String SELECT_ALL_PRODUCTS = "select p.*, c.name \n" +
            "from product p \n" +
            "join category c\n" +
            "on p.id_category = c.id";
    private final String UPDATE_PRODUCT = "UPDATE `product` SET `name` = ?, `price` = ?, `quantity` = ?, `id_category` = ?, `avatar` = ?, `description` = ?, `create_at` = ? WHERE (`id` = ?);";

    private final String INSERT_PRODUCTS = "INSERT INTO `product` (`name`, `price`, `quantity`, `id_category`, `avatar`, `description`, `create_at`) \n" +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";
    private final String FIND_BY_ID = "SELECT * FROM `product` WHERE `id` = ?"; // show Edit

    private final String DELETE_BY_ID = "DELETE FROM `product` WHERE (`id` = ?)";



    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        try (Connection connection = getConnection();

             // Step 2: truyền câu lênh mình muốn chạy nằm ở trong này (SELECT_USERS)
             PreparedStatement preparedStatement = connection
                     .prepareStatement(SELECT_ALL_PRODUCTS)) {
            System.out.println(preparedStatement);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {

                products.add(getProductByResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    public void insertProduct(Product product){
        try (Connection connection = getConnection();


             // Step 2: truyền câu lênh mình muốn chạy nằm ở trong này (SELECT_USERS)
             PreparedStatement preparedStatement = connection
                     .prepareStatement(INSERT_PRODUCTS)) {
            System.out.println(preparedStatement);
            preparedStatement.setString(1,product.getName());
            preparedStatement.setString(2, String.valueOf(product.getPrice()));
            preparedStatement.setString(3, String.valueOf(product.getQuantity()));
            preparedStatement.setString(4, String.valueOf(product.getIdCategory()));
            preparedStatement.setString(5,product.getAvatar());
            preparedStatement.setString(6,product.getDescription());
            preparedStatement.setTimestamp(7,Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateProduct(Product product) {
        try (Connection connection = getConnection();

             // Step 2: truyền câu lênh mình muốn chạy nằm ở trong này (SELECT_USERS)
             PreparedStatement preparedStatement = connection
                     .prepareStatement(UPDATE_PRODUCT)) {
            System.out.println(preparedStatement);
            preparedStatement.setString(1,product.getName());
            preparedStatement.setString(2, String.valueOf(product.getPrice()));
            preparedStatement.setString(3, String.valueOf(product.getQuantity()));
            preparedStatement.setString(4, String.valueOf(product.getIdCategory()));
            preparedStatement.setString(5,product.getAvatar());
            preparedStatement.setString(6,product.getDescription());
            preparedStatement.setTimestamp(7,Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setLong(8,product.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Product> findById(Long id) {
        try (Connection connection = getConnection();

             // Step 2: truyền câu lênh mình muốn chạy nằm ở trong này (SELECT_PRODUCTS)
             PreparedStatement preparedStatement = connection
                     .prepareStatement(FIND_BY_ID)) {
            preparedStatement.setLong(1, id);
            System.out.println(preparedStatement);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return Optional.of(getProductByResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void deleteById(Long id) {
        try (Connection connection = getConnection();

             // Step 2: truyền câu lênh mình muốn chạy nằm ở trong này (SELECT_PRODUCTS)
             PreparedStatement preparedStatement = connection
                     .prepareStatement(DELETE_BY_ID)) {
            preparedStatement.setLong(1, id);
            System.out.println(preparedStatement);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Product getProductByResultSet(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        String name = rs.getString("name");
        Double price = rs.getDouble("price");
        Integer quantity = rs.getInt("quantity");
        Integer idCategory = rs.getInt("id_category");
        String avatar = rs.getString("avatar");
        String description = rs.getString("description");
        String createAt = rs.getString("create_at");
        return new Product(id, name, price,quantity, idCategory, avatar, description,  Date.valueOf(createAt));
    }
    public Date ConvertDate(Date createAt){

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String s = df.format(createAt);
        String result = s;
        try {
            createAt = (Date) df.parse(result);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return createAt;
    }
}

