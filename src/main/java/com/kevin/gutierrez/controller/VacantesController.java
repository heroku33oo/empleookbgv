package com.kevin.gutierrez.controller;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kevin.gutierrez.model.Vacante;
import com.kevin.gutierrez.service.IntCategoriasService;
import com.kevin.gutierrez.service.IntVacantesService;
import com.kevin.gutierrez.util.Utileria;

@Controller
@RequestMapping("/vacantes")
public class VacantesController {

	@Value("${app.ruta.images}")
	private String ruta;
	
	@Autowired
	public IntVacantesService vacantesService;
	
	@Autowired
	public IntCategoriasService categoriasService;
	
	@GetMapping("/buscar")
	public String buscar(@RequestParam("id") int idVacante,
			Model model) {
	Vacante vacante = vacantesService.buscarPoiId(idVacante);
	model.addAttribute("vacante", vacante);
	return "vacantes/formVacante";
	}
	
	@GetMapping("/eliminar")
	public String eliminar(@RequestParam("id")int idVacante,
			RedirectAttributes atributo) {
		vacantesService.eliminar(idVacante);
		atributo.addAttribute("msj", "Vacante Eliminada");
		return "redirect:/vacantes/indexPaginado";
	}
	
	
	@GetMapping("/detalle")
	public String detalle(@RequestParam("id") int idVacante, Model model) {
		//System.out.println("idVacante " + idVacante);
		Vacante vacante = vacantesService.buscarPoiId(idVacante);
		model.addAttribute("vacante", vacante);
		return "vacantes/detalle";
		
	}
	
	@PostMapping("/guardar")
	//Data bindingg
	public String guardar(@Valid Vacante vacante,BindingResult resultado,
			RedirectAttributes atributo, Model model, 
			@RequestParam("archivoImagen") MultipartFile multiPart) {
		 
		
		if(resultado.hasErrors()) {
			for(ObjectError error: resultado.getAllErrors()) {
				System.out.println("Error: " + error.getDefaultMessage());
			}
			return "vacantes/formVacante";
		}
			//**************************
			if (!multiPart.isEmpty()) {
			//String ruta = "/empleos/img-vacantes/"; // Linux/MAC
			//String ruta = "c:/empleos/img-vacantes/"; // Windows
			String nombreImagen = Utileria.guardarArchivo(multiPart, ruta);
			if (nombreImagen != null){ // La imagen si se subio
			// Procesamos la variable nombreImagen
			vacante.setImagen(nombreImagen); 
			}
		}
			//*************************
		
		System.out.println(vacante);
		//vacante.setId(vacantesService.obtenerTodas().size()+1);
		vacante.setCategoria(categoriasService.buscarPorId(vacante.getCategoria().getId()));
		//Guardar
		vacantesService.guardar(vacante);
		atributo.addFlashAttribute("msg", "Vacante guardada con exito");
		//mostrar con redireccionamiento
		return "redirect:/vacantes/indexPaginado";
	}
	
	@GetMapping("/crear")
	public String crear(Vacante vacante, Model model) {
		return "vacantes/formVacante";
	}
	
	@GetMapping(value = "/indexPaginado")
	public String mostrarIndexPaginado(Model model, Pageable page) {
	Page<Vacante>lista = 
			vacantesService.buscarTodas(page);
	model.addAttribute("vacantes", lista);
	return "vacantes/listaVacantes";
	}
	
	@GetMapping("/index")
	public String mostrarIndex() {
	/*	for(Vacante v : vacantesService.obtenerTodas()) 
			System.out.println(v); */
		return "vacantes/listaVacantes";
	}
	@InitBinder
    protected void initBinder(WebDataBinder binder) {
      binder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
        @Override
        public void setAsText(String text) throws IllegalArgumentException{
          setValue(LocalDate.parse(text, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        }

 

        @Override
        public String getAsText() throws IllegalArgumentException {
          return DateTimeFormatter.ofPattern("dd-MM-yyyy").format((LocalDate) getValue());
        }
      });
    }
	/*
	 * Para cuando tenemos datos comunes en todo el controlador a travez de los metodos definidos
	 * y se vincula de forma automatica el controlador con la vista
	 * funciona como una variable global
	 */
	@ModelAttribute
	public void setGenericos(Model model) {
		model.addAttribute("total", vacantesService.numeroVacantes());
		model.addAttribute("categorias", categoriasService.obtenerTodas());
		model.addAttribute("vacantes", vacantesService.obtenerTodas());
	}
	
	
}
