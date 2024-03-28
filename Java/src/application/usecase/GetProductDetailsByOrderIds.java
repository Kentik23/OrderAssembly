package application.usecase;

import application.model.ProductDetails;

import java.util.List;

public interface GetProductDetailsByOrderIds {
    List<ProductDetails> execute(int[] ids) throws Exception;
}
