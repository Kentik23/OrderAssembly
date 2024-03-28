package application.usecase;

import entities.OrderContents;
import entities.Product;
import entities.Rack;
import application.repository.*;
import application.model.ProductDetails;
import entities.RackContents;

import java.util.ArrayList;
import java.util.List;

public class GetProductDetailsByOrderIdsImpl implements GetProductDetailsByOrderIds {
    private final OrderContentsRepository orderContentsRepository;
    private final ProductRepository productRepository;
    private final RackContentsRepository rackContentsRepository;
    private final RackRepository rackRepository;

    public GetProductDetailsByOrderIdsImpl(OrderContentsRepository orderContentsRepository, ProductRepository productRepository,
                                           RackContentsRepository rackContentsRepository, RackRepository rackRepository) {
        this.orderContentsRepository = orderContentsRepository;
        this.productRepository = productRepository;
        this.rackContentsRepository = rackContentsRepository;
        this.rackRepository = rackRepository;
    }

    @Override
    public List<ProductDetails> execute(int[] ids) throws Exception {
        List<OrderContents> orderContentsList = orderContentsRepository.findByOrderIdIn(ids);

        int[] productsIds = orderContentsList.stream().map(OrderContents::getProductId).mapToInt(i -> i).toArray();
        List<Product> products = productRepository.findByIdIn(productsIds);

        List<RackContents> rackContentsList = rackContentsRepository.findByProductIdIn(productsIds);

        int[] racksIds = rackContentsList.stream().map(RackContents::getRackId).mapToInt(i -> i).toArray();
        List<Rack> racks = rackRepository.findByIdIn(racksIds);

        List<ProductDetails> productDetailsList = new ArrayList<>();
        for (OrderContents orderContents : orderContentsList) {
            int productId = orderContents.getProductId();

            System.out.println(productId);

            String productName = products.stream().filter(x -> x.getId() == productId).map(Product::getName).findFirst().get();
            int orderId = orderContents.getOrderId();
            int count = orderContents.getCount();

            List<RackContents> productRackContentsList = rackContentsList.stream().filter(x -> x.getProductId() == productId).toList();

            List<Rack> productRacks = new ArrayList<>();

            productRackContentsList.stream().map(RackContents::getRackId).forEach(x -> productRacks.add(racks.stream().filter(y -> y.getId() == x).findFirst().get()));

            int mainRackId = productRackContentsList.stream().filter(RackContents::isMain).map(RackContents::getRackId).findFirst().get();
            Rack mainRack = productRacks.stream().filter(x -> x.getId() == mainRackId).findFirst().get();

            List<Rack> additionalRacks = productRacks.stream().filter(x -> x.getId() != mainRackId).toList();

            productDetailsList.add(new ProductDetails(productId, productName, orderId, mainRack, additionalRacks, count));
        }
        return productDetailsList;
    }
}
