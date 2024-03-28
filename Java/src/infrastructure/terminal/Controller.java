package infrastructure.terminal;

import application.model.ProductDetails;
import application.usecase.GetProductDetailsByOrderIdsImpl;

import java.util.List;

public class Controller {
    private final GetProductDetailsByOrderIdsImpl getProductDetailsByOrderIds;
    private final AssemblyPageBuilder assemblyPageBuilder;

    public Controller(GetProductDetailsByOrderIdsImpl getProductDetailsByOrderIds, AssemblyPageBuilder assemblyPageBuilder) {
        this.getProductDetailsByOrderIds = getProductDetailsByOrderIds;
        this.assemblyPageBuilder = assemblyPageBuilder;
    }

    public String getOrderAssemblyPage(int[] ids) throws Exception {
        List<ProductDetails> productDetailsList = getProductDetailsByOrderIds.execute(ids);
        String orderAssemblyPage = assemblyPageBuilder.build(ids, productDetailsList);
        return orderAssemblyPage;
    }
}
