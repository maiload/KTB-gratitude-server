package ktb.hackathon.ktbgratitudediary.security;

import jakarta.servlet.http.HttpServletResponse;
import ktb.hackathon.ktbgratitudediary.domain.UserDto;
import ktb.hackathon.ktbgratitudediary.domain.security.CustomUserDetails;
import ktb.hackathon.ktbgratitudediary.repository.UserRepository;
import ktb.hackathon.ktbgratitudediary.security.filter.JwtAuthenticationFilter;
import ktb.hackathon.ktbgratitudediary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class securityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private static final String[] PERMITTED_URI= {"/api/v1/users/login", "/api/v1/users/sign-up", "/api/v1/users/reissue"};
    private static final String[] SWAGGER_UI_URI= {"/swagger-ui/**", "/v3/api-docs/**"};

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
//            .cors(h -> h
//                    .configurationSource(corsConfigurationSource())) // 로컬 환경
            .httpBasic(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(h ->
                    h.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // httpSession 생성 X, SecurityContext 공유 X
            )
            .authorizeHttpRequests(h -> h
                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                    .requestMatchers(HttpMethod.POST, PERMITTED_URI).permitAll()
                    .requestMatchers(SWAGGER_UI_URI).permitAll()
                    .anyRequest().authenticated())
            .formLogin(AbstractHttpConfigurer::disable)
            .logout(AbstractHttpConfigurer::disable)
            .exceptionHandling(h -> h
                    .authenticationEntryPoint(((request, response, authException) ->
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED)))
                    .accessDeniedHandler(((request, response, accessDeniedException) ->
                            response.sendError(HttpServletResponse.SC_FORBIDDEN)))
            )
            .addFilterBefore(
                    jwtAuthenticationFilter,
                    UsernamePasswordAuthenticationFilter.class
            );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
            return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> userRepository
                .findByLoginId(username)
                .map(UserDto::from)
                .map(CustomUserDetails::from)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User[%s] not found.", username)));
    }

    @Bean
    @Profile("default")
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));  // 허용할 출처 설정
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));  // 허용할 메서드 설정
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));  // 허용할 헤더 설정
//        configuration.setAllowCredentials(true);  // 쿠키 등 자격 증명 허용
        configuration.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);  // 모든 경로에 대해 CORS 설정 적용
        return source;
    }
}
