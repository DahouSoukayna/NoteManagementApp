package com.gsnotes.services;

import java.util.List;

import com.gsnotes.bo.Element;
import com.gsnotes.bo.Etudiant;
import com.gsnotes.bo.Module;
import com.gsnotes.utils.export.ExcelExporter;



public interface IModuleNoteService {
	
	public List<Etudiant> getAllStudents();
	
	public List<Element> getAllElementsByModuleId(Long id);
	
	//public List<Etudiant> getAllStudentsByModuleId(Long id);
	
	public ExcelExporter prepareModuleNoteExport(Long idModule, Long idSession, String profName, List<Etudiant> studentsModList);

}
//List<Etudiant> students