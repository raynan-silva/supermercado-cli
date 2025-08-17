package org.example.repo;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.example.core.FirebaseService;
import org.example.model.Product;


import java.util.*;
import java.util.concurrent.ExecutionException;

public class ProductRepository {
    private final Firestore db = FirebaseService.getDb();
    private final String COL = "products";

    public List<Product> listAll() {
        try {
            ApiFuture<QuerySnapshot> fut = db.collection(COL).get();
            List<QueryDocumentSnapshot> docs = fut.get().getDocuments();
            List<Product> out = new ArrayList<>();
            for (DocumentSnapshot d : docs) {
                Product p = d.toObject(Product.class);
                if (p != null) out.add(p);
            }
            return out;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Product> getById(String id) {
        try {
            DocumentSnapshot d = db.collection(COL).document(id).get().get();
            if (d.exists()) return Optional.ofNullable(d.toObject(Product.class));
            return Optional.empty();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public void seedIfEmpty() {
        if (!listAll().isEmpty()) return;
        List<Product> seed = List.of(
                new Product("arroz-1kg", "Arroz Tipo 1 1kg", "Mercearia", 6.99),
                new Product("feijao-1kg", "Feijão Carioca 1kg", "Mercearia", 7.49),
                new Product("leite-1l", "Leite Integral 1L", "Laticínios", 4.79),
                new Product("pao-frances", "Pão Francês 100g", "Padaria", 0.89),
                new Product("maça", "Maçã Gala 1kg", "Hortifruti", 8.90)
        );
        for (Product p : seed) save(p);
    }

    public void save(Product p) {
        try {
            db.collection(COL).document(p.getId()).set(p).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}

