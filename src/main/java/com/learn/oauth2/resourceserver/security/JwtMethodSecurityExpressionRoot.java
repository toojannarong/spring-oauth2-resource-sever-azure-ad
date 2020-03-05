package com.learn.oauth2.resourceserver.security;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.CollectionHelper.newArrayList;

@Slf4j
public class JwtMethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

  private static final String APPLICATION_ROLE_CLAIM = "roles";

  public JwtMethodSecurityExpressionRoot(Authentication authentication) {
    super(authentication);
  }


  @SuppressWarnings({"SuspiciousMethodCalls", "WeakerAccess"})
  public boolean hasAnyApplicationRole(String... requiredRoles) {
    Optional<Jwt> jwt = getJwt();

    List<String> userRoles = jwt
        .map(it -> it.getClaimAsStringList(APPLICATION_ROLE_CLAIM))
        .orElse(newArrayList());

    List<String> requiredRoleList = Arrays.asList(requiredRoles);
    boolean hasApplicationRole = userRoles
        .stream()
        .anyMatch(requiredRoleList::contains);

    if (!hasApplicationRole) {
      log.warn("Unauthorized access: [{}], required application role: {}, actual roles: {}", requiredRoles, userRoles);
    }
    return hasApplicationRole;
  }

  private Optional<Jwt> getJwt() {
    return Optional.ofNullable(authentication)
        .filter(it -> it instanceof JwtAuthenticationToken)
        .map(it -> (JwtAuthenticationToken) it)
        .map(JwtAuthenticationToken::getToken);
  }


  @Override
  public Object getFilterObject() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setFilterObject(Object filterObject) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Object getReturnObject() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setReturnObject(Object returnObject) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Object getThis() {
    throw new UnsupportedOperationException();
  }
}

