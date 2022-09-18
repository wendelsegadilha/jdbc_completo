package xyz.wendelsegadilha.jdbc;

import xyz.wendelsegadilha.jdbc.model.Category;
import xyz.wendelsegadilha.jdbc.model.Product;
import xyz.wendelsegadilha.jdbc.repository.CategoryRepositoryImp;
import xyz.wendelsegadilha.jdbc.repository.ProductRepositoryImpl;
import xyz.wendelsegadilha.jdbc.repository.Repository;
import xyz.wendelsegadilha.jdbc.service.ProductCatalogService;
import xyz.wendelsegadilha.jdbc.service.ProductCatalogServiceImpl;
import xyz.wendelsegadilha.jdbc.util.DataBaseConnection;

import java.sql.*;
import java.util.Date;

public class ExampleJdbc {

    public static void main(String[] args) throws SQLException {

        ProductCatalogService service = new ProductCatalogServiceImpl();

        System.out.println("===============  LISTAGEM COMPLETA ====================");
        service.findAllProducts().forEach(System.out::println);

        System.out.println("===============  NOVA CATEGORIA ====================");
        Category category = new Category();
        category.setName("Escritório");

        System.out.println("===============  NOVO PRODUTO ====================");
        Product product = new Product();
        product.setName("Impressora HP");
        product.setPrice(450.00);
        product.setRegisterDate(new Date());
        product.setSku("gloty58963");

        service.saveProductWithCategory(product, category);
        service.findAllProducts().forEach(System.out::println);

    }
}
