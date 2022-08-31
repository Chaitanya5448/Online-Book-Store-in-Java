package online.book.store.service;

import online.book.store.dao.CategoryDao;
import online.book.store.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Component
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public List<Category> popularCategories() {

        return this.categoryDao.popularCategories();
    }

    @Override
    public List<Category> allCategories() {
        return this.categoryDao.allCategory();
    }

}
