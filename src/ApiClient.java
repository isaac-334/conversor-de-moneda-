
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiClient {
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/274f010c4bbb6e55206cfb00/latest/USD";
    private HttpClient client;

    public ApiClient() {
        client = HttpClient.newHttpClient();
    }

    public  String obtenerTasaCambio() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }


}
