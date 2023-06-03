package com.project.tutoronline.handler;

import com.project.tutoronline.model.entity.Account;
import com.project.tutoronline.service.CustomAccountDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        String redirectURL = "/login";
        try {
            CustomAccountDetails customUserDetails = (CustomAccountDetails) authentication.getPrincipal();
            Account account = customUserDetails.getAccount();

            if (account != null) {
                redirectURL = switch (account.getRole().getName().toUpperCase()) {
                    case "ADMIN" -> "/back/dashboard";
                    case "TUTOR", "PARENT" -> "/";
                    default -> "/login";
                };
            }

            response.sendRedirect(redirectURL);
        } catch (Exception ex) {
            response.sendRedirect(redirectURL);
        }
    }

}