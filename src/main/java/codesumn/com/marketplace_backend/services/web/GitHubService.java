package codesumn.com.marketplace_backend.services.web;

import codesumn.com.marketplace_backend.config.EnvironConfig;
import codesumn.com.marketplace_backend.dtos.response.GitHubUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class GitHubService {

    private final RestTemplate restTemplate;
    private final String githubApiUrl;

    @Autowired
    public GitHubService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.githubApiUrl = EnvironConfig.GITHUB_API_URL;
    }

    public GitHubUserDto getGitHubUser(String token) {
        String url = UriComponentsBuilder.fromHttpUrl(githubApiUrl + "/user")
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                GitHubUserDto.class
        ).getBody();
    }
}


