package model;

import java.util.Date;

public class Product {
    private long id;
    private String name;
    private Double price;
    private int quantity;
    private int idCategory;
    private String avatar;
    private String description;
    private Date createAt;

    public Product() {
    }

    public Product(long id, String name, Double price, int quantity, int idCategory, String avatar, String description, Date createAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.idCategory = idCategory;
        this.avatar = avatar;
        this.description = description;
        this.createAt = createAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantily) {
        this.quantity = quantily;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateAt() {
        if (createAt == null) return "";
        return createAt.toString();
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
