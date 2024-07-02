package com.subodh.user;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.subodh.role.Role;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Token {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String token;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime expriredAt;
	
	private LocalDateTime validatedAt;
	@ManyToOne
	@JoinColumn(nullable =  false, name = "userId")
	private User user;
}
