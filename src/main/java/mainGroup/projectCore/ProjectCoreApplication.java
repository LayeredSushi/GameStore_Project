package mainGroup.projectCore;

import javafx.application.Application;
import mainGroup.projectCore.model.Employee;
import mainGroup.projectCore.repository.EmployeeRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//@ComponentScan("mainGroup.projectCore.repository.EmployeeRepo")
public class ProjectCoreApplication {

	public static void main(String[] args) {
		//SpringApplication.run(ProjectCoreApplication.class, args);
		Application.launch(GameFXApplication.class, args);
	}

}
