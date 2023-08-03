package service;

import Util.AppConstant;
import dao.UserDAO;
import model.User;
import service.dto.PageableRequest;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    private static List<User> users;

    private static Long currentId = 0L;

    private static final UserService userService;

    private UserDAO userDAO = new UserDAO();

    static {
        users = new ArrayList<>();
//        users.add(new User(++currentId, "Vinh", "Vinh2","Vinh3","Vinh4", EGender.MALE,  Date.valueOf("1994-07-29"), "Demo"));
//        users.add(new User(++currentId, "Vinh", "Vinh2","Vinh3","Vinh4", EGender.MALE,  Date.valueOf("1994-07-29"), "Demo"));
//        users.add(new User(++currentId, "Vinh", "Vinh2","Vinh3","Vinh4", EGender.MALE,  Date.valueOf("1994-07-29"), "Demo"));
//        users.add(new User(++currentId, "Vinh", "Vinh2","Vinh3","Vinh4", EGender.MALE,  Date.valueOf("1994-07-29"), "Demo"));
        userService = new UserService();
    }

    public List<User> getUsers(PageableRequest request){
        return userDAO.findAll(request);
    }
    public User findById(Long id){
        return userDAO.findById(id)
                .orElseThrow(() ->  new RuntimeException(String.format(AppConstant.ID_NOT_FOUND, "User")));

//        return users.stream()
//                .filter(user -> Objects.equals(user.getId(), id)) // lọc qua list users với điều kiện là user id == id truyền vào
//                .findFirst() // lấy phần tử tìm thấy đầu tiên
//                .orElseThrow(() ->  new RuntimeException(String.format(AppConstant.ID_NOT_FOUND, "User")));
//            //nếu không tìm thấy thì trả ra lỗi
    }
    public void create(User user){
        userDAO.insertUser(user);
    }

    public static UserService getUserService() {
        return userService;
    }
    private UserService(){}

    public void edit(User user) {
        userDAO.updateUser(user);
    }

    public boolean existById(Long id) {
        return userDAO.findById(id).orElse(null) != null;
    }

    public void delete(Long userId) {
        userDAO.deleteById(userId);
//        users = users
//                .stream()
//                .filter(user -> !Objects.equals(user.getId(), userId))
//                .collect(Collectors.toList());
    }
}