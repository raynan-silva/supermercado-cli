package org.example.repo;


import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.example.core.FirebaseService;
import org.example.model.Cart;
import org.example.model.CartItem;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class CartRepository {
    private final Firestore db = FirebaseService.getDb();
    // users/{uid}/cart/{productId}
    private CollectionReference cartCol(String uid) {
        return db.collection("users").document(uid).collection("cart");
    }

    public void saveCart(String uid, Cart cart) {
        // Simples: sobrescreve cada item (id = productId). Remove itens ausentes.
        try {
            // Apaga carrinho atual
            for (DocumentSnapshot d : cartCol(uid).get().get().getDocuments()) {
                d.getReference().delete().get();
            }
            // Grava itens atuais
            for (CartItem it : cart.list()) {
                Map<String, Object> doc = new HashMap<>();
                doc.put("productId", it.getProduct().getId());
                doc.put("name", it.getProduct().getName());
                doc.put("price", it.getProduct().getPrice());
                doc.put("quantity", it.getQuantity());
                doc.put("subtotal", it.getSubtotal());
                cartCol(uid).document(it.getProduct().getId()).set(doc).get();
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public double computeTotal(String uid) {
        try {
            double total = 0.0;
            for (DocumentSnapshot d : cartCol(uid).get().get().getDocuments()) {
                Number sub = (Number) d.get("subtotal");
                if (sub != null) total += sub.doubleValue();
            }
            return total;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUserIfNew(String uid, String email) {
        try {
            DocumentReference ref = db.collection("users").document(uid);
            if (!ref.get().get().exists()) {
                Map<String, Object> data = new HashMap<>();
                data.put("email", email);
                data.put("createdAt", FieldValue.serverTimestamp());
                ref.set(data).get();
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}

