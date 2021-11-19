package br.com.alura.livraria.infra;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.alura.livraria.modelo.Usuario;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ParameterType;
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
				.globalRequestParameters(Arrays.asList(
						new RequestParameterBuilder()
						.name("Authorization")
						.description("Bearer Token")
						.required(false)
						.in(ParameterType.HEADER)
						.build()))
				.ignoredParameterTypes(Usuario.class)
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfo(
				"API Livraria On Line",
				"API Rest para gerenciamento de uma livraria on line.",
				"Versão 1.0",
				"Termos de serviço",
				new Contact("Artur Coutinho", "https://github.com/arturpc1969/", "arturpc@yahoo.com.br"),
				"Licença da API", "URL da Licença da API", Collections.emptyList());
	}

}
