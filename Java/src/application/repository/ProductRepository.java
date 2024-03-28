package application.repository;

import entities.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> findByIdIn(int[] ids) throws Exception;
}
