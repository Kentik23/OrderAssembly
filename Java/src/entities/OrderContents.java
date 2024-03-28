package entities;

public class OrderContents {
    private int orderId;
    private int productId;
    private int count;

    public OrderContents(int orderId, int productId, int count) {
        this.orderId = orderId;
        this.productId = productId;
        this.count = count;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
