package infrastructure.dao;

import application.repository.RackRepository;
import entities.Rack;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RackDAO implements RackRepository {
    public List<Rack> mapByRows(ResultSet rs) throws SQLException {
        List<Rack> racks = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("Id");
            String name = rs.getString("Name");
            Rack rack = new Rack(id, name);
            racks.add(rack);
        }
        return racks;
    }
    @Override
    public List<Rack> findByIdIn(int[] ids) throws Exception {
        StringBuilder sql = new StringBuilder("SELECT * FROM racks WHERE Id IN (");
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
