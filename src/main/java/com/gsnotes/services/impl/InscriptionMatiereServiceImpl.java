package com.gsnotes.services.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gsnotes.bo.InscriptionMatiere;
import com.gsnotes.dao.IInscriptionMatiereDao;
import com.gsnotes.services.IInscriptionMatiereService;

@Service
@Transactional
public class InscriptionMatiereServiceImpl implements IInscriptionMatiereService{

	
	@Autowired
	IInscriptionMatiereDao inscripMatDao;
	
	
	@Override
	public List<InscriptionMatiere> getAllInscriptionMatieres() {
		return inscripMatDao.findAll();
	}
	
	

}
