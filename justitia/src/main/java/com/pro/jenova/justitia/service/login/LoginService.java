package com.pro.jenova.justitia.service.login;

import java.util.Map;

public interface LoginService {

    LoginResult init(String clientId, String username, String scopes, Map<String, String> params);

}
