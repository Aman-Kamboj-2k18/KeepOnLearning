package com.aman.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.aman.demo.Model.Alien;

public interface AlienRepo extends JpaRepository<Alien, Integer> {

	@Query("select a from Alien a where a.aname=?1")
	List<Alien> abc(String name);

}