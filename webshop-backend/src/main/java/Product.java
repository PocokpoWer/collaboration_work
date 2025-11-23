public class Product {
    private long id;
    private String name;
    private MonetaryAmount price;
    private int stock;

    public Product(long id, String name, MonetaryAmount price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public MonetaryAmount getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return name + " (" + price + " Ft, stock: " + stock + ")";
    }
}