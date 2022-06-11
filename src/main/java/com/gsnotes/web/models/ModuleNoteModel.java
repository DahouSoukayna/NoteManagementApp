package com.gsnotes.web.models;

public class ModuleNoteModel {
	
	
	private Long moduleId;
	private Long sessionId;
	private Long niveauId;
	
	

	public ModuleNoteModel() {
			
		}
	
		public Long getModuleId() {
		return moduleId;
	}



	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}



	public Long getSessionId() {
		return sessionId;
	}



	public void setSessionId(Long sessionId) {
		this.sessionId = sessionId;
	}
	
	public Long getNiveauId() {
		return niveauId;
	}

	public void setNiveauId(Long niveauId) {
		this.niveauId = niveauId;
	}

}

		
	
	
