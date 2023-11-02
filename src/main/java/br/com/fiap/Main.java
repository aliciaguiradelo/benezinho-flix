package br.com.fiap;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {

    private final static String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkNGRkZDRlMmFiZDM4Mjk1ZjgxNmIxMTZjZTFmMTFhZSIsInN1YiI6IjY1MmVmZWQzMzU4ZGE3NWI1ZjdhYmJlZiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.1_tDWTQhkVYUIp9g15o3fgQC4cCJ3bFUc9y1MW9MEyY";

    public static void main(String[] args) {

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("oracle-fiap");
        EntityManager manager = factory.createEntityManager();


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.themoviedb.org/3/movie/now_playing?language=pt-BR&page=2"))
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + TOKEN)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) System.out.println("Erro ao acessar a API: " + response.statusCode());

            ObjectMapper om = new ObjectMapper();
            Root root = om.readValue(response.body(), Root.class);


            manager.getTransaction().begin();

            manager.persist(root);

            manager.getTransaction().commit();


            System.out.println(root);


        } catch (IOException e) {
            System.err.println("Não foi possível acessar a API: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Não foi possível acessar a API: " + e.getMessage());
        }

        System.out.println(response.body());
    }
}