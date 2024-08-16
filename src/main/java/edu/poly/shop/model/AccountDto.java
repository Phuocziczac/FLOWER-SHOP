package edu.poly.shop.model;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto implements Serializable {
	@NotNull
	private String username;
	@NotEmpty
	@Length(min = 8, max = 50)
	private String password;
	
	private MultipartFile image;  
	private String imgurl;
	private Boolean isEdit = false;
}
