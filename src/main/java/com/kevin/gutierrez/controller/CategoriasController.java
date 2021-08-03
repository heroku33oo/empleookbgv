package com.kevin.gutierrez.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kevin.gutierrez.model.Categoria;

import com.kevin.gutierrez.service.IntCategoriasService;

@Controller
@RequestMapping("/categorias")
public class CategoriasController {

	
	
	//instancia inyectada
	@Autowired
	private IntCategoriasService CategoriasService;
	
	@GetMapping("/crear")
    public String crear(Categoria categoria) {
        return "categorias/formCategoria";
    }	
	
	@GetMapping("/buscar")
    public String buscar(@RequestParam("id") int idCategoria,
            Model model) {
        Categoria categoria = CategoriasService.buscarPorId(idCategoria);
        System.out.println(categoria);
        model.addAttribute("categoria", categoria);
        return "categorias/formCategoria";
    }
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable("id") int idCategoria,
		RedirectAttributes attribute){
		//System.out.println("IdCategoria = " + idCategoria);
		attribute.addFlashAttribute("msg", "Registro eliminado correctamente");
		CategoriasService.eliminar(idCategoria);
		return "redirect:/categorias/index";
	}
	
	@PostMapping("/guardar")  
	//Data Binding (Vincular datos entre una clase modelo y un formulario)
	public String guardar(Categoria categoria, RedirectAttributes attribute) {
			//Esta linea es para incrementar el id con colecciones
		//categoria.setId(CategoriasService.obtenerTodas().size()+1);
		System.out.println(categoria);
		CategoriasService.guardar(categoria);
		attribute.addFlashAttribute("msg", "¡Categoria guardada con exito!");
		//redireccionamiento a una nueva petición
		return "redirect:/categorias/index";
		
	}
	
	/*public String guardar(@RequestParam("nombre") String nombre,
			@RequestParam("descripcion") String descripcion,
			Model model) {
		/*
		System.out.println("Nombre: "+ nombre);
		System.out.println("Descripcion: "+ descripcion); 
		//Guardar
		Categoria c = new Categoria();
		c.setId(CategoriasService.obtenerTodas().size()+1);
		c.setNombre(nombre);
		c.setDescripcion(descripcion);
		CategoriasService.guardar(c);
		
		model.addAttribute("categorias", CategoriasService.obtenerTodas());
		return "categorias/listaCategorias";
	}*/
	
	/*@GetMapping(value = "/indexPaginaado")
	public String mostrarIndexPaginado(Model model, Pageable page) {
	Page<Categoria> lista = CategoriasService.buscarTodas(page);
	model.addAttribute("categorias", lista);
	return "categorias/listaCategorias";
	}*/
	@GetMapping(value = "/indexPaginado")
    public String mostrarIndexPaginado(Model model, Pageable page) {
    Page<Categoria>lista = CategoriasService.buscarTodas(page);
    model.addAttribute("categorias", lista);
    return "categorias/listaCategorias";
    }
	
	
	@GetMapping("/index")
	public String mostrarIndex(Model model) {
		model.addAttribute("categorias", CategoriasService.obtenerTodas());
		for(Categoria categoria : CategoriasService.obtenerTodas()) {
		System.out.println(categoria);
		}
		return "categorias/listaCategorias";
	}
	
}
