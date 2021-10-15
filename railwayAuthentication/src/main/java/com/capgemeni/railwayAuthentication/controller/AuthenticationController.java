package com.capgemeni.railwayAuthentication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.capgemeni.railwayAuthentication.Entity.Userinfo;
import com.capgemeni.railwayAuthentication.service.AuthenticationService;


@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	
	@Autowired
	AuthenticationService authService;
	
	@GetMapping("/users")
    public List<Userinfo> getAllUsers(){
        return authService.getUsers();
    }
	
	@RequestMapping(value="/addUser",method=RequestMethod.POST)
	public @ResponseBody Userinfo addUser(@RequestParam String Username,@RequestParam String Password,@RequestParam String Contact,@RequestParam String Address,@RequestParam String Usertype) {
		Userinfo userinfo=new Userinfo();
		userinfo.setUsername(Username);
		userinfo.setAddress(Address);
		userinfo.setContact(Contact);
		userinfo.setPassword(Password);
		userinfo.setUsertype(Usertype);
		return authService.addUser(userinfo);
	}
	@PostMapping("/login")
	public @ResponseBody Userinfo login(@RequestParam String Username,@RequestParam String Password)
	{
		return authService.login(Username,Password);
	}


}
 