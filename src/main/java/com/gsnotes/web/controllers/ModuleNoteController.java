package com.gsnotes.web.controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gsnotes.bo.InscriptionAnnuelle;
import com.gsnotes.bo.InscriptionMatiere;
//import com.ensah.core.web.models.UserAndAccountInfos;
import com.gsnotes.bo.Etudiant;
import com.gsnotes.bo.Module;
import com.gsnotes.services.IInscriptionAnnuelleService;
import com.gsnotes.services.IInscriptionMatiereService;
import com.gsnotes.services.IModuleNoteService;
import com.gsnotes.services.IModuleService;
import com.gsnotes.services.INiveauService;
import com.gsnotes.services.ISessionService;
import com.gsnotes.utils.export.ExcelExporter;
import com.gsnotes.web.models.ModuleNoteModel;
import com.gsnotes.web.models.UserAndAccountInfos;


/**
 * 
 * @author Soukayna Dahou
 *
 */


@Controller
@RequestMapping("/prof")
public class ModuleNoteController {
	
	@Autowired
	private IModuleService moduleService;
	
	@Autowired
	private IModuleNoteService moduleNotesService;
	
	@Autowired
	private ISessionService sessionService;
	
	
	
	@Autowired
	private IInscriptionMatiereService inscripMatService;
	
	@Autowired
	private IInscriptionAnnuelleService inscripAnnuService;
	

	
	@Autowired
	private HttpSession httpSession;
	
	
	
	//Cette méthode initialise le formulaire de generation de fichier de notes du module
	@RequestMapping(value = "generateModuleNoteFile", method = RequestMethod.GET)
	//@GetMapping("generateModuleNoteFile")
	public String generateModuleNoteFile(Model model) {

		
		//On crée le model 
		ModuleNoteModel moduleNoteModel = new ModuleNoteModel();

		//On enregistre le modèle pour le passer à la vue
		model.addAttribute("moduleNoteModel", moduleNoteModel);

		//On ajoute la liste des modules dans le modele 
		model.addAttribute("moduleList", moduleService.getAllModules());
		
		//On ajoute également la liste des session dans le modèle
		model.addAttribute("sessionList", sessionService.getAllSessions());
		
		

		
		//On affiche la vue
		return "prof/moduleNoteForm";  
	}
	
	
	//generer le fichier 
	@RequestMapping("profHomePage")
	public String ModuleNoteFile(@ModelAttribute("moduleNoteModel") ModuleNoteModel modNoteModel, Model model) {
		//La génération du fichier est implémentée au niveau service
				//Il suffit de passer l'id du role et l'id de personne
				//à la couche service
//		Long idModule = modNoteModel.getModuleId();
//		Long idSession = modNoteModel.getSessionId();
		return "prof/profHomePage";
	}
	
	


	
	
	
	//export Module notes getMapping
	@PostMapping("exportModuleNotes")
	public void exportToExcel(HttpServletResponse response,@ModelAttribute("moduleNoteModel") ModuleNoteModel modNoteModel,
			Model model) throws IOException {
		
		//La génération du fichier est implémentée au niveau service
		//Il suffit de passer l'id du module et l'id de session(ratt,normal)
		//à la couche service par le model

		Long idModule = modNoteModel.getModuleId();
		Long idSession = modNoteModel.getSessionId();
		
		//get prof name from httpsession
		String profName = "";	
		UserAndAccountInfos userInfo = (UserAndAccountInfos) httpSession.getAttribute("userInfo");
		if(userInfo != null) {
			profName = userInfo.getNom();
		}
		
		//get list Students inscrit dans module(idModule)
		List<Etudiant> studentsModList = getAllStudentInscripModule(idModule);
		
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=moduleNotes_" + currentDateTime + ".xlsx";
		response.setHeader(headerKey, headerValue);

		//get all students inscrit dans ce module
		//List<Etudiant> students = moduleNotesService.getAllStudents();students,

		ExcelExporter excelExporter = moduleNotesService.prepareModuleNoteExport(idModule, idSession, profName, studentsModList);

		excelExporter.export(response);
	}

	
	
	
	//methode pour returner list of students inscrit dans le module selectionées using id Module
	private List<Etudiant> getAllStudentInscripModule(Long idModule){
		
//		LocalDate currentDate = LocalDate.parse(date); 
//		// Get year from date
//        int year = currentDate.getYear();
		
		
		int year = Calendar.getInstance().get(Calendar.YEAR); 
		
		List<Etudiant> studentsModList = new ArrayList<>();
		
		//get list of all inscrip Annuelles 
		List<InscriptionAnnuelle> inscripAnn = inscripAnnuService.getAllInscriptionAnnuelles();
		List<InscriptionMatiere> inscripMat = inscripMatService.getAllInscriptionMatieres();
		
		
		//parcours de inscripAnn et chercher etudiant inscrit dans ce module
		for(InscriptionAnnuelle inscAnn : inscripAnn) {
			for(InscriptionMatiere inscMat : inscAnn.getInscriptionMatieres()) {
				//on verifie l'idModule , l'annee , la classe et la validation
				if((!studentsModList.contains(inscAnn.getEtudiant())) &&
					(inscMat.getValidation()=="NV" || inscMat.getValidation() == null ) &&
					(inscAnn.getAnnee() == year ) && (inscMat.getMatiere().getModule().getIdModule() == idModule) ) {
					
					studentsModList.add(inscAnn.getEtudiant());
				}
			}
			
			//conditions
			//&& (inscMat.getValidation()=="NV" || inscMat.getValidation() == null )
			//(inscAnn.getAnnee() == year ) && 
			
			
		}
		
		
		
		return studentsModList;
		
	}
	
	
	
	
}
