package com.pro.jenova.justitia.rest.controller.client;

import com.pro.jenova.common.rest.ErrorResponse;
import com.pro.jenova.common.rest.RestResponse;
import com.pro.jenova.common.rest.VoidResponse;
import com.pro.jenova.justitia.rest.controller.client.request.RestCreateClientRequest;
import com.pro.jenova.justitia.rest.controller.client.response.RestClientDetails;
import com.pro.jenova.justitia.rest.controller.client.response.RestListClientsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientAlreadyExistsException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/justitia-api/client")
public class ClientController {

    @Autowired
    private JdbcClientDetailsService clientDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<RestResponse> create(@RequestBody RestCreateClientRequest restCreateClientRequest) {
        try {
            clientDetailsService.addClientDetails(restCreateClientRequest);
        } catch (final ClientAlreadyExistsException exc) {
            return ErrorResponse.badRequest("CLIENT_ALREADY_EXISTS");
        }

        return VoidResponse.created();
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public ResponseEntity<RestResponse> remove(@RequestBody RestCreateClientRequest restCreateClientRequest) {
        try {
            clientDetailsService.removeClientDetails(restCreateClientRequest.getClientId());
        } catch (final NoSuchClientException exc) {
            return ErrorResponse.badRequest("USERNAME_NOT_FOUND");
        }

        return VoidResponse.ok();
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<RestResponse> list() {
        List<ClientDetails> clients = clientDetailsService.listClientDetails();

        return new ResponseEntity<>(new RestListClientsResponse.Builder().withClients(clients.stream()
                .map(this::toRestClientDetails).collect(toList())).build(), HttpStatus.OK);
    }

    private RestClientDetails toRestClientDetails(ClientDetails details) {
        return new RestClientDetails.Builder()
                .withClientId(details.getClientId())
                .withScopes(details.getScope())
                .withRedirectUri(details.getRegisteredRedirectUri())
                .withAccessTokenDurationSecs(details.getAccessTokenValiditySeconds())
                .withRefreshTokenDurationSecs(details.getRefreshTokenValiditySeconds())
                .build();
    }

}
