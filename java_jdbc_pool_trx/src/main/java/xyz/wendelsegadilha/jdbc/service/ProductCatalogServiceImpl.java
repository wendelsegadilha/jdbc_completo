package xyz.wendelsegadilha.jdbc.service;

import xyz.wendelsegadilha.jdbc.model.Category;
import xyz.wendelsegadilha.jdbc.model.Product;
import xyz.wendelsegadilha.jdbc.repository.CategoryRepositoryImp;
import xyz.wendelsegadilha.jdbc.repository.ProductRepositoryImpl;
import xyz.wendelsegadilha.jdbc.repository.Repository;
import xyz.wendelsegadilha.jdbc.util.DataBaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ProductCatalogServiceImpl implements ProductCatalogService {

    private final Repository<Product> productRepository;
    private final Repository<Category> categoryRepository;

    public ProductCatalogServiceImpl() {
        this.productRepository = new ProductRepositoryImpl();
        this.categoryRepository = new CategoryRepositoryImp();
    }

    @Override
    public List<Product> findAllProducts() throws SQLException {
        try (Connection conn = DataBaseConnection.getConnection()) {
            productRepository.setConn(conn);
            return productRepository.findAll();
        }
    }

    @Override
    public Product findByIdProduct(Long id) throws SQLException {
        try (Connection conn = DataBaseConnection.getConnection()) {
            productRepository.setConn(conn);
            return productRepository.findById(id);
        }
    }

    @Override
    public Product saveProduct(Product product) throws SQLException {
        try (Connection conn = DataBaseConnection.getConnection()) {
            productRepository.setConn(conn);
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            try {
                product = productRepository.save(product);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
            return product;
        }
    }

    @Override
    public void deleteProduct(Long id) throws SQLException {
        try (Connection conn = DataBaseConnection.getConnection()) {
            productRepository.setConn(conn);
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            try {
                productRepository.delete(id);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        }

    }

    @Override
    public List<Category> findAllCategories() throws SQLException {
        try (Connection conn = DataBaseConnection.getConnection()) {
            categoryRepository.setConn(conn);
            return categoryRepository.findAll();
        }
    }

    @Override
    public Category findByIdCategory(Long id) throws SQLException {
        try (Connection conn = DataBaseConnection.getConnection()) {
            categoryRepository.setConn(conn);
            return categoryRepository.findById(id);
        }
    }

    @Override
    public Category saveCategory(Category category) throws SQLException {
        try (Connection conn = DataBaseConnection.getConnection()) {
            categoryRepository.setConn(conn);
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            try {
                category = categoryRepository.save(category);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
            return category;
        }
    }

    @Override
    public void deleteCategory(Long id) throws SQLException {
        try (Connection conn = DataBaseConnection.getConnection()) {
            categoryRepository.setConn(conn);
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            try {
                categoryRepository.delete(id);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        }

    }

    @Override
    public void saveProductWithCategory(Product product, Category category) throws SQLException {
        try (Connection conn = DataBaseConnection.getConnection()) {
            categoryRepository.setConn(conn);
            productRepository.setConn(conn);
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            try {
                category = categoryRepository.save(category);
                product.setCategory(category);
                productRepository.save(product);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        }

    }
}
