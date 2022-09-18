package xyz.wendelsegadilha.jdbc;

import xyz.wendelsegadilha.jdbc.model.Product;
import xyz.wendelsegadilha.jdbc.repository.ProductRepositoryImpl;
import xyz.wendelsegadilha.jdbc.repository.Repository;
import xyz.wendelsegadilha.jdbc.util.DataBaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class ExampleDeleteJdbc {

    public static void main(String[] args) {

        try(Connection conn = DataBaseConnection.getInstace()){
            Repository<Product> repository = new ProductRepositoryImpl();

            System.out.println("===============  LISTAGEM COMPLETA ====================");
            repository.findAll().forEach(System.out::println);

            System.out.println("===============  LISTAGEM POR ID ====================");
            System.out.println(repository.findById(1L));

            System.out.println("===============  DELETE PRODUTO ====================");
            repository.delete(3L);
            System.out.println("success product delete");
            repository.findAll().forEach(System.out::println);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
