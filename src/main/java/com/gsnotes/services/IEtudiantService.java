package com.gsnotes.services;

import java.util.List;

import com.gsnotes.bo.InscriptionAnnuelle;
import com.gsnotes.bo.Etudiant;

public interface IEtudiantService {
	
	public Etudiant getStudentById(Long idEtudiant);
	public List<InscriptionAnnuelle> getAllAnnualInscriptionsByStudentId(Long idEtudiant);

}
