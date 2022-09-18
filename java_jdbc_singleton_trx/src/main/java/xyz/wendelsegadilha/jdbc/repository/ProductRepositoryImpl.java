package xyz.wendelsegadilha.jdbc.repository;

import xyz.wendelsegadilha.jdbc.model.Category;
import xyz.wendelsegadilha.jdbc.model.Product;
import xyz.wendelsegadilha.jdbc.util.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryImpl implements Repository<Product>{

    private Connection getConnection() throws SQLException {
        return DataBaseConnection.getInstace();
    }

    @Override
    public List<Product> findAll() throws SQLException {
        List<Product> products = new ArrayList<>();
        try(Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("select p.*, c.nome as categoria from produtos as p inner join " +
                    "categorias as c on (p.categoria_id = c.id)")) {
            while (rs.next()){
                Product p = createProduct(rs);
                products.add(p);
            }
        }
        return products;
    }

    @Override
    public Product findById(Long id) throws SQLException {
        Product product = null;

        try(PreparedStatement stmt = getConnection().prepareStatement("select p.*, c.nome as categoria from " +
                "produtos as p inner join categorias as c on (p.categoria_id = c.id) where p.id = ?")) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    product = createProduct(rs);
                }
            }
        }
        return product;
    }

    @Override
    public void save(Product product) throws SQLException {
        String sql = null;
        if (product.getId() != null && product.getId() > 0){
            sql = "UPDATE produtos SET nome = ?, preco = ?, categoria_id= ?, sku = ? WHERE id = ?";
        }else {
            sql = "INSERT INTO produtos (nome, preco, categoria_id, sku, data_registro) VALUES (?, ?, ?, ?, ?)";
        }
        try(PreparedStatement stmt = getConnection().prepareStatement(sql)){
            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setLong(3, product.getCategory().getId());
            stmt.setString(4, product.getSku());
            if (product.getId() != null && product.getId() > 0){
                stmt.setLong(5, product.getId());
            }else {
                stmt.setDate(5, new Date(product.getRegisterDate().getTime()));
            }
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        try(PreparedStatement stmt = getConnection().prepareStatement("DELETE FROM produtos WHERE id = ?")) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    private static Product createProduct(ResultSet rs) throws SQLException {
        Product p = new Product();
        p.setId(rs.getLong("id"));
        p.setName(rs.getString("nome"));
        p.setPrice(rs.getDouble("preco"));
        p.setRegisterDate(rs.getDate("data_registro"));
        p.setSku(rs.getString("sku"));
        Category category = new Category();
        category.setId(rs.getLong("categoria_id"));
        category.setName(rs.getString("categoria"));
        p.setCategory(category);
        return p;
    }
}
