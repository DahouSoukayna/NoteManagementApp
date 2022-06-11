package com.gsnotes.services.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gsnotes.bo.InscriptionAnnuelle;
import com.gsnotes.bo.Etudiant;
import com.gsnotes.dao.IInscriptionAnnuelleDao;
import com.gsnotes.dao.IInscriptionMatiereDao;
import com.gsnotes.dao.IInscriptionModuleDao;
import com.gsnotes.services.IInscriptionAnnuelleService;

@Service
@Transactional
public class InscriptionAnnuelleServiceImpl implements IInscriptionAnnuelleService{

	@Autowired
	IInscriptionAnnuelleDao inscripAnnDao ;
	
	
	@Override
	public List<InscriptionAnnuelle> getAllInscriptionAnnuelles() {
		return inscripAnnDao.findAll();
	}
	
	
	
	
}
