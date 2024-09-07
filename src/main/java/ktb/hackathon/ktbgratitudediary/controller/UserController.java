package ktb.hackathon.ktbgratitudediary.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import ktb.hackathon.ktbgratitudediary.domain.request.LogInRequest;
import ktb.hackathon.ktbgratitudediary.domain.request.SignUpRequest;
import ktb.hackathon.ktbgratitudediary.domain.security.TokenInfo;
import ktb.hackathon.ktbgratitudediary.response.SuccessResponse;
import ktb.hackathon.ktbgratitudediary.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/users/")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "Operations pertaining to user management")
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Operation(summary = "회원 가입", description = "Register a new user with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        String encodedPassword = passwordEncoder.encode(signUpRequest.password());
        userService.saveUser(signUpRequest.toDto(encodedPassword));
        log.info("UserController.signUp");
        return SuccessResponse.created();
    }

    @Operation(summary = "로그인", description = "Authenticate user and return a JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LogInRequest logInRequest, HttpServletResponse response) {
        TokenInfo tokenInfo = userService.logInUser(response, logInRequest.toDto());
        log.info("UserController.login");
        return SuccessResponse.ok(tokenInfo.accessToken());
    }

    @Operation(summary = "로그아웃", description = "Logout the currently authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Logout successful, no content", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        userService.logOutUser(request, response);
        log.info("UserController.logout");
        return SuccessResponse.ok();
    }

    @Operation(summary = "토큰 재발급", description = "Reissue a new JWT token using a valid refresh token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token reissued successfully", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
    })
    @GetMapping("/reissue")
    public ResponseEntity<Object> reissue(HttpServletRequest request, HttpServletResponse response) {
        TokenInfo tokenInfo = userService.reissueToken(request, response);
        log.info("UserController.reissue");
        return SuccessResponse.ok(tokenInfo.accessToken());
    }
}
