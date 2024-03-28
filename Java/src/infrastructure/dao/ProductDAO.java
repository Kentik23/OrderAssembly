package infrastructure.dao;

import application.repository.ProductRepository;
import entities.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO implements ProductRepository {
    public List<Product> mapByRows(ResultSet rs) throws SQLException {
        List<Product> products = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("Id");
            String name = rs.getString("Name");
            Product product = new Product(id, name);
            products.add(product);
        }
        return products;
    }
    @Override
    public List<Product> findByIdIn(int[] ids) throws Exception {
        StringBuilder sql = new StringBuilder("SELECT * FROM products WHERE Id IN (");
        for (int i = 0; i < ids.length; i++) {
            sql.append(ids[i]).append(',');
        }
        sql.setCharAt(sql.length() - 1, ')');
        sql.append(';');

        try (Connection con = ConnectionManager.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql.toString())) {
            return mapByRows(rs);
        }
    }
}
