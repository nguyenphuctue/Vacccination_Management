package com.training.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.*;

import lombok.*;

@Entity
@Table(name = "customer")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

	@Id
	@Column(name = "customer_id",length = 36)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long customerId;

	@Column(name = "address")
	private String address;

	@Column(name = "date_of_birth")
	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;

	@Column(name = "email", length = 100, unique = true)
	private String email;

	@Column(name = "full_name", length = 100)
	private String fullName;

	@Column(name = "gender", length = 10)
	private boolean gender;

	@Column(name = "indentity_card", length = 12)
	private String identityCard;

	@Column(name = "password")
	private String password;

	@Column(name = "phone", length = 20)
	private String phone;

	@Column(name = "user_name", unique = true)
	private String userName;
	
	@OneToMany(mappedBy = "customer")
	private Set<InjectionResult> injectionResults;

	@Column(name = "status_save")
	private byte statusSave;
}
