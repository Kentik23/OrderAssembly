import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    //Вспомогательный класс представляющий строку выборки из БД
    static class Row {
        private String rackName;
        private String productName;
        private int productId;
        private int orderId;
        private int Count;
        private boolean Main;

        public String getRackName() {
            return rackName;
        }

        public void setRackName(String rackName) {
            this.rackName = rackName;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public int getCount() {
            return Count;
        }

        public void setCount(int count) {
            Count = count;
        }

        public boolean isMain() {
            return Main;
        }

        public void setMain(boolean main) {
            Main = main;
        }
    }

    //Получение соединения с БД
    private Connection getConnection() throws SQLException {
        final String URL = "jdbc:mysql://localhost:3306/assembling_orders_db";
        final String USERNAME = "root";
        final String PASSWORD = "2332";

        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        return connection;
    }

    //Формирование запроса
    private String getQuery(int[] orderNumbers) {
        StringBuilder query = new StringBuilder("SELECT racks.Name, products.Name, products.Id, orders.Id, ordercontents.Count, rackcontents.Main FROM orders\n" +
                "    JOIN ordercontents ON orders.Id = ordercontents.OrderId\n" +
                "    JOIN products ON ordercontents.ProductID = products.Id\n" +
                "    JOIN rackcontents ON products.Id = rackcontents.ProductID\n" +
                "    JOIN racks ON rackcontents.RackId = racks.Id\n" +
                "WHERE orders.Id IN ");

        query.append('(');
        for (int i = 0; i < orderNumbers.length - 1; i++) {
            query.append(orderNumbers[i]).append(',');
        }
        query.append(orderNumbers[orderNumbers.length - 1]).append(')').append(';');

        return query.toString();
    }

    //Создание списка строк с информацией по товарам для сборки
    private List<Row> getAllRows(ResultSet rs) throws SQLException {
        List<Row> allRows = new ArrayList<>();
        while (rs.next()) {
            Row row = new Row();
            row.setRackName(rs.getString("racks.Name"));
            row.setProductName(rs.getString("products.Name"));
            row.setProductId(rs.getInt("products.Id"));
            row.setOrderId(rs.getInt("orders.Id"));
            row.setCount(rs.getInt("Count"));
            row.setMain(rs.getBoolean("Main"));
            allRows.add(row);
        }
        return allRows;
    }

    public String getAssemblingPage(int[] orderNumbers) throws SQLException {
        try (Connection con = this.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(getQuery(orderNumbers))) {

            StringBuilder assemblingPage = new StringBuilder("=+=+=+=\nСтраница сборки заказов ").append(Arrays.toString(orderNumbers)).append('\n').append('\n');

            List<Row> allRows = getAllRows(rs);
            // Строки содержащие информацию о товарах, расположенных на главном стеллаже
            List<Row> mainRows = allRows.stream().filter(Row::isMain).toList();
            // Строки содержащие информацию о товарах, расположенных на дополнительном стеллаже
            List<Row> additionalRows = allRows.stream().filter(x -> !x.isMain()).toList();

            String rackName = "";
            for(Row row : mainRows) {
                //Вывод названия стеллажа, если он встречается впервые
                if (!rackName.equals(row.getRackName())) {
                    rackName = row.getRackName();
                    assemblingPage.append("===Стеллаж ").append(row.getRackName()).append('\n');
                }

                //вывод информации по товару
                assemblingPage.append(row.getProductName()).append(" (id=").append(row.getProductId()).append(')').append('\n');

                //вывод информации по заказу
                assemblingPage.append("заказ ").append(row.getOrderId()).append(", ").append(row.getCount()).append(" шт").append('\n');

                //создание списка с дополнительными стеллажами для данного товара
                List<String> additionalRacks = additionalRows.stream().filter(x -> x.productId == row.getProductId()).map(Row::getRackName).toList();
                //Если списон не пустой, то указываем названия стеллажей
                if (!additionalRacks.isEmpty()) {
                    assemblingPage.append("доп стеллаж: ");
                    for (String rack : additionalRacks)
                        assemblingPage.append(rack).append(',');
                    assemblingPage.deleteCharAt(assemblingPage.length() - 1).append('\n');
                }
                assemblingPage.append('\n');
            }

            return assemblingPage.toString();
        }
    }

    public static void main(String[] args) {
        // Преобразование аргумента с номерами заказов в массив int
        String[] orderNumbersStrs = args[0].split(",");
        int[] orderNumbers = new int[orderNumbersStrs.length];
        for(int i = 0; i < orderNumbers.length; i++) {
            orderNumbers[i] = Integer.parseInt(orderNumbersStrs[i]);
        }

        //Создание экземпляра класса и вывод страницы со сборкой заказов
        Main main = new Main();

        try {
            System.out.println(main.getAssemblingPage(orderNumbers));
        } catch (SQLException e) {
            System.out.println("Ошибка создания страницы сборки заказов");
        }
    }
}
