package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


// acceder a toda la lista --> http://localhost:8080/api/estudiantes
// acceder a una informacion en especifico  --> http://localhost:8080/api/estudiantes/2
@RestController
@RequestMapping("api/estudiantes")
public class EstudianteController {

	private static final List<Estudiante> estudiantes = new ArrayList<>();
	private final AtomicLong contador = new AtomicLong();
	public EstudianteController() {
		initData();
	}
//	data de prueba
	private void initData() {
		estudiantes.add(new Estudiante(contador.incrementAndGet(), "Elizeth", "Rojas Delgadp", 20, "Los Robles 123"));
		estudiantes.add(new Estudiante(contador.incrementAndGet(), "Juan Carlos", "Gutierrez Altamirano", 70, "Las casuarinas 111"));
		estudiantes.add(new Estudiante(contador.incrementAndGet(), "Alejandro", "De la cruz Amparo", 20, "Casa grande Mz F18 lote 87"));
	}
	
//	metodos http
	@GetMapping()
	public ResponseEntity<List<Estudiante>> listar(){
		return new ResponseEntity<>(estudiantes, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Estudiante> obtener(@PathVariable long id){
		Estudiante estudiante = estudiantes.stream().filter(x->id==x.getId()).findAny().orElse(null);
		HttpStatus estado = (estudiante!=null)?HttpStatus.OK:HttpStatus.NOT_FOUND;
		return new ResponseEntity<Estudiante>(estudiante, estado);
	}
	
	@PostMapping()
	public ResponseEntity<Estudiante> registrar(@RequestBody Estudiante e){
		Estudiante estudiante = new Estudiante(contador.incrementAndGet(), e.getNombre(), e.getApellido(), e.getEdad(), e.getDireccion());
		estudiantes.add(estudiante);
		HttpHeaders headers = new HttpHeaders();
		headers.add("estudiantes", "api/estudiantes/"+estudiante.getId());
		return new ResponseEntity<Estudiante>(estudiante, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Estudiante> actualizar(@PathVariable long id, @RequestBody Estudiante e){
		Estudiante estudianteTemporal = null;
		for(Estudiante estudiante : estudiantes){
			if(estudiante.getId()==id) {
				estudiante.setNombre(e.getNombre());
				estudiante.setApellido(e.getApellido());
				estudiante.setEdad(e.getEdad());
				estudiante.setDireccion(e.getDireccion());
				estudianteTemporal = estudiante;
				break;
			}
		}
		return new ResponseEntity<Estudiante>(estudianteTemporal, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Estudiante> eliminar(@PathVariable long id){
		Estudiante estudiante = estudiantes.stream().filter(x->id==x.getId()).findAny().orElse(null);
		if(estudiante!=null) estudiantes.remove(estudiante);
		return new ResponseEntity<Estudiante>(HttpStatus.NO_CONTENT);
	}
	
}
