package com.aman.demo.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.aman.demo.Model.Alien;
import com.aman.demo.dao.AlienRepo;

@RestController
public class AlienController {

	@Autowired
	AlienRepo repo;

	@RequestMapping("/")
	public String Home() {
		return "Home.jsp";
	}

	@PostMapping("/alien")
	public Alien addAlien(Alien alien) {
		System.out.println("I am in post");
		repo.save(alien);
		return alien;
	}

	@GetMapping(path = "/aliens", produces = { "application/json" })
	public List<Alien> getAliens() {
		return repo.findAll();
	}

	@GetMapping("/alienname/{aname}")
	public List<Alien> getAlienName(@PathVariable String aname) {
		System.out.println(aname);
		return repo.abc(aname);
	}

	@GetMapping("/alien/{aid}")
	public Optional<Alien> getAlien(@PathVariable int aid) {
		return repo.findById(aid);
	}

	@DeleteMapping("/alien/{aid}")
	public String deleteAlien(@PathVariable int aid) {
		Alien a = repo.getOne(aid);
		repo.delete(a);
		return "Deleted Succesfully";

	}

	@PutMapping("/alien")
	public Alien saveorupdateAlien(@RequestBody Alien alien) {
		repo.save(alien);
		return alien;
	}

	@RequestMapping("/updateAlien")
	public ModelAndView updateAlien(Alien alien) {
		System.out.println("before model and view");
		ModelAndView mv = new ModelAndView("updateAlien.jsp");
		repo.deleteById(alien.getAid());
		System.out.println("After deletion");
		repo.save(alien);
		System.out.println("After saving");
		mv.addObject(alien);
		System.out.println("After adding");
		return mv;
	}
}
