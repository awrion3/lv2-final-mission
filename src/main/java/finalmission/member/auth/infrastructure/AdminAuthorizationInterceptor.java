package finalmission.member.auth.infrastructure;

import finalmission.member.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AdminAuthorizationInterceptor implements HandlerInterceptor {

    private final AuthService authService;

    @Override
    public boolean preHandle(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final Object handler
    ) {
        if (isNotControllerMethod(handler)) {
            return true;
        }
        authService.checkAdmin(request);
        return true;
    }

    private boolean isNotControllerMethod(final Object handler) {
        return !(handler instanceof HandlerMethod);
    }
}
