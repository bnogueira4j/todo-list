package tech.ada.java.todolist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration //Indica que esta classe é uma configuração Spring.
@EnableWebSecurity //Habilita a segurança web baseada em Spring Security, sempre precisamos colocar na classe de configuracao do spring security
public class SecurityConfig {

    //indica que o método é um bean gerenciado pelo Spring
    @Bean
    //Define nosso filtro de segurança e suas configuracoes.
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                //desabilitando as opções de frame, é necessário para permitir a visualização do console H2
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))

                //Desabilita a proteção contra CSRF. desabilitamos uma camada de seguranca para podermos fazer POST sem precisar de token
                .csrf(AbstractHttpConfigurer::disable)

                //Configura as regras de autorização para as requisições HTTP.
                .authorizeHttpRequests(auth -> {

                            //Permite todas as requisições GET para o endpoint "/aluno" sem autenticação.
                            auth.requestMatchers(HttpMethod.GET, "/aluno").permitAll();
                            //Requer autenticação para todas as outras requisições.
                            auth.anyRequest().authenticated();

                        }
                )
                //configura as opções padrão para a autenticação básica: name e password
                .httpBasic(Customizer.withDefaults());
        //Retorna a cadeia de filtros de segurança configurad
        return http.build();
    }
}
