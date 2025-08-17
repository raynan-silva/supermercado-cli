package org.example.model;

import java.util.*;

public class Cart {
    private final Map<String, CartItem> items = new LinkedHashMap<>();

    public void add(Product p, int q) {
        items.compute(p.getId(), (k, v) -> {
            if (v == null) return new CartItem(p, q);
            v.add(q); return v;
        });
    }

    public List<CartItem> list() { return new ArrayList<>(items.values()); }
    public double total() { return items.values().stream().mapToDouble(CartItem::getSubtotal).sum(); }
    public boolean isEmpty() { return items.isEmpty(); }
    public void clear() { items.clear(); }
}

