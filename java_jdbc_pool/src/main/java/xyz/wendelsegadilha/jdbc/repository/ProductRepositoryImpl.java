package xyz.wendelsegadilha.jdbc.repository;

import xyz.wendelsegadilha.jdbc.model.Category;
import xyz.wendelsegadilha.jdbc.model.Product;
import xyz.wendelsegadilha.jdbc.util.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryImpl implements Repository<Product> {

    private Connection getConnection() throws SQLException {
        return DataBaseConnection.getConnection();
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("select p.*, c.nome as categoria from produtos as p inner join " +
                     "categorias as c on (p.categoria_id = c.id)")) {
            while (rs.next()) {
                Product p = createProduct(rs);
                products.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public Product findById(Long id) {
        Product product = null;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("select p.*, c.nome as categoria from " +
                     "produtos as p inner join categorias as c on (p.categoria_id = c.id) where p.id = ?")) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    product = createProduct(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public void save(Product product) {
        String sql = null;
        if (product.getId() != null && product.getId() > 0) {
            sql = "UPDATE produtos SET nome = ?, preco = ?, categoria_id= ? WHERE id = ?";
        } else {
            sql = "INSERT INTO produtos (nome, preco, categoria_id, data_registro) VALUES (?, ?, ?, ?)";
        }
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setLong(3, product.getCategory().getId());
            if (product.getId() != null && product.getId() > 0) {
                stmt.setLong(4, product.getId());
            } else {
                stmt.setDate(4, new Date(product.getRegisterDate().getTime()));
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM produtos WHERE id = ?")) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Product createProduct(ResultSet rs) throws SQLException {
        Product p = new Product();
        p.setId(rs.getLong("id"));
        p.setName(rs.getString("nome"));
        p.setPrice(rs.getDouble("preco"));
        p.setRegisterDate(rs.getDate("data_registro"));
        Category category = new Category();
        category.setId(rs.getLong("categoria_id"));
        category.setName(rs.getString("categoria"));
        p.setCategory(category);
        return p;
    }
}
