package controller;

import Util.AppConstant;
import Util.AppUtil;
import Util.RunnableCustom;
import Util.RunnableWithRegex;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.enums.EGender;
import model.Role;
import model.User;
import service.RoleService;
import service.UserService;
import service.dto.PageableRequest;
import service.dto.enums.ESortType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@WebServlet(urlPatterns = "/users", name = "userServlet")
public class UserServlet extends HttpServlet {
    private final String PAGE = "users"; // đặt hằng số

    private Map<String, RunnableCustom> validators;

    private final Map<String, String> errors = new HashMap<>(); // tạo map để validators add lỗi vào map này

    @Override
    public void init() {
        validators = new HashMap<>();
        // tạo validator với name field là phone, và nó validate theo Regex Pattern
        // tạo tất các validator cho all fields.
        // mình có thế xài cái thằng khác
        validators.put("phone", new RunnableWithRegex("[0-9]{10}", "phone", errors));
        //validators.put("dob", new RunnableWithRegex("[0-9]{10}", "dob", errors));
        //định nghĩa tất cả các fields
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
        User user = getValidUser(req,resp); // lấy ra user và + xử lý cho việc validation của các field trong class User.
        if(errors.size() == 0){ //không xảy lỗi (errors size == 0) thì mình mới tạo user.
            UserService.getUserService().create(user);
            resp.sendRedirect("/users?message=Created");
        }

    }
    private void edit(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User user = getValidUser(req,resp); // lấy ra user và + xử lý cho việc validation của các field trong class User.
        if(errors.size() == 0){ //không xảy lỗi (errors size == 0) thì mình mới sửa user.
            UserService.getUserService().edit(user);
            resp.sendRedirect("/users?message=Edited");
        }
    }

    private void showList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PageableRequest request = new PageableRequest(
                req.getParameter("search"),
                req.getParameter("sortField"),
                ESortType.valueOf(AppUtil.getParameterWithDefaultValue(req,"sortType", ESortType.DESC).toString())
        ); //tao doi tuong pageable voi parametter search

        req.setAttribute("pageable", request);
        req.setAttribute("users", UserService.getUserService().getUsers(request)); // gửi qua list users để jsp vẻ lên trang web
        req.setAttribute("usersJSON", new ObjectMapper().writeValueAsString(UserService.getUserService().getUsers(request)));
        req.setAttribute("message", req.getParameter("message")); // gửi qua message để toastr show thông báo
        req.setAttribute("genderJSON", new ObjectMapper().writeValueAsString(EGender.values()));
        req.setAttribute("rolesJSON", new ObjectMapper().writeValueAsString(RoleService.getRoles()));
        req.getRequestDispatcher(PAGE + AppConstant.LIST_PAGE).forward(req,resp);
    }

    private void showCreate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("userJSON", new ObjectMapper().writeValueAsString(new User())); // gửi qua user rỗng để JS vẻ lên trang web
        req.setAttribute("genderJSON", new ObjectMapper().writeValueAsString(EGender.values()));
        req.setAttribute("rolesJSON", new ObjectMapper().writeValueAsString(RoleService.getRoles()));
        req.getRequestDispatcher(PAGE + AppConstant.CREATE_PAGE)
                .forward(req,resp);
    }
    private void showEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        if(checkIdNotFound(req, resp, id)) return;
        req.setAttribute("genderJSON", new ObjectMapper().writeValueAsString(EGender.values()));
        req.setAttribute("user", UserService.getUserService().findById(id)); // gửi user để jsp check xem edit hay là create User
        req.setAttribute("userJSON", new ObjectMapper().writeValueAsString(UserService.getUserService().findById(id))); // gửi qua user được tìm thấy bằng id để JS vẻ lên trang web
        req.setAttribute("rolesJSON", new ObjectMapper().writeValueAsString(RoleService.getRoles()));
        req.getRequestDispatcher(PAGE + AppConstant.CREATE_PAGE)
                .forward(req,resp);

    }
    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        if(checkIdNotFound(req, resp, id)) return;
        UserService.getUserService().delete(id);
        resp.sendRedirect(PAGE + "?message=Deleted");
    }

    private User getValidUser(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User user = (User) AppUtil.getObjectWithValidation(req, User.class,  validators); //
        user.setRole(new Role(Long.valueOf(req.getParameter("role_id"))));
        if(errors.size() > 0){
            req.setAttribute("userJSON", new ObjectMapper().writeValueAsString(user)); //hiểu dòng đơn giản là muốn gửi data qua JS thì phải xài thằng này  new ObjectMapper().writeValueAsString(user).
            req.setAttribute("rolesJSON", new ObjectMapper().writeValueAsString(RoleService.getRoles()));
            req.setAttribute("genderJSON", new ObjectMapper().writeValueAsString(EGender.values()));
            req.setAttribute("message","Something was wrong");
            req.getRequestDispatcher(PAGE + AppConstant.CREATE_PAGE)
                    .forward(req,resp);
        }
        return user;
    }

    private boolean checkIdNotFound(HttpServletRequest req, HttpServletResponse resp, Long id) throws IOException{
        if(!UserService.getUserService().existById(id)){
            resp.sendRedirect(PAGE + "?message=Id not found");
            return true;
        }
        return false;
    }
}