package com.JoelXavier.usuario.infraestructure.repository;


import com.JoelXavier.usuario.infraestructure.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
