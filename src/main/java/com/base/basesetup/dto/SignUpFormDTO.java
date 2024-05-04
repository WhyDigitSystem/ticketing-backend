/*
 * ========================================================================
 * This file is the intellectual property of GSM Outdoors.it
 * may not be copied in whole or in part without the express written
 * permission of GSM Outdoors.
 * ========================================================================
 * Copyrights(c) 2023 GSM Outdoors. All rights reserved.
 * ========================================================================
 */
package com.base.basesetup.dto;

//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpFormDTO {
	@NotBlank(message = "First Name is required")
	private String firstName;

	private String lastName;

	private String userName;

	@NotBlank(message = "Email is required")
	@Size(max = 30)
	@Email
	private String email;

	@NotBlank
	@Size(min = 6, max = 100, message = "Password is required")
	private String password;

//	@Size(min = 2, max = 13, message = "Please provide Valid Phone Number")
//	private String phoneNumber;
//
//	private String secondaryPhone;

//	@Enumerated(EnumType.STRING)
//	private Gender gender;

//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
//	@Past(message = "The date of birth must be in the past.")
//	private LocalDate dob;

}
