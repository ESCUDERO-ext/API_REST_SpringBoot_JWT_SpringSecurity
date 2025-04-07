package com.sistema.blog.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistema.blog.entidades.Usuario;

public interface RolRepositorio extends JpaRepository<Usuario, Long> {

	public Optional<Usuario> findByNombre(String nombre);
}
