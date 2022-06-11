package com.gsnotes.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gsnotes.services.IInscriptionAnnuelleService;
import com.gsnotes.services.IInscriptionMatiereService;

@Controller
@RequestMapping("/studentsInscrip")
public class InscriptionMatModController {

	@Autowired
	private IInscriptionMatiereService inscripMatService;
	
	@Autowired
	private IInscriptionAnnuelleService inscripAnnuService;
	
	
	
	
	
	
}
