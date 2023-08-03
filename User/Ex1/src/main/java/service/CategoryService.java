package service;

import dao.CategoryDao;
import model.Category;

import java.util.List;

public class CategoryService implements ICategoryService {

    private CategoryDao categoryDao = new CategoryDao();

    @Override
    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    @Override
    public Category findById(int ID) {
        return categoryDao.findById(ID);
    }
}
