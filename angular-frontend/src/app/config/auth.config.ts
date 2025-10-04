import { PassedInitialConfig } from 'angular-auth-oidc-client';

// Use localhost Keycloak when running the frontend locally, otherwise use the
// in-cluster DNS name. This prevents the browser from trying to resolve
// cluster-internal hostnames when you're developing on your machine.
const isLocalHost = window.location.hostname === 'localhost' || window.location.hostname === '127.0.0.1';
const keycloakBase = isLocalHost
  ? 'http://localhost:8181'
  : 'http://keycloak.default.svc.cluster.local:8181';

export const authConfig: PassedInitialConfig = {
  config: {
    authority: `${keycloakBase}/realms/spring-microservices-security-realm`,
    redirectUrl: window.location.origin,
    postLogoutRedirectUri: window.location.origin,
    clientId: 'angular-client',
    scope: 'openid profile offline_access',
    responseType: 'code',
    silentRenew: true,
    useRefreshToken: true,
    renewTimeBeforeTokenExpiresInSeconds: 30,
  }
}
