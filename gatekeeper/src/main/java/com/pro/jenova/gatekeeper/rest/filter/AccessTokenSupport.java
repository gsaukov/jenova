package com.pro.jenova.gatekeeper.rest.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pro.jenova.gatekeeper.data.entity.AccessToken;
import com.pro.jenova.gatekeeper.data.repository.AccessTokenRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.regex.Pattern;

import static java.util.Optional.empty;
import static java.util.regex.Pattern.compile;
import static org.slf4j.LoggerFactory.getLogger;

@Component
@Transactional(noRollbackFor = IllegalArgumentException.class)
public class AccessTokenSupport {

    private static final Logger logger = getLogger(AccessTokenSupport.class);

    private static final ObjectMapper objectMapper = new ObjectMapper(); // Thread Safe

    private static final Pattern UUID_PATTERN =
            compile("^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$");

    @Autowired
    private AccessTokenRepository accessTokenRepository;

    public Optional<String> processBearer(String token) {
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

        verifyAccessToken(accessToken.get());

        return isUuid ? accessToken.map(AccessToken::getEncoded) : empty();
    }

    private void verifyAccessToken(AccessToken accessToken) {
        Integer usageCount = accessToken.getUsageCount();

        if (usageCount >= accessToken.getMaxUsages()) {
            throw new IllegalArgumentException("Token usage count has reached max value.");
        }

        accessToken.setUsageCount(usageCount + 1);
    }

    private boolean isUuid(String value) {
        return UUID_PATTERN.matcher(value).matches();
    }

}
