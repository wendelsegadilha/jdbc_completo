package xyz.wendelsegadilha.jdbc;

import xyz.wendelsegadilha.jdbc.model.Category;
import xyz.wendelsegadilha.jdbc.model.Product;
import xyz.wendelsegadilha.jdbc.repository.ProductRepositoryImpl;
import xyz.wendelsegadilha.jdbc.repository.Repository;
import xyz.wendelsegadilha.jdbc.util.DataBaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

public class ExampleUpdateJdbc {

    public static void main(String[] args) {

        try(Connection conn = DataBaseConnection.getInstace()){
            Repository<Product> repository = new ProductRepositoryImpl();

            System.out.println("===============  LISTAGEM COMPLETA ====================");
            repository.findAll().forEach(System.out::println);

            System.out.println("===============  LISTAGEM POR ID ====================");
            System.out.println(repository.findById(1L));

            System.out.println("===============  ATUALIZA PRODUTO ====================");
            Product product = new Product();
            product.setId(4L);
            product.setName("Teclado Razer K58");
            product.setPrice(142.57);
            Category category = new Category();
            category.setId(4L);
            product.setCategory(category);
            repository.save(product);
            System.out.println("success product update");
            repository.findAll().forEach(System.out::println);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
