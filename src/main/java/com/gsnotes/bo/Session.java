package com.gsnotes.bo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Session {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idSession;
	
	private String nomSession;

	
	
	public Long getIdSession() {
		return idSession;
	}

	public void setIdSession(Long idSession) {
		this.idSession = idSession;
	}

	public String getNomSession() {
		return nomSession;
	}

	public void setNomSession(String nomSession) {
		this.nomSession = nomSession;
	}

	
	

}
