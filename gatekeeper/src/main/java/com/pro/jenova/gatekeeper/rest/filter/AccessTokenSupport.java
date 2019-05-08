package com.pro.jenova.gatekeeper.rest.filter;

import com.pro.jenova.gatekeeper.data.entity.AccessToken;
import com.pro.jenova.gatekeeper.data.repository.AccessTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.regex.Pattern;

import static java.util.Arrays.stream;
import static java.util.Optional.empty;
import static java.util.regex.Pattern.compile;
import static org.apache.commons.lang.StringUtils.splitPreserveAllTokens;

@Component
public class AccessTokenSupport {

    private static final Pattern UUID_PATTERN =
            compile("^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$");

    @Autowired
    private AccessTokenRepository accessTokenRepository;

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

        throw new IllegalArgumentException("Token permissions do not allow invoked service.");
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
