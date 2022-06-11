package com.gsnotes.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gsnotes.bo.InscriptionMatiere;


public interface IInscriptionMatiereService {
	
	List<InscriptionMatiere> getAllInscriptionMatieres();

}
