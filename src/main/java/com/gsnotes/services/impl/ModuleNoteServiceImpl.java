package com.gsnotes.services.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.poi.ss.util.CellReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsnotes.services.IEtudiantService;
import com.ensah.core.web.models.UserAndAccountInfos;
import com.gsnotes.bo.Element;
import com.gsnotes.bo.Etudiant;
import com.gsnotes.bo.InscriptionAnnuelle;
import com.gsnotes.bo.Module;
import com.gsnotes.dao.IEtudiantDao;
import com.gsnotes.dao.IInscriptionModuleDao;
import com.gsnotes.dao.IModuleDao;
import com.gsnotes.dao.ISessionDao;
import com.gsnotes.services.IModuleNoteService;
import com.gsnotes.utils.export.ExcelExporter;
import com.gsnotes.bo.Niveau;
import com.gsnotes.bo.Session;


@Service
@Transactional
public class ModuleNoteServiceImpl implements IModuleNoteService{
	
	@Autowired
	private IEtudiantDao etudiantDao; 
	
	@Autowired
	private IModuleDao moduleDao;
	
	@Autowired
	private ISessionDao sessionDao;
	

	

	@Override
	public ExcelExporter prepareModuleNoteExport( Long idModule, Long idSession, String profName, List<Etudiant> studentsModList ) {
		
		Session sessionEtd = sessionDao.getById(idSession);
		Module mod= moduleDao.getById(idModule);
	
		String[] headerColNames = new String[] {"Module", "Semestre","Année", "Enseignant","Session","Classe"};
		
		//find niveau alias  and sessionName
		String classe = mod.getNiveau().getAlias();
		String examSession = String.valueOf( sessionEtd.getNomSession());   
		//obtenir toutes les matieres d'un module
		List<Element> elements = getAllElementsByModuleId(idModule);
		double[] currentCoefs = new double[elements.size()];
		
		//get allStudentsByModule : students qui sont inscrit dans ce module
		List<Etudiant> students=studentsModList ;
		
		//table de donnée des etudiants
		String[][] data = new String[students.size()][ 6+elements.size()];
		 
		//header 1
		List<String> headData = new ArrayList<String>(); 
		headData.add(mod.getTitre()); 	
		headData.add("Printemps");
		headData.add("2021/2022");
		headData.add(profName); //prof injection
		headData.add(examSession);
		headData.add(classe);
		
		
		
		//header 2:  columns
		List<String> colNames = new ArrayList<String>();
		colNames.add("ID");	colNames.add("CNE");	colNames.add("Nom");	colNames.add("Prénom");
		
		
		
		
		
		
		int i = 0;
		for (Etudiant e : students) {
			data[i][0] =Long.toString(e.getIdUtilisateur() );
			data[i][1] = e.getCne();
			data[i][2] = e.getNom();
			data[i][3] = e.getPrenom();
			
			i++;
		}
		
		int j = 0;
		//completer par la liste des elements
		for(Element elm : elements) {
			colNames.add( elm.getNom() );
			//save currrentCoeffs of each elements in currentCoefs dans le même ordre des elements 
			currentCoefs[j] = elm.getCurrentCoefficient();
			j++;
		}
		
		colNames.add("Moyenne"); colNames.add("Validation");
		
		
		
		//convert to simple array
		String[] columnNames = new String[colNames.size()];
		colNames.toArray( columnNames );
		String[] headerData = new String[headData.size()];
		headData.toArray( headerData );
		
		
		return new ExcelExporter(headerColNames, headerData, columnNames, data, currentCoefs,"ModuleNotes");
		
	}


	@Override
	public List<Element> getAllElementsByModuleId(Long id) {
		
		Module mod = moduleDao.getById(id);
		 
		return mod.getElements();	
	}
	
	
	
	
	
	

	
	@Override
	public List<Etudiant> getAllStudents() {
		return etudiantDao.findAll();
	}
	


	
	


	
	

}
