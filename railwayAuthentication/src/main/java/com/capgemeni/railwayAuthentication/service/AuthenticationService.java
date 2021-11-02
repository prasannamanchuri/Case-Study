package com.capgemeni.railwayAuthentication.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemeni.railwayAuthentication.Dao.AuthenticationDao;
import com.capgemeni.railwayAuthentication.Entity.BankInfo;
import com.capgemeni.railwayAuthentication.Entity.Userinfo;

@Service
public class AuthenticationService {

	@Autowired
	AuthenticationDao authDao;
	
	public List<Userinfo> getUsers() {
		return authDao.getUsers();
	}
	
	public String addUser(Userinfo userinfo) {
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

	public Userinfo getUserinfoByUsername(String username) {
		List<Userinfo> userList= authDao.getuserinfobyusername(username);
		if(userList.size()>0)
			return userList.get(0);
		else
			return null;
	}

	public String addBanksForUser(BankInfo bankinfo) {
		BankInfo bank=authDao.addBanksForUser(bankinfo);
		if(bank!=null)
			return "success";
		else
			return "Failed Adding Bank details of user";
	}

	public String editUser(Userinfo userinfo) {
		return authDao.editUser(userinfo);
	}

	public String deleteUser(String username) {
		return authDao.deleteUser(username);
	}

	public String editBanksForUser(BankInfo bankinfo) {
		return authDao.editBanksForUser(bankinfo);
	}

	public String deleteBanksForUser(BankInfo bankinfo) {
		return authDao.deleteBanksForUser(bankinfo);
	}

	public BankInfo getBankInfoById(String id) {
		return authDao.getBankInfoById(id);
	}

	public List<BankInfo> getAllBanksByUserInfo(Userinfo userinfo) {
		return authDao.getAllBanksByUserInfo(userinfo);
	}

	public String updateBalanceForCard(Userinfo userinfo, Double amount,String cardNo) {
		return authDao.updateBalanceForCard(userinfo,amount,cardNo);
	}

}
