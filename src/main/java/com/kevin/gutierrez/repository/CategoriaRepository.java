package com.kevin.gutierrez.repository;


import org.springframework.data.jpa.repository.JpaRepository;


import com.kevin.gutierrez.model.Categoria;



public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

	
}
