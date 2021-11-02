package com.capgemeni.railwayAuthentication.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.capgemeni.railwayAuthentication.Entity.BankInfo;
import com.capgemeni.railwayAuthentication.Entity.Userinfo;
import com.capgemeni.railwayAuthentication.service.AuthenticationService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class AuthenticationController {
	
	static Logger logger = Logger.getLogger(AuthenticationController.class.getName());
	
	@Autowired
	AuthenticationService authService;
	
	@GetMapping("/users")
    public List<Userinfo> getAllUsers(){
        logger.debug("AuthController>>>>getAllUsers>>>>");
		return authService.getUsers();
    }
	
	@RequestMapping(value="/addUser",method=RequestMethod.POST)
	public @ResponseBody String addUser(@RequestBody Userinfo userinfo) 
	{
		return authService.addUser(userinfo);
	}
	
	@PostMapping("/login")
	public @ResponseBody Userinfo login(@RequestParam String Username,@RequestParam String Password)
	{
		return authService.login(Username,Password);
	}
	
	@PostMapping("/getUserinfoByUsername")
	public @ResponseBody Userinfo getUserinfoByUsername(@RequestParam String username)
	{
		return authService.getUserinfoByUsername(username);
	}

	@RequestMapping(value = "/editUser", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String editUser(@RequestBody Userinfo userinfo) {
		return authService.editUser(userinfo);
	}
	
	@PostMapping("/deleteUser")
	public @ResponseBody String deleteUser(@RequestParam String username) {
		return authService.deleteUser(username);
	}
	
	@RequestMapping(value="/addBanksForUser",method=RequestMethod.POST)
	public @ResponseBody String addBanksForUser(@RequestBody BankInfo bankinfo) 
	{
		logger.debug("AuthController>>>>addBanksForUser>>>>");
		return authService.addBanksForUser(bankinfo);
	}
	
	@RequestMapping(value="/editBanksForUser",method=RequestMethod.POST)
	public @ResponseBody String editBanksForUser(@RequestBody BankInfo bankinfo) 
	{
		logger.debug("AuthController>>>>editBanksForUser>>>>");
		return authService.editBanksForUser(bankinfo);
	}
	
	@RequestMapping(value="/deleteBanksForUser",method=RequestMethod.POST)
	public @ResponseBody String deleteBanksForUser(@RequestBody BankInfo bankinfo) 
	{
		logger.debug("AuthController>>>>deleteBanksForUser>>>>");
		return authService.deleteBanksForUser(bankinfo);
	}
	
	@PostMapping("/getBankInfoById")
	public @ResponseBody BankInfo getBankInfoById(@RequestParam String id)
	{
		return authService.getBankInfoById(id);
	}
	
	@RequestMapping(value="/getAllBanksByUserInfo",method=RequestMethod.POST)
	public @ResponseBody List<BankInfo> getAllBanksByUserInfo(@RequestBody Userinfo userinfo) 
	{
		logger.debug("AuthController>>>>getAllBanksByUserInfo>>>>");
		return authService.getAllBanksByUserInfo(userinfo);
	}
}
 