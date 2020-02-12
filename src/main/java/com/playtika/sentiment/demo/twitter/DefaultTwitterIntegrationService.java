package com.playtika.sentiment.demo.twitter;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth10aService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

@Slf4j
@AllArgsConstructor
public class DefaultTwitterIntegrationService {
//    private static final String NON_ALPHA_NUMERIC_UNDERSCORE_REGEX = "[\\W]";
//    private static final String WHITESPACE = " ";
//    private static final String PARAMETER_MISSING_MSG = "Twitter account=[%s] configuration parameter[%s] is missing";
    private static final String ERROR_MESSAGE_TEMPLATE = "{\"error\":\"%s\"}";
    private static final String URL = "..."; //todo
    private static final String CLIENT_ID = "..."; //todo
    private static final String API_KEY = "..."; //todo
    private static final String API_SECRET = "..."; //todo
    private static final String ACCESS_TOKEN = "..."; //todo
    private static final String ACCESS_TOKEN_SECRET = "..."; //todo
//    private static final String AUDIENCES_GET_URL = "/%s/tailored_audiences";
//    private static final String AUDIENCES_POST_USERS_URL = "/%s/tailored_audiences/%s/users";
//    private static final String AUDIENCES_CREATE_URL = "/%s/tailored_audiences?name=%s";

    private final RestTemplate restTemplate;

    public String getAllAudiences(int gameTypeId, String accountId) {
        String urlTemplate = URL;
        String url = format(urlTemplate, accountId);
        OAuthRequest request = getOAuthRequest(gameTypeId, accountId, url, Verb.GET);

        try {
            return sendGetAudiencesRequest(request, url);
        } catch (Exception e) {
            log.error("Twitter audiences for gameType {} and account {} were not fetched", gameTypeId, accountId, e);
            return format(ERROR_MESSAGE_TEMPLATE, e.getMessage());
        }
    }

    private OAuth10aService getOAuthService() {
        return new ServiceBuilder(CLIENT_ID)
                .apiKey(API_KEY)
                .apiSecret(API_SECRET)
                .build(TwitterApi.instance());
    }

    private String sendGetAudiencesRequest(OAuthRequest request, String url) {

        HttpHeaders headers = new HttpHeaders();
        request.getHeaders().forEach(headers::set);
        HttpEntity<String> entityReq = new HttpEntity<>("parameters", headers);

        ResponseEntity<String> respEntity = restTemplate.exchange(url, HttpMethod.GET, entityReq, String.class);

        String audiencesJsonString = respEntity.getBody();

        log.debug("Twitter audiences were successfully fetched with result {}", respEntity.getStatusCode());
        return audiencesJsonString;
    }

    private OAuthRequest getOAuthRequest(int gameTypeId, String accountId, String url, Verb method) {
        String urlForSignature = URL;

        OAuthRequest request = new OAuthRequest(method, urlForSignature);
        signRequest(request, gameTypeId, accountId);
        return request;
    }

    private void signRequest(OAuthRequest request, int gameTypeId, String accountId) {

        OAuth10aService service = getOAuthService();
        OAuth1AccessToken accessToken = new OAuth1AccessToken(ACCESS_TOKEN, ACCESS_TOKEN_SECRET);

        service.signRequest(accessToken, request);
    }

    private String sendCreateAudiencesRequest(OAuthRequest request, String url) {
        HttpHeaders headers = new HttpHeaders();
        request.getHeaders().forEach(headers::set);
        HttpEntity<JsonNode> entityRequest = new HttpEntity<>(headers);

        ResponseEntity<JsonNode> respEntity = restTemplate.exchange(url, HttpMethod.POST, entityRequest, JsonNode.class);

        JsonNode createdAudienceId = processTwitterResponse(url, respEntity);
        return createdAudienceId.asText();
    }

    private JsonNode processTwitterResponse(String url, ResponseEntity<JsonNode> respEntity) {
        return ofNullable(respEntity.getBody())
                .map(responseJsonNode -> {
                    log.info("Create twitter audience response fetched: {}", responseJsonNode);
                    return responseJsonNode.get("data");
                })
                .map(dataJsonNode -> dataJsonNode.get("id"))
                .orElseThrow(() -> new IllegalArgumentException(format("Create twitter campaign response is not valid for url [%s]", url)));
    }

}
