package com.gsnotes.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gsnotes.bo.Session;

public interface ISessionDao extends JpaRepository<Session, Long>{

}
