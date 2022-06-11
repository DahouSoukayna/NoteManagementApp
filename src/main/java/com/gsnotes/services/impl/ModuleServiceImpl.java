package com.gsnotes.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsnotes.bo.Element;
import com.gsnotes.bo.Module;
import com.gsnotes.dao.IModuleDao;
import com.gsnotes.services.IModuleService;

@Service
@Transactional

public class ModuleServiceImpl implements IModuleService{
	
	@Autowired
	private IModuleDao moduleDao;

	@Override
	public List<Module> getAllModules() {
		return moduleDao.findAll();
	}

//	@Override
//	public List<Element> getAllElements(Long id) {
//		
//		 Module mod = moduleDao.getById(id);
//		 
//		 
//		 return mod.getElements();
//	}

	


	
	

}
