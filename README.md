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
- ID(email)와 password만 사용하는 로그인 기능. 실제 데이터는 MySQL DB에 저장한다.

#### OAuth 로그인
- OAuth란 사용자가 애플리케이션에 로그인할 때, 제3자 서비스(예: kakao, naver 등)의 인증을 사용하여 로그인을 처리하는 방식.
- 다음과 같은 인증 절차를 거치게 됨
  -  로그인 요청 : 애플리케이션은 서비슷 제공자에게 인증을 요청하며, 필요한 정보(권한 범위 등)를 전달
  -  사용자 인증 : 사용자는 OAuth 제공자(kakao,naver,google 등)에서 로그인하고 애플리케이션이 요청한 정보(프로필, 이메일 등)에 대한 권한을 승인
  -  리다이렉트 및 인증 코드 교환 : OAuth 2.0 제공자는 애플리케이션에 설정된 리다이렉트 URI로 사용자를 돌려보내며, 인증 코드를 전달하고 애플리케이션은 이 인증 코드를 사용하여 OAuth 2.0 제공자에게 액세스 토큰을 요청
  -  사용자 정보 가져오기 : 애플리케이션은 발급된 액세스 토큰을 사용하여 제공자로부터 사용자 정보를 가져오고, 이를 통해 로그인 처리를 완료
[프론트앤드]<http://github.com/jeongseyoung/](https://github.com/jeongseyoung/OAuth_Frontend>
