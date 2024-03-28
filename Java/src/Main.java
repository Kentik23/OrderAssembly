import application.usecase.GetProductDetailsByOrderIdsImpl;
import infrastructure.dao.OrderContentsDAO;
import infrastructure.dao.ProductDAO;
import infrastructure.dao.RackContentsDAO;
import infrastructure.dao.RackDAO;
import infrastructure.terminal.AssemblyPageBuilder;
import infrastructure.terminal.Controller;

public class Main {
    public static void main(String[] args) {
        OrderContentsDAO orderContentsDAO = new OrderContentsDAO();
        ProductDAO productDAO = new ProductDAO();
        RackContentsDAO rackContentsDAO = new RackContentsDAO();
        RackDAO rackDAO = new RackDAO();
        GetProductDetailsByOrderIdsImpl getProductDetailsByOrderIds = new GetProductDetailsByOrderIdsImpl(orderContentsDAO, productDAO, rackContentsDAO, rackDAO);
        AssemblyPageBuilder assemblyPageBuilder = new AssemblyPageBuilder();
        Controller controller = new Controller(getProductDetailsByOrderIds, assemblyPageBuilder);


        // Преобразование аргумента с номерами заказов в массив int
        String[] orderNumbersStrs = args[0].split(",");
        int[] orderNumbers = new int[orderNumbersStrs.length];
        for(int i = 0; i < orderNumbers.length; i++) {
            orderNumbers[i] = Integer.parseInt(orderNumbersStrs[i]);
        }

        // Вызов метода контроллера
        try {
            System.out.println(controller.getOrderAssemblyPage(orderNumbers));
        } catch (Exception e) {
            System.out.println("Ошибка создания страницы сборки заказов:");
            e.printStackTrace(System.out);
        }
    }
}
