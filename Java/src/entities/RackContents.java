package entities;

public class RackContents {
    private int rackId;
    private int productId;
    private boolean main;

    public RackContents(int rackId, int productId, boolean main) {
        this.rackId = rackId;
        this.productId = productId;
        this.main = main;
    }

    public int getRackId() {
        return rackId;
    }

    public void setRackId(int rackId) {
        this.rackId = rackId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public boolean isMain() {
        return main;
    }

    public void setMain(boolean main) {
        this.main = main;
    }
}
