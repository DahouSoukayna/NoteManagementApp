package com.gsnotes.services.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gsnotes.bo.Niveau;
import com.gsnotes.dao.INiveauDao;
import com.gsnotes.services.INiveauService;



@Service
@Transactional
public class INiveauServiceImpl implements INiveauService{
	
	@Autowired
	INiveauDao niveauDao;
	
	

	@Override
	public List<Niveau> getAllNiveaux() {
		return niveauDao.findAll();
	}

}
