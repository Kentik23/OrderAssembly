package infrastructure.dao;

import application.repository.RackContentsRepository;
import entities.RackContents;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RackContentsDAO implements RackContentsRepository {
    public List<RackContents> mapByRows(ResultSet rs) throws SQLException {
        List<RackContents> rackContentsList = new ArrayList<>();
        while (rs.next()) {
            int rackId = rs.getInt("RackId");
            int productId = rs.getInt("ProductID");
            boolean main = rs.getBoolean("Main");

            RackContents rackContents = new RackContents(rackId, productId, main);
            rackContentsList.add(rackContents);
        }
        return rackContentsList;
    }
    public List<RackContents> findByProductIdIn(int[] ids) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT * FROM rackcontents WHERE RackId IN (");
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
