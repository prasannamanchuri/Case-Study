package com.capgemeni.railwayAuthentication.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemeni.railwayAuthentication.Dao.AuthenticationDao;
import com.capgemeni.railwayAuthentication.Entity.Userinfo;

@Service
public class AuthenticationService {

	@Autowired
	AuthenticationDao authDao;
	
	public List<Userinfo> getUsers() {
		return authDao.getUsers();
	}
	
	public Userinfo addUser(Userinfo userinfo) {
		return authDao.addUser(userinfo);
	}
	public Userinfo login(String Username,String Password) {
		List<Userinfo>  userList=authDao.getuserinfobyusername(Username);
		for( int i=0;i<userList.size();i++)
		{
			if(userList.get(i).getUsername().equals(Username)&&userList.get(i).getPassword().equals(Password))
			{
				return userList.get(i);
			}
			else
			{
				return null;
			}
		}
		return null;
		
	
		
	}

}
