package xyz.wendelsegadilha.jdbc;

import xyz.wendelsegadilha.jdbc.model.Category;
import xyz.wendelsegadilha.jdbc.model.Product;
import xyz.wendelsegadilha.jdbc.repository.ProductRepositoryImpl;
import xyz.wendelsegadilha.jdbc.repository.Repository;
import xyz.wendelsegadilha.jdbc.util.DataBaseConnection;

import java.sql.*;
import java.util.Date;

public class ExampleJdbc {

    public static void main(String[] args) {

        try(Connection conn = DataBaseConnection.getInstace()){
            Repository<Product> repository = new ProductRepositoryImpl();

            System.out.println("===============  LISTAGEM COMPLETA ====================");
            repository.findAll().forEach(System.out::println);

            System.out.println("===============  LISTAGEM POR ID ====================");
            System.out.println(repository.findById(1L));

            System.out.println("===============  NOVO PRODUTO ====================");
            Product product = new Product();
            product.setName("Luva de Box");
            product.setPrice(145.00);
            product.setRegisterDate(new Date());
            Category category = new Category();
            category.setId(2L);
            product.setCategory(category);
            repository.save(product);
            repository.findAll().forEach(System.out::println);
            System.out.println("success product save");
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
