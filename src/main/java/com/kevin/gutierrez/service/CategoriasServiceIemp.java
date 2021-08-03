package com.kevin.gutierrez.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kevin.gutierrez.model.Categoria;
@Service
public class CategoriasServiceIemp implements IntCategoriasService{

	private List<Categoria> lista;
	
	public CategoriasServiceIemp() {
		
		lista = new LinkedList<Categoria>();
		
		Categoria c1 = new Categoria();
		c1.setId(1);
		c1.setNombre("Vigilancia");
		c1.setDescripcion("Relacionado con la seguridad");
		//**************
		Categoria c2 = new Categoria();
		c2.setId(2);
		c2.setNombre("Contabilidad");
		c2.setDescripcion("Relacionado con contabilidad y finanza");
		//*************
		Categoria c3 = new Categoria();
		c3.setId(3);
		c3.setNombre("Ingenieria");
		c3.setDescripcion("Relacionado con áreas de ingenieria");
		//*************
		Categoria c4 = new Categoria();
		c4.setId(4);
		c4.setNombre("Tecnicos");
		c4.setDescripcion("Relacionado con diferentes áreas");
		lista.add(c1);
		lista.add(c2);
		lista.add(c3);
		lista.add(c4);
	}
	
	@Override
	public List<Categoria> obtenerTodas() {
		return lista;
	}

	@Override
	public void guardar(Categoria categoria) {
		lista.add(categoria);
	}

	@Override
	public void eliminar(Integer idCategoria) {
		lista.remove(buscarPorId(idCategoria));
	}

	@Override
	public Categoria buscarPorId(Integer idCategoria) {
		for(Categoria categoria: lista) {
			if(categoria.getId() == idCategoria) {
				return categoria;
			}
		}
		return null;
	}

	@Override
	public Page<Categoria> buscarTodas(Pageable page) {
		// TODO Auto-generated method stub
		return null;
	}

}
