package xyz.wendelsegadilha.jdbc.service;

import xyz.wendelsegadilha.jdbc.model.Category;
import xyz.wendelsegadilha.jdbc.model.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductCatalogService {

    List<Product> findAllProducts() throws SQLException;

    Product findByIdProduct(Long id) throws SQLException;

    Product saveProduct(Product product) throws SQLException;

    void deleteProduct(Long id) throws SQLException;

    List<Category> findAllCategories() throws SQLException;

    Category findByIdCategory(Long id) throws SQLException;

    Category saveCategory(Category category) throws SQLException;

    void deleteCategory(Long id) throws SQLException;

    void saveProductWithCategory(Product product, Category category) throws SQLException;
}
