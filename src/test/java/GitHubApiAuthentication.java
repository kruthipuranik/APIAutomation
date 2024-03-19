import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

public class GitHubApiAuthentication {
    public static void main(String[] args) {
        String token = "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";
        String url = "https://api.github.com/repos/kruthipuranik/tekarkdel"; // Replace with the actual repository URL

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + token)
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body()); // Process the response as needed
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}