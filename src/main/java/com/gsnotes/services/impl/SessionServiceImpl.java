package com.gsnotes.services.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gsnotes.bo.Session;
import com.gsnotes.dao.ISessionDao;
import com.gsnotes.services.ISessionService;

@Service
@Transactional
public class SessionServiceImpl implements ISessionService {
	
	@Autowired
	ISessionDao sessionDao;

	@Override
	public List<Session> getAllSessions() {
		 
		return sessionDao.findAll();
	}

}
