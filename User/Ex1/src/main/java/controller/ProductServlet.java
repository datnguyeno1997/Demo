package controller;

import Util.AppConstant;
import Util.AppUtil;
import Util.RunnableCustom;
import Util.RunnableWithRegex;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Category;
import model.Product;
import service.CategoryService;
import service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@WebServlet(urlPatterns = "/products", name = "productServlet")
public class ProductServlet extends HttpServlet {
    private final String PAGE = "products"; // đặt hằng số

    private Map<String, RunnableCustom> validators;
    private CategoryService categoryService = new CategoryService();

    private final Map<String, String> errors = new HashMap<>(); // tạo map để validators add lỗi vào map này

    @Override
    public void init() {
        validators = new HashMap<>();

//        validators.put("phone", new RunnableWithRegex("[0-9]{10}", "phone", errors));
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
        errors.clear(); // clear lỗi cũ
        String action = req.getParameter(AppConstant.ACTION); // lấy action
        if (Objects.equals(action, AppConstant.CREATE)) {
            //kiểm tra xem action = create thi call create
            create(req, resp);
            return;
        }
        if (Objects.equals(action, AppConstant.EDIT)) {
            //kiểm tra xem action = create thi call edit
            edit(req, resp);
            return;
        }
        showList(req, resp);
    }

    private void create(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Product product = getValidProduct(req,resp); // lấy ra user và + xử lý cho việc validation của các field trong class User.
        if(errors.size() == 0){ //không xảy lỗi (errors size == 0) thì mình mới tạo user.
            ProductService.getProductService().create(product);
            resp.sendRedirect("/products?message=Created");
        }

    }
    private void edit(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Product product = getValidProduct(req,resp); // lấy ra user và + xử lý cho việc validation của các field trong class User.
        if(errors.size() == 0){ //không xảy lỗi (errors size == 0) thì mình mới sửa user.
            ProductService.getProductService().edit(product);
            resp.sendRedirect("/products?message=Edited");
        }
    }

    private void showList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("products", ProductService.getProductService().getProducts()); // gửi qua list users để jsp vẻ lên trang web
        req.setAttribute("message", req.getParameter("message")); // gửi qua message để toastr show thông báo
        List<Category> categories = categoryService.findAll();
        req.getRequestDispatcher(PAGE + AppConstant.LIST_PAGE).forward(req,resp);
    }

    private void showCreate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("productJSON", new ObjectMapper().writeValueAsString(new Product())); // gửi qua user rỗng để JS vẻ lên trang web
        List<Category> categories = categoryService.findAll();
        req.getRequestDispatcher(PAGE + AppConstant.CREATE_PAGE)
                .forward(req,resp);
    }
    private void showEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        if(checkIdNotFound(req, resp, id)) return;
        req.setAttribute("product", ProductService.getProductService().findById(id)); // gửi user để jsp check xem edit hay là create User
        req.setAttribute("productJSON", new ObjectMapper().writeValueAsString(ProductService.getProductService().findById(id))); // gửi qua user được tìm thấy bằng id để JS vẻ lên trang web
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
        Product product = (Product) AppUtil.getObjectWithValidation(req, Product.class,  validators); //

        if(errors.size() > 0){
            req.setAttribute("productJSON", new ObjectMapper().writeValueAsString(product)); //hiểu dòng đơn giản là muốn gửi data qua JS thì phải xài thằng này  new ObjectMapper().writeValueAsString(user).
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

