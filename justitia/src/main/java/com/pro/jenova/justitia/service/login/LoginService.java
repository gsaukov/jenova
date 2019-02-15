package com.pro.jenova.justitia.service.login;

import java.util.Map;

public interface LoginService {

    LoginServiceResult initiate(String username, Map<String, String> attributes);

}
