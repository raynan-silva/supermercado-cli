package org.example;

import org.example.auth.AuthClient;
import org.example.auth.AuthResponse;
import org.example.model.Cart;
import org.example.model.Product;
import org.example.repo.CartRepository;
import org.example.repo.ProductRepository;
import org.example.util.Console;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class Main {

    public static void main(String[] args) throws Exception {
        // 1) Configurar sua WEB API KEY do Firebase (Project Settings → General)
        final String WEB_API_KEY = System.getenv("FIREBASE_WEB_API_KEY");
        if (WEB_API_KEY == null || WEB_API_KEY.isBlank()) {
            System.out.println("Defina a variável de ambiente FIREBASE_WEB_API_KEY com sua Web API Key.");
            return;
        }

        AuthClient auth = new AuthClient(WEB_API_KEY);
        ProductRepository products = new ProductRepository();
        CartRepository carts = new CartRepository();

        // Semente de produtos no Firestore (uma vez)
        products.seedIfEmpty();

        Cart cart = new Cart();
        String uid = null;
        String email = null;

        System.out.println("=== Supermercado (CLI) ===");
        while (true) {
            System.out.println("\n1) Cadastrar  2) Login  3) Listar produtos  4) Adicionar ao carrinho  5) Ver carrinho  6) Finalizar  0) Sair");
            String op = Console.ask("Escolha");

            try {
                switch (op) {
                    case "1": // Cadastrar
                        String e1 = Console.ask("E-mail");
                        String p1 = Console.ask("Senha (mín. 6)");
                        AuthResponse r1 = auth.signUp(e1, p1);
                        uid = r1.getLocalId(); email = r1.getEmail();
                        carts.saveUserIfNew(uid, email);
                        System.out.println("Cadastro ok. UID=" + uid);
                        break;

                    case "2": // Login
                        String e2 = Console.ask("E-mail");
                        String p2 = Console.ask("Senha");
                        AuthResponse r2 = auth.signIn(e2, p2);
                        uid = r2.getLocalId(); email = r2.getEmail();
                        carts.saveUserIfNew(uid, email);
                        System.out.println("Login ok. UID=" + uid);
                        break;

                    case "3": // Listar produtos
                        ensureLogged(uid);
                        List<Product> list = products.listAll();
                        if (list.isEmpty()) System.out.println("Sem produtos.");
                        else list.forEach(System.out::println);
                        break;

                    case "4": // Adicionar
                        ensureLogged(uid);
                        String id = Console.ask("ID do produto");
                        int q = Console.askInt("Quantidade");
                        Optional<Product> prod = products.getById(id);
                        if (prod.isEmpty()) {
                            System.out.println("Produto não encontrado.");
                        } else {
                            cart.add(prod.get(), q);
                            System.out.println("Adicionado: " + prod.get().getName() + " x" + q);
                        }
                        break;

                    case "5": // Ver carrinho
                        ensureLogged(uid);
                        if (cart.isEmpty()) System.out.println("Carrinho vazio.");
                        else {
                            cart.list().forEach(i ->
                                    System.out.printf("- %s x%d = R$ %.2f%n",
                                            i.getProduct().getName(), i.getQuantity(), i.getSubtotal()));
                            System.out.printf("Total parcial: R$ %.2f%n", cart.total());
                        }
                        break;

                    case "6": // Finalizar (salva no Firestore)
                        ensureLogged(uid);
                        if (cart.isEmpty()) {
                            System.out.println("Carrinho vazio.");
                        } else {
                            carts.saveCart(uid, cart);
                            double total = carts.computeTotal(uid);
                            cart.clear();
                            System.out.printf("Compra finalizada! Total registrado: R$ %.2f%n", total);
                        }
                        break;

                    case "0":
                        System.out.println("Até mais!");
                        return;

                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (IOException ex) {
                System.out.println("Erro em Auth: " + ex.getMessage());
            } catch (RuntimeException ex) {
                System.out.println("Erro: " + ex.getMessage());
            }
        }
    }

    private static void ensureLogged(String uid) {
        if (uid == null) throw new RuntimeException("Faça login/cadastro primeiro.");
    }
}
