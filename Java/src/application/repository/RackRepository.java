package application.repository;

import entities.Rack;

import java.util.List;

public interface RackRepository {
    List<Rack> findByIdIn(int[] ids) throws Exception;
}
