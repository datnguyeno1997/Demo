package service;

import dao.RoleDAO;
import model.Role;

import java.util.List;

public class RoleService {
    public static List<Role> getRoles(){
        return new RoleDAO().getRoles();
    }
}
