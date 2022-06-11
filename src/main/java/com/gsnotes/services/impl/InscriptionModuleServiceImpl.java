package com.gsnotes.services.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gsnotes.bo.InscriptionModule;
import com.gsnotes.dao.IInscriptionModuleDao;
import com.gsnotes.services.IInscriptionModuleService;




@Service
@Transactional
public class InscriptionModuleServiceImpl implements IInscriptionModuleService{
	
	@Autowired
	IInscriptionModuleDao inscripModuleDao;
	

	public List<InscriptionModule> getAllInscriptionModules(){
		return inscripModuleDao.findAll();
	}
	
}
