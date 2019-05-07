package com.pro.jenova.justitia.rest.controller.scope;

import com.pro.jenova.common.rest.ErrorResponse;
import com.pro.jenova.common.rest.RestResponse;
import com.pro.jenova.common.rest.VoidResponse;
import com.pro.jenova.justitia.data.entity.Scope;
import com.pro.jenova.justitia.data.repository.ScopeRepository;
import com.pro.jenova.justitia.rest.controller.scope.request.RestCreateScopeRequest;
import com.pro.jenova.justitia.rest.controller.scope.request.RestListScopesRequest;
import com.pro.jenova.justitia.rest.controller.scope.request.RestRemoveScopeRequest;
import com.pro.jenova.justitia.rest.controller.scope.response.RestListScopeDetails;
import com.pro.jenova.justitia.rest.controller.scope.response.RestListScopesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/justitia-api/scope")
public class ScopeController {

    @Autowired
    private ScopeRepository scopeRepository;

    @PostMapping("/create")
    public ResponseEntity<RestResponse> create(@RequestBody RestCreateScopeRequest restCreateScopeRequest) {
        if (scopeRepository.existsByClientIdAndName(restCreateScopeRequest.getClientId(),
                restCreateScopeRequest.getName())) {
            return ErrorResponse.badRequest("SCOPE_FOR_CLIENT_ALREADY_EXISTS");
        }

        scopeRepository.save(new Scope.Builder()
                .withClientId(restCreateScopeRequest.getClientId())
                .withName(restCreateScopeRequest.getName())
                .withMaxUsages(restCreateScopeRequest.getMaxUsages())
                .withPermissions(restCreateScopeRequest.getPermissions())
                .build());

        return VoidResponse.created();
    }

    @PostMapping("/remove")
    public ResponseEntity<RestResponse> remove(@RequestBody RestRemoveScopeRequest restRemoveScopeRequest) {
        if (scopeRepository.removeByClientIdAndName(restRemoveScopeRequest.getClientId(),
                restRemoveScopeRequest.getName()) > 0L) {
            return VoidResponse.ok();
        }

        return ErrorResponse.badRequest("SCOPE_FOR_CLIENT_NOT_FOUND");
    }

    @PostMapping("/list")
    public ResponseEntity<RestResponse> list(@RequestBody RestListScopesRequest restListScopesRequest) {
        List<Scope> scopes = scopeRepository.findByClientId(restListScopesRequest.getClientId());

        return new ResponseEntity<>(new RestListScopesResponse.Builder().withScopes(scopes.stream()
                .map(this::toRestListScopeDetails).collect(toList())).build(), HttpStatus.OK);
    }

    private RestListScopeDetails toRestListScopeDetails(Scope scope) {
        return new RestListScopeDetails.Builder()
                .withClientId(scope.getClientId())
                .withName(scope.getName())
                .withMaxUsages(scope.getMaxUsages())
                .withPermissions(scope.getPermissions())
                .build();
    }

}
