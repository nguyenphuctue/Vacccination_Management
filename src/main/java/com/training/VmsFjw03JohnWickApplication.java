package com.training;

import java.util.Arrays;
import java.util.Date;

import com.training.model.Customer;
import com.training.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.training.model.Employee;
import com.training.model.News;
import com.training.model.Role;
import com.training.repository.EmployeeRepository;
import com.training.repository.NewsRepository;
import com.training.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@EnableJpaRepositories
public class VmsFjw03JohnWickApplication {

	public static void main(String[] args) {
		SpringApplication.run(VmsFjw03JohnWickApplication.class, args);
	}

}

@Component
@RequiredArgsConstructor
class CustomCommanlineRunner implements CommandLineRunner {

	private final PasswordEncoder passwordEncoder;
	private final RoleRepository roleRepository;
	private final EmployeeRepository employeeRepository;
	private final CustomerRepository customerRepository;
	private final NewsRepository newsRepository;

	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String mode;

	@Override
	public void run(String... args) throws Exception {
		if (mode.equals("create")) {
			Role roleAdmin = new Role("ROLE_ADMIN");
			Role roleUser = new Role("ROLE_USER");
			Role roleCustomer = new Role("ROLE_CUSTOMER");

			Employee admin = Employee.builder()
					.employeeId("1")
					.email("kim.admin@gmail.com")
					.userName("admin")
					.password(passwordEncoder.encode("123456"))
					.roles(Arrays.asList(roleAdmin, roleUser))
					.statusSave(1)
					.build();
			
			Employee user = Employee.builder()
					.employeeId("2")
					.email("user@gmail.com")
					.userName("user")
					.password(passwordEncoder.encode("12345678"))
					.roles(Arrays.asList(roleUser))
					.statusSave(1)
					.build();
			Employee user1 = Employee.builder()
					.employeeId("3")
					.email("user1@gmail.com")
					.userName("user1")
					.password(passwordEncoder.encode("12345678"))
					.roles(Arrays.asList(roleUser))
					.statusSave(1)
					.build();
			Employee user2 = Employee.builder()
					.employeeId("4")
					.email("user2@gmail.com")
					.userName("user2")
					.password(passwordEncoder.encode("12345678"))
					.roles(Arrays.asList(roleUser))
					.statusSave(1)
					.build();
			Employee user3 = Employee.builder()
					.employeeId("5")
					.email("user3@gmail.com")
					.userName("user3")
					.password(passwordEncoder.encode("12345678"))
					.roles(Arrays.asList(roleUser))
					.statusSave(1)
					.build();

			roleRepository.save(roleAdmin);
			roleRepository.save(roleUser);
			roleRepository.save(roleCustomer);

			employeeRepository.save(admin);
			employeeRepository.save(user);
			employeeRepository.save(user1);
			employeeRepository.save(user2);
			employeeRepository.save(user3);

			Customer customer1 = Customer.builder()
					.customerId(1)
					.address("Hanoi")
					.dateOfBirth(new Date())
					.email("nguyenvana@gmail.com")
					.fullName("Nguyen Van A")
					.gender(true)
					.identityCard("666666")
					.password(passwordEncoder.encode("1"))
					.phone("0123456789")
					.userName("aaa")
					.build();
			Customer customer2 = Customer.builder()
					.address("Namdinh")
					.dateOfBirth(new Date())
					.email("nguyenvanb@gmail.com")
					.fullName("Nguyen Van B")
					.gender(true)
					.identityCard("888888")
					.password(passwordEncoder.encode("1"))
					.phone("0123456789")
					.userName("bbb")
					.build();
			Customer customer3 = Customer.builder()
					.address("Haiduong")
					.dateOfBirth(new Date())
					.email("nguyenvanc@gmail.com")
					.fullName("Nguyen Van C")
					.gender(true)
					.identityCard("999999")
					.password(passwordEncoder.encode("1"))
					.phone("0123456789")
					.userName("ccc")
					.build();

			customerRepository.save(customer1);
			customerRepository.save(customer2);
			customerRepository.save(customer3);
			
			News news1 = News.builder().title("Dịch H5N1").preview("Hiện nay có 4 loại Vaccine chính")
					.content("Hiện nay có 4 loại Vaccine chính, tùy thuộc vào cách chế biến và sản xuất vaccine")
					.postDate(new Date())
//					.newsUUID(new UUID(0, 0))
					.build();

			News news2 = News.builder().title("Dịch Covit19").preview("Hiện nay có 4 loại Vaccine chính")
					.content("Hiện nay có 4 loại Vaccine chính, tùy thuộc vào cách chế biến và sản xuất vaccine")
					.postDate(new Date())
					.build();

			News news3 = News.builder().title("Dịch H5N1").preview("Hiện nay có 4 loại Vaccine chính")
					.content("Hiện nay có 4 loại Vaccine chính, tùy thuộc vào cách chế biến và sản xuất vaccine")
					.postDate(new Date())
					.build();

			News news4 = News.builder().title("Dịch Covit19").preview("Hiện nay có 4 loại Vaccine chính")
					.content("Hiện nay có 4 loại Vaccine chính, tùy thuộc vào cách chế biến và sản xuất vaccine")
					.postDate(new Date())
					.build();

			News news5 = News.builder().title("Dịch Covit19").preview("Hiện nay có 4 loại Vaccine chính")
					.content("Hiện nay có 4 loại Vaccine chính, tùy thuộc vào cách chế biến và sản xuất vaccine")
					.postDate(new Date())
					.build();
			newsRepository.save(news1);
			newsRepository.save(news2);
			newsRepository.save(news3);
			newsRepository.save(news4);
			newsRepository.save(news5);

		}
	}
}
