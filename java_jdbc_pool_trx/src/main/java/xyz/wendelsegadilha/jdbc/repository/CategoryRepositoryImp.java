package xyz.wendelsegadilha.jdbc.repository;

import xyz.wendelsegadilha.jdbc.model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepositoryImp implements Repository<Category>{

    private Connection conn;

    public CategoryRepositoryImp() {
    }

    public CategoryRepositoryImp(Connection conn) {
        this.conn = conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Category> findAll() throws SQLException {
        List<Category> categories = new ArrayList<>();
        try(Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM categorias")) {
            while (rs.next()) {
                Category c = createCategory(rs);
                categories.add(c);
            }
        }
        return categories;
    }

    @Override
    public Category findById(Long id) throws SQLException {
        Category category = null;
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM categorias WHERE id = ?")) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()){
                    category = createCategory(rs);
                }
            }
        }
        return category;
    }

    @Override
    public Category save(Category category) throws SQLException {
        String sql = null;
        if (category.getId() != null && category.getId() > 0){
            sql = "UPDATE categorias SET nome = ? WHERE id = ?";
        } else {
            sql = "INSERT INTO categorias(nome) VALUES(?)";
        }
        try(PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, category.getName());
            if (category.getId() != null && category.getId() > 0){
                stmt.setLong(2, category.getId());
            }
            stmt.executeUpdate();
            if (category.getId() == null){
                try(ResultSet rs = stmt.getGeneratedKeys()){
                    if (rs.next()){
                        category.setId(rs.getLong(1));
                    }
                }
            }
        }
        return category;
    }

    @Override
    public void delete(Long id) throws SQLException {
        try(PreparedStatement stmt = conn.prepareStatement("DELETE FROM categorias WHERE id = ?")) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    private static Category createCategory(ResultSet rs) throws SQLException {
        Category c = new Category();
        c.setId(rs.getLong("id"));
        c.setName(rs.getString("nome"));
        return c;
    }
}
