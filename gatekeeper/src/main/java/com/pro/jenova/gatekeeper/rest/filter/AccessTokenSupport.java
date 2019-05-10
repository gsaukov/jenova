package com.pro.jenova.gatekeeper.rest.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pro.jenova.gatekeeper.data.entity.AccessToken;
import com.pro.jenova.gatekeeper.data.entity.Scope;
import com.pro.jenova.gatekeeper.data.repository.AccessTokenRepository;
import com.pro.jenova.gatekeeper.data.repository.ScopeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Base64;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

import static java.util.Arrays.stream;
import static java.util.Optional.empty;
import static java.util.regex.Pattern.compile;
import static org.apache.commons.lang.StringUtils.splitPreserveAllTokens;

@Component
public class AccessTokenSupport {

    private static final ObjectMapper objectMapper = new ObjectMapper(); // Thread Safe

    private static final Pattern UUID_PATTERN =
            compile("^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$");

    @Autowired
    private AccessTokenRepository accessTokenRepository;

    @Autowired
    private ScopeRepository scopeRepository;

    @Transactional(noRollbackFor = IllegalArgumentException.class)
    public Optional<String> processBearer(String requestURI, String token) {
        boolean isUuid = isUuid(token);

        Optional<AccessToken> accessToken;

        if (isUuid) {
            accessToken = accessTokenRepository.findByJti(token);
        } else {
            accessToken = accessTokenRepository.findByEncoded(token);
        }

        if (!accessToken.isPresent()) {
            return empty();
        }

        verifyAccessToken(requestURI, accessToken.get());

        return isUuid ? accessToken.map(AccessToken::getEncoded) : empty();
    }

    private void verifyAccessToken(String requestURI, AccessToken accessToken) {
        verifyTokenPermissions(requestURI, accessToken);
        verifyTokenUsages(accessToken);
    }

    private void verifyTokenPermissions(String requestURI, AccessToken accessToken) {
        String permissions = accessToken.getPermissions();

        if (permissions == null) {
            return;
        }

        if (stream(splitPreserveAllTokens(permissions)).anyMatch(
                permission -> requestURI.endsWith(permission.trim()))) {
            return;
        }

        throw new IllegalArgumentException(getMessageWithScope(requestURI, accessToken));
    }

    private String getMessageWithScope(String requestURI, AccessToken accessToken) {
        String jwt = accessToken.getEncoded();

        int startIndex = jwt.indexOf(".");
        int endIndex = jwt.lastIndexOf(".");

        String base64Claims = jwt.substring(startIndex + 1, endIndex);

        byte[] decodedBytes = Base64.getDecoder().decode(base64Claims);

        try {
            JsonNode claims = objectMapper.readTree(new String(decodedBytes));
            JsonNode clientId = claims.get("client_id");

            Set<String> matchingScopes = new HashSet<>();
            appendMatchingScopes(requestURI, clientId, matchingScopes);

            if (matchingScopes.isEmpty()) {
                return "Token permissions do not allow invoked service. No feasible scopes found for client.";
            } else {
                return "Token permissions do not allow invoked service. Feasible scopes are "
                        + matchingScopes.toString();
            }
        } catch (IOException exc) {
            return "Token permissions do not allow invoked service.";
        }
    }

    private void appendMatchingScopes(String requestURI, JsonNode clientId, Set<String> requiredScopes) {
        scopeRepository.findByClientId(clientId.textValue()).forEach(scope -> {
            if (isPermissionsEnough(requestURI, scope)) {
                requiredScopes.add(scope.getName());
            }
        });
    }

    private boolean isPermissionsEnough(String requestURI, Scope scope) {
        String permissions = scope.getPermissions();

        if (permissions == null) {
            return true;
        }

        return stream(splitPreserveAllTokens(permissions))
                .anyMatch(permission -> requestURI.endsWith(permission.trim()));
    }

    private void verifyTokenUsages(AccessToken accessToken) {
        Integer maxUsages = accessToken.getMaxUsages();
        Integer usageCount = accessToken.getUsageCount();

        if (maxUsages != null && usageCount != null && usageCount >= maxUsages) {
            throw new IllegalArgumentException("Token usage count has reached max value.");
        }

        accessToken.setUsageCount(usageCount + 1);
    }

    private boolean isUuid(String value) {
        return UUID_PATTERN.matcher(value).matches();
    }

}
