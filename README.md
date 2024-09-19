<img src="https://capsule-render.vercel.app/api?type=venom&height=150&color=auto&text=SpringSecurity%20,%20OAuth%20로그인%20구현+%20&fontColor=000000&fontAlign=50&fontSize=30" />

#### 소개 
- Spring Security와 JWT를 이용하여 자체 로그인과 OAuth 로그인 구현.


#### 기능 
- 일반 로그인 구현
- 카카오, 네이버, 구글 OAuth 로그인 구현


#### 스프링시큐리티 config작성
- HTTP 요청에 대한 접근 권한 설정
```java
.authorizeHttpRequests(r -> r
                        .requestMatchers("/", "/api/v1/auth/**", "/oauth2/**").permitAll()
                        .requestMatchers("/api/v1/user/**").hasRole("USER") 
                        .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
```
- OAuth 2.0 로그인 프로세스 설정
```java
.oauth2Login(oauth -> oauth.authorizationEndpoint(endpoint -> endpoint.baseUri("/api/v1/auth/oauth2"))
                        .redirectionEndpoint(endpoint -> endpoint.baseUri("/oauth2/callback/*"))
                        .userInfoEndpoint(endpoint -> endpoint.userService(defaultOAuth2UserService))
                        .successHandler(oAuth2SuccessHandler))
```

#### 일반 로그인
ID(email)와 password만 사용하는 로그인 기능. 실제 데이터는 MySQL DB에 저장한다.

#### OAuth 로그인
- OAuth란 사용자가 애플리케이션에 로그인할 때, 제3자 서비스(예: kakao, naver 등)의 인증을 사용하여 로그인을 처리하는 방식.
- 
[프론트앤드]<http://github.com/jeongseyoung/](https://github.com/jeongseyoung/OAuth_Frontend>
