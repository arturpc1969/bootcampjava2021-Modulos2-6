package br.com.alura.livraria.infra;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxSwaggerConfigurations {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfo(
				"API Livraria On Line",
				"API Rest para gerenciamento de uma livraria on line.",
				"Versão 0.1",
				"Termos de serviço",
				new Contact("Artur Coutinho", "https://github.com/arturpc1969/", "arturpc@yahoo.com.br"),
				"Licença da API", "URL da Licença da API", Collections.emptyList());
	}

}
