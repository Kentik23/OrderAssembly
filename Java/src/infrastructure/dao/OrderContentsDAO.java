package infrastructure.dao;

import application.repository.OrderContentsRepository;
import entities.OrderContents;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderContentsDAO implements OrderContentsRepository {
    public List<OrderContents> mapByRows(ResultSet rs) throws SQLException {
        List<OrderContents> orderContentsList = new ArrayList<>();
        while (rs.next()) {
            int orderId = rs.getInt("OrderId");
            int productId = rs.getInt("ProductId");
            int count = rs.getInt("Count");

            OrderContents orderContents = new OrderContents(orderId, productId, count);
            orderContentsList.add(orderContents);
        }
        return orderContentsList;
    }
    public List<OrderContents> findByOrderIdIn(int[] ids) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT * FROM ordercontents WHERE OrderId IN (");
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
