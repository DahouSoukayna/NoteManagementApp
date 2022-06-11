package com.gsnotes.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsnotes.services.IEtudiantService;
import com.gsnotes.bo.Etudiant;
import com.gsnotes.bo.InscriptionAnnuelle;
import com.gsnotes.dao.IEtudiantDao;


@Service
@Transactional
public class EtudiantServiceImpl implements IEtudiantService {
	
	@Autowired
	IEtudiantDao etudiantDao;
	
	
	
	@Override
	public Etudiant getStudentById(Long idEtudiant) {
		return etudiantDao.findById(idEtudiant).get();
	}

	@Override
	public List<InscriptionAnnuelle> getAllAnnualInscriptionsByStudentId(Long idEtudiant) {
		return getStudentById(idEtudiant).getInscriptions();
	}



	
	
	

}
