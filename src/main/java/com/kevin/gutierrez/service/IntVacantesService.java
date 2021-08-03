package com.kevin.gutierrez.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kevin.gutierrez.model.Vacante;

public interface IntVacantesService {

	public List<Vacante> obtenerTodas();
	public void guardar(Vacante vacante);
	public void eliminar(Integer idVacante);
	public Vacante buscarPoiId(Integer idVacante);
	
	public Integer numeroVacantes();
	
	Page<Vacante>buscarTodas(Pageable page);
	public List<Vacante> obtenerDestacadas();
	public List<Vacante> todasDestacadas();
}