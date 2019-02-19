package com.pro.jenova.justitia.service.login;

import java.util.Map;

public interface LoginService {

    LoginServiceResult initiate(String username, String level, Map<String, String> attributes);

}
