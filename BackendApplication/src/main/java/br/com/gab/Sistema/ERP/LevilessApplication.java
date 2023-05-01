package br.com.gab.Sistema.ERP;

import br.com.gab.Sistema.ERP.Model.RoleModel;
import br.com.gab.Sistema.ERP.Model.Usuario;
import br.com.gab.Sistema.ERP.Model.enums.RoleName;
import br.com.gab.Sistema.ERP.Service.UserService;
import br.com.gab.Sistema.ERP.utils.CriptografiaUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.util.ArrayList;

@SpringBootApplication
public class LevilessApplication {

	public static void main(String[] args) {
		SpringApplication.run(LevilessApplication.class, args);
	}

	@Bean
	public DataSource dataSource(){
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://localhost:5432/leviless");
		dataSource.setUsername("postgres");
		dataSource.setPassword("admin");
		return dataSource;
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

//	//
//	@Bean
//	CommandLineRunner run(UserService userService){
//		return args -> {
//			userService.saveRole(new RoleModel(null, "ROLE_USER"));
//			userService.saveRole(new RoleModel(null, "ROLE_MANAGER"));
//			userService.saveRole(new RoleModel(null, "ROLE_ADMIN"));
//			userService.saveRole(new RoleModel(null, "ROLE_SUPER_ADMIN"));
//
//			userService.saveUser(new Usuario(null, "Jonh Travolta", "gab", "eita", new ArrayList<>()));
//			userService.saveUser(new Usuario(null, "Pedro", "teste", "eita", new ArrayList<>()));
//			userService.saveUser(new Usuario(null, "Joao", "aoba", "eita", new ArrayList<>()));
//			userService.saveUser(new Usuario(null, "Jessica", "bora", "eita", new ArrayList<>()));
//
//			userService.addRoleToUser("gab", "ROLE_USER");
//			userService.addRoleToUser("gab", "ROLE_ADMIN");
//			userService.addRoleToUser("gab", "ROLE_SUPER_ADMIN");
//			userService.addRoleToUser("teste", "ROLE_MANAGER");
//			userService.addRoleToUser("aoba", "ROLE_ADMIN");
//			userService.addRoleToUser("bora", "ROLE_SUPER_ADMIN");
//		};
//	}


}
