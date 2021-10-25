package com.capgemeni.railwayAuthentication.Dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.capgemeni.railwayAuthentication.Entity.Userinfo;
import com.capgemeni.railwayAuthentication.Repository.UserInfoRepository;

@Component
public class AuthenticationDao {
	
	@Autowired
	UserInfoRepository userinfoRepo;
	
	public List<Userinfo> getUsers() {
		return userinfoRepo.findAll();
	}
	
	public String addUser(Userinfo userinfo) {
		Userinfo user= userinfoRepo.save(userinfo);
		if(user!=null)
			return "success";
		else
			return "Failed Signup";
	}
	public List<Userinfo> getuserinfobyusername(String Username) {
		return userinfoRepo.findByusername(Username);
	}
	

}
