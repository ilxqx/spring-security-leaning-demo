package com.ilxqx.springsecuritylearning.config;

import com.ilxqx.springsecuritylearning.support.SmsCodeAuthenticationProvider;
import com.ilxqx.springsecuritylearning.support.SmsCodeLoginConfigurer2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.context.SecurityContextRepository;

/**
 * 安全配置
 *
 * @author venus
 * @version 1.0.0
 * @since 2022/6/21 22:40
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
                                                   AuthenticationSuccessHandler authenticationSuccessHandler,
                                                   AuthenticationFailureHandler authenticationFailureHandler,
                                                   AuthenticationEntryPoint authenticationEntryPoint,
                                                   @Qualifier("frameworkJwtSecurityContextRepository") SecurityContextRepository securityContextRepository) throws Exception {

        httpSecurity.formLogin()
            .usernameParameter("username")
            .passwordParameter("password")
            .loginProcessingUrl("/login")
            .successHandler(authenticationSuccessHandler)
            .failureHandler(authenticationFailureHandler)
            .securityContextRepository(securityContextRepository)
            .and()
            .securityContext()
            .requireExplicitSave(true)
            .securityContextRepository(securityContextRepository)
            .and()
            .csrf()
            .disable()
            .authorizeHttpRequests()
            .anyRequest()
            .authenticated()
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(authenticationEntryPoint);

        // /admin/user  GET:/admin/user
        /*Field filterOrdersField = HttpSecurity.class.getDeclaredField("filterOrders");
        filterOrdersField.setAccessible(true);
        Object filterRegistration = filterOrdersField.get(httpSecurity);
        Method putMethod = filterRegistration.getClass().getDeclaredMethod("put", Class.class, int.class);
        putMethod.setAccessible(true);
        putMethod.invoke(filterRegistration, SmsCodeAuthenticationFilter.class, 1901);*/

        /*SmsCodeLoginConfigurer<HttpSecurity> smsCodeLoginConfigurer = new SmsCodeLoginConfigurer<>("/smsCodeLogin");
        smsCodeLoginConfigurer
            .successHandler(authenticationSuccessHandler)
            .failureHandler(authenticationFailureHandler);

        httpSecurity.apply(smsCodeLoginConfigurer);*/
        SmsCodeLoginConfigurer2<HttpSecurity> smsCodeLoginConfigurer2 = new SmsCodeLoginConfigurer2<>();
        smsCodeLoginConfigurer2.successHandler(authenticationSuccessHandler)
            .failureHandler(authenticationFailureHandler);

        httpSecurity.apply(smsCodeLoginConfigurer2);

        httpSecurity.authenticationProvider(new SmsCodeAuthenticationProvider());

        return httpSecurity.build();
    }
}
