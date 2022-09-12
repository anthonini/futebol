package br.com.anthonini.futebol;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

import br.com.anthonini.arquitetura.thymeleaf.ArquiteturaDialect;

@ComponentScan("br.com.anthonini")
@SpringBootApplication
@EnableJpaRepositories(enableDefaultTransactions = false)
public class FutebolApplication {

	public static void main(String[] args) {
		SpringApplication.run(FutebolApplication.class, args);
	}
	
	@Bean
	public LocaleResolver localeResolver() {
		return new FixedLocaleResolver(new Locale("pt", "BR"));
	}
	
	@Bean
	public ArquiteturaDialect arquiteturaDialect() {
		return new ArquiteturaDialect();
	}

}
