package br.com.igorsilva.taskmanager.auth;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

@RequestMapping("/auth")
@RestController
public class TokenController {

    private final RestClient restClient = RestClient.builder().baseUrl("http://localhost:8080").build();

    @PostMapping
    public ResponseEntity<String> getToken(@RequestBody User user) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("username", user.username());
        formData.add("password", user.password());
        formData.add("grant_type", user.grantType());
        formData.add("client_id", user.clientId());

        String result = restClient.post()
                .uri("/realms/taskmanager/protocol/openid-connect/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(formData)
                .retrieve()
                .body(String.class);

        return ResponseEntity.ok(result);
    }

    public record User(String username, String password, String grantType, String clientId) {}
}
