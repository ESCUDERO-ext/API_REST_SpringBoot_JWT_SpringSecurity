package com.sistema.blog.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

//DTO = Data Transfer Object
public class ComentarioDTO {

	private long id;
	
	@NotEmpty(message = "El nombre no debe ser vacío o nulo")
	private String nombre;
	
	@NotEmpty(message = "El email no debe ser vacío o nulo")
	@Email
	private String email;
	
	@NotEmpty
	@Size(min = 10, message = "El cuerpo debe tener al menos 10 caracteres")
	private String cuerpo;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCuerpo() {
		return cuerpo;
	}

	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}

	public ComentarioDTO() {
		super();
	}

}
