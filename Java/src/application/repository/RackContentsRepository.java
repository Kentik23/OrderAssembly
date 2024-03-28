package application.repository;

import entities.RackContents;

import java.util.List;

public interface RackContentsRepository {
    List<RackContents> findByProductIdIn(int[] ids) throws Exception;
}
