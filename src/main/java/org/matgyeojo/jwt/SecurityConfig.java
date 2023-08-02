//package org.matgyeojo.jwt;
//
//import javax.annotation.security.PermitAll;
//
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@EnableWebSecurity //기본적인 웹 보안을 활성화 하겠다는 의미
//public class SecurityConfig extends WebSecurityConfigurerAdapter{
//	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests() //httpservletRequest를 사용하는 요청들에 대한 접근제한을 설정하겠다는 의미
//			.antMatchers("/api/hello/").permitAll() // api/hello에 대한 요청은 인증없이 접근을 허용하겠다
//			.anyRequest().authenticated(); //나머지 요청들은 모두 인증되어야 한다.
//	}
//}
