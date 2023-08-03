package service;

import Util.AppConstant;

import dao.ProductDAO;
import model.Product;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private static List<Product> products;

    private static Long currentId = 0L;

    private static final ProductService productService;

    private ProductDAO productDAO = new ProductDAO();

    static {
        products = new ArrayList<>();
//        products.add(new Product(++currentId, "MAVEN", "2500","12","ACTION","dat","dat", Date.valueOf("1994-07-29")));
//        products.add(new Product(++currentId, "MAVEN", "2500","12","ACTION","dat","dat", Date.valueOf("1994-07-29")));
//        products.add(new Product(++currentId, "MAVEN", "2500","12","ACTION","dat","dat", Date.valueOf("1994-07-29")));
//        products.add(new Product(++currentId, "MAVEN", "2500","12","ACTION","dat","dat", Date.valueOf("1994-07-29")));
        productService = new ProductService();
    }

    public List<Product> getProducts(){
        return productDAO.findAll();
    }
    public Product findById(Long id){
        return productDAO.findById(id)
                .orElseThrow(() ->  new RuntimeException(String.format(AppConstant.ID_NOT_FOUND, "Product")));
    }
    public void create(Product product){
        productDAO.insertProduct(product);
    }

    public static ProductService getProductService() {
        return productService;
    }
    private ProductService(){}

    public void edit(Product product) {
        productDAO.updateProduct(product);
    }

    public boolean existById(Long id) {
        return productDAO.findById(id).orElse(null) != null;
    }

    public void delete(Long productId) {
        productDAO.deleteById(productId);

    }
}