package service;

import Util.AppConstant;
import model.EAuthor;
import model.ECategory;
import model.Product;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProductService {

    private static List<Product> products;

    private static Long currentId = 0L;

    private static final ProductService productService;

    static {
        products = new ArrayList<>();
        products.add(new Product(++currentId, EAuthor.TOKUDA, ECategory.ACTION," Sát Quyền ", Date.valueOf("1997-12-20"), "Demo"));
        products.add(new Product(++currentId, EAuthor.KENTO, ECategory.DRAMA," Chết Thì Chịu ", Date.valueOf("1997-12-20"), "Demo"));
        products.add(new Product(++currentId, EAuthor.KENTO, ECategory.COMEDY," Tây Du Ký ", Date.valueOf("1997-12-20"), "Demo"));
        products.add(new Product(++currentId, EAuthor.TOKUDA, ECategory.HORROR,"Chú Nguyền ", Date.valueOf("1997-12-20"), "Demo"));
        productService = new ProductService();
    }

    public List<Product> getProducts(){
        return products;
    }
    public Product findById(Long id){

        return products.stream()
                .filter(product -> Objects.equals(product.getId(), id))
                .findFirst()
                .orElseThrow(() ->  new RuntimeException(String.format(AppConstant.ID_NOT_FOUND, "Product")));
    }
    public void create(Product product){
        product.setId(++currentId);
        products.add(product);
    }

    public static ProductService getProductService() {
        return productService;
    }
    private ProductService(){}

    public void edit(Product product) {
        for (var item : products){
            if(item.getId().equals(product.getId())){
                item.setAuthor(product.getAuthor());
                item.setCategory(product.getCategory());
                item.setTitle(product.getTitle());
                item.setDatePublish(product.getDatePublish());
                item.setDescription(product.getDescription());
            }
        }
    }

    public boolean existById(Long id) {
        for (var product : products){
            if(Objects.equals(id, product.getId())){
                return true;
            }
        }
        return false;
    }

    public void delete(Long productId) {
        products = products
                .stream()
                .filter(product -> !Objects.equals(product.getId(), productId))
                .collect(Collectors.toList());
    }
}
