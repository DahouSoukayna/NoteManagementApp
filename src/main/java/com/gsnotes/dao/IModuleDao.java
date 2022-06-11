package com.gsnotes.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gsnotes.bo.Module;
import com.gsnotes.bo.Element;

public interface IModuleDao extends JpaRepository<Module, Long>{
	
	
	//public List<Element> getElementsByModuleId(Long id);
	
	

}
