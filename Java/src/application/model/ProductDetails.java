package application.model;

import entities.Product;
import entities.Rack;

import java.util.List;

public class ProductDetails extends Product {
    private int orderId;
    private Rack mainRack;
    private List<Rack> additionalRacks;
    private int count;

    public ProductDetails(int id, String name, int orderId, Rack mainRack, List<Rack> additionalRacks, int count) {
        super(id, name);
        this.orderId = orderId;
        this.mainRack = mainRack;
        this.additionalRacks = additionalRacks;
        this.count = count;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Rack getMainRack() {
        return mainRack;
    }

    public void setMainRack(Rack mainRack) {
        this.mainRack = mainRack;
    }

    public List<Rack> getAdditionalRacks() {
        return additionalRacks;
    }

    public void setAdditionalRacks(List<Rack> additionalRacks) {
        this.additionalRacks = additionalRacks;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
