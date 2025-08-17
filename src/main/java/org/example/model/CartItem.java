package org.example.model;

public class CartItem {
    private Product product;
    private int quantity;

    public CartItem() {}
    public CartItem(Product p, int q) { this.product = p; this.quantity = q; }

    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public double getSubtotal() { return product.getPrice() * quantity; }

    public void add(int q) { this.quantity += q; }
}
