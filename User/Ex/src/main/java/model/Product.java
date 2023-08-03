package model;

import lombok.Builder;
import lombok.Data;
import java.sql.Date;

@Data
@Builder
public class Product {
    private Long id;
    private EAuthor author;
    private ECategory category;
    private String title;
    private Date datePublish;
    private String description;

    public Product() {
    }

    public Product(Long id, EAuthor author, ECategory category, String title, Date datePublish, String description) {
        this.id = id;
        this.author = author;
        this.category = category;
        this.title = title;
        this.datePublish = datePublish;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EAuthor getAuthor() {
        return author;
    }

    public void setAuthor(EAuthor author) {
        this.author = author;
    }

    public ECategory getCategory() {
        return category;
    }

    public void setCategory(ECategory category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDatePublish() {
        return datePublish;
    }

    public void setDatePublish(Date datePublish) {
        this.datePublish = datePublish;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
