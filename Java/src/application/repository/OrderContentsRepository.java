package application.repository;

import entities.OrderContents;

import java.util.List;

public interface OrderContentsRepository {
    List<OrderContents> findByOrderIdIn(int[] ids) throws Exception;
}
