package controller;

import Util.AppConstant;
import Util.AppUtil;
import Util.RunnableProduct;
import Util.RunnableWithRegex;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Product;
import service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@WebServlet(urlPatterns = "/products", name = "ProductServlet")
public class ProductServlet extends HttpServlet {
    private final String PAGE = "products";

    private Map<String, RunnableProduct> validators;

    private final Map<String, String> errors = new HashMap<>();

    @Override
    public void init() {
        validators = new HashMap<>();

//        validators.put("phone", new RunnableWithRegex("[0-9]{10}", "phone", errors));
//        validators.put("dob", new RunnableWithRegex("[0-9]{10}", "dob", errors));

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter(AppConstant.ACTION);

        if(Objects.equals(action, AppConstant.EDIT)){
            showEdit(req,resp);
            return;
        }
        if (Objects.equals(action, AppConstant.CREATE)) {
            showCreate(req, resp);
            return;
        }
        if (Objects.equals(action, AppConstant.DELETE)) {
            delete(req, resp);
            return;
        }
        showList(req, resp);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        errors.clear();
        String action = req.getParameter(AppConstant.ACTION);
        if (Objects.equals(action, AppConstant.CREATE)) {
            create(req, resp);
            return;
        }
        if (Objects.equals(action, AppConstant.EDIT)) {
            edit(req, resp);
            return;
        }
        showList(req, resp);
    }

    private void create(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Product product = getValidProduct(req,resp);
        if(errors.size() == 0){
            ProductService.getProductService().create(product);
            resp.sendRedirect("/products?message=Created");
        }

    }
    private void edit(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Product product = getValidProduct(req,resp);
        if(errors.size() == 0){
            ProductService.getProductService().edit(product);
            resp.sendRedirect("/products?message=Edited");
        }
    }

    private void showList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("products", ProductService.getProductService().getProducts());
        req.setAttribute("message", req.getParameter("message"));
        req.getRequestDispatcher(PAGE + AppConstant.LIST_PAGE).forward(req,resp);
    }

    private void showCreate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("productJSON", new ObjectMapper().writeValueAsString(new Product()));
        req.getRequestDispatcher(PAGE + AppConstant.CREATE_PAGE)
                .forward(req,resp);
    }
    private void showEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        if(checkIdNotFound(req, resp, id)) return;

        req.setAttribute("product", ProductService.getProductService().findById(id));
        req.setAttribute("productJSON", new ObjectMapper().writeValueAsString(ProductService.getProductService().findById(id)));
        req.getRequestDispatcher(PAGE + AppConstant.CREATE_PAGE)
                .forward(req,resp);
    }
    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        if(checkIdNotFound(req, resp, id)) return;
        ProductService.getProductService().delete(id);
        resp.sendRedirect(PAGE + "?message=Deleted");
    }

    private Product getValidProduct(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Product product = (Product) AppUtil.getObjectWithValidation(req, Product.class,  validators);
        if(errors.size() > 0){
            req.setAttribute("productJSON", new ObjectMapper().writeValueAsString(product));
            req.setAttribute("message","Something was wrong");
            req.getRequestDispatcher(PAGE + AppConstant.CREATE_PAGE)
                    .forward(req,resp);
        }
        return product;
    }

    private boolean checkIdNotFound(HttpServletRequest req, HttpServletResponse resp, Long id) throws IOException{
        if(!ProductService.getProductService().existById(id)){
            resp.sendRedirect(PAGE + "?message=Id not found");
            return true;
        }
        return false;
    }
}
