package com.kevin.gutierrez.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kevin.gutierrez.model.Vacante;

public interface VacanteRepository extends JpaRepository<Vacante, Integer> {

	//recupera las vacantes destacadas y con un estatus
			List<Vacante> findByDestacadoAndEstatusOrderByIdDesc(Integer destacado,String estatus);

			//metodo que realiza una consulta con sql nativo
			@Query(value="SELECT * FROM vacantes WHERE destacado= 1\r\n"
					+ "AND estatus = 'Aprobada' ORDER BY id", nativeQuery = true)
			List<Vacante> obtenerTodasDestacadas();
	
}
