package xyz.wendelsegadilha.jdbc;

import xyz.wendelsegadilha.jdbc.model.Category;
import xyz.wendelsegadilha.jdbc.model.Product;
import xyz.wendelsegadilha.jdbc.repository.ProductRepositoryImpl;
import xyz.wendelsegadilha.jdbc.repository.Repository;
import xyz.wendelsegadilha.jdbc.util.DataBaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

public class ExampleJdbcTrx {

    public static void main(String[] args) throws SQLException {

        try(Connection conn = DataBaseConnection.getInstace()) {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            try {
                Repository<Product> repository = new ProductRepositoryImpl();

                System.out.println("===============  LISTAGEM COMPLETA ====================");
                repository.findAll().forEach(System.out::println);

                System.out.println("===============  LISTAGEM POR ID ====================");
                System.out.println(repository.findById(1L));

                System.out.println("===============  NOVO PRODUTO ====================");
                Product product = new Product();
                product.setName("Bola Futebol");
                product.setPrice(58.00);
                product.setRegisterDate(new Date());
                product.setSku("abcde12345");
                Category category = new Category();
                category.setId(2L);
                product.setCategory(category);
                repository.save(product);
                repository.findAll().forEach(System.out::println);
                System.out.println("success product save");

                System.out.println("===============  ATUALIZA PRODUTO ====================");
                product = new Product();
                product.setId(4L);
                product.setName("Teclado Razer K58");
                product.setPrice(142.57);
                product.setSku("abcd9874");
                category = new Category();
                category.setId(4L);
                product.setCategory(category);
                repository.save(product);
                System.out.println("success product update");
                repository.findAll().forEach(System.out::println);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();

            }
        }
    }
}
