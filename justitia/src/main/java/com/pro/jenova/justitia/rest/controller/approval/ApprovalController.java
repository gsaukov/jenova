package com.pro.jenova.justitia.rest.controller.approval;

import com.pro.jenova.common.rest.ErrorResponse;
import com.pro.jenova.common.rest.RestResponse;
import com.pro.jenova.common.rest.VoidResponse;
import com.pro.jenova.justitia.data.entity.User;
import com.pro.jenova.justitia.data.repository.UserRepository;
import com.pro.jenova.justitia.rest.controller.approval.response.RestListApprovalDetails;
import com.pro.jenova.justitia.rest.controller.approval.response.RestListApprovalsResponse;
import com.pro.jenova.justitia.rest.controller.user.request.RestCreateUserRequest;
import com.pro.jenova.justitia.rest.controller.user.request.RestRemoveUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping("/justitia-api/approval")
public class ApprovalController {

    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss'Z");

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/create")
    public ResponseEntity<RestResponse> create(@RequestBody RestCreateUserRequest restCreateUserRequest) {
        if (userRepository.existsByUsername(restCreateUserRequest.getUsername())) {
            return ErrorResponse.badRequest("USERNAME_ALREADY_EXISTS");
        }

        userRepository.save(new User.Builder()
                .withUsername(restCreateUserRequest.getUsername())
                .withPassword(passwordEncoder.encode(restCreateUserRequest.getPassword()))
                .build());

        return VoidResponse.created();
    }

    @PostMapping("/remove")
    public ResponseEntity<RestResponse> remove(@RequestBody RestRemoveUserRequest restRemoveUserRequest) {
        if (userRepository.removeByUsername(restRemoveUserRequest.getUsername()) > 0L) {
            return VoidResponse.ok();
        }

        return ErrorResponse.badRequest("USERNAME_NOT_FOUND");
    }

    @GetMapping("/list")
    public ResponseEntity<RestResponse> list() {
        List<RestListApprovalDetails> approvals = jdbcTemplate.query(
                "select userId, clientId, scope, expiresAt from oauth_approvals",
                (result, number) -> new RestListApprovalDetails.Builder()
                        .withUserId(result.getString("userId"))
                        .withClientId(result.getString("clientId"))
                        .withScope(result.getString("scope"))
                        .withExpiration(FORMATTER.format(result.getDate("expiresAt")))
                        .build());

        return new ResponseEntity<>(new RestListApprovalsResponse.Builder().withApprovals(approvals).build(),
                HttpStatus.OK);
    }

}
