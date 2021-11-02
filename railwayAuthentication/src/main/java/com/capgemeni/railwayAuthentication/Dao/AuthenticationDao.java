package com.capgemeni.railwayAuthentication.Dao;

import java.util.List;

import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.javers.core.diff.changetype.ValueChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.capgemeni.railwayAuthentication.Entity.BankInfo;
import com.capgemeni.railwayAuthentication.Entity.Userinfo;
import com.capgemeni.railwayAuthentication.Repository.BankInfoRepository;
import com.capgemeni.railwayAuthentication.Repository.UserInfoRepository;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

@Component
public class AuthenticationDao {
	
	@Autowired
	UserInfoRepository userinfoRepo;
	
	@Autowired
	BankInfoRepository bankinfoRepo;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
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

	public BankInfo addBanksForUser(BankInfo bankinfo) {
		return bankinfoRepo.save(bankinfo);
	}

	public String editUser(Userinfo userinfo) {
		Query query = new Query();
		query.addCriteria(Criteria.where("username").is(userinfo.getUsername()));
		Userinfo curretUserInfo=mongoTemplate.findOne(query, Userinfo.class);
		Javers javers = JaversBuilder.javers().build();
		Diff diff = javers.compare(curretUserInfo, userinfo);
		Update update = new Update();
		for(int i=0;i<diff.getChanges().size();i++) {
			ValueChange change = diff.getChangesByType(ValueChange.class).get(i);
			if(!change.getPropertyName().equalsIgnoreCase("_id"))
				update.set(change.getPropertyName(), change.getRight());
		}

		UpdateResult result=mongoTemplate.updateFirst(query, update, Userinfo.class);
		if(result.getMatchedCount()==0)
			return "Failed Updating";
		else
			return "success";
	}

	public String deleteUser(String username) {
		Query query = new Query();
		query.addCriteria(Criteria.where("username").is(username));
		Userinfo curretUserInfo=mongoTemplate.findOne(query, Userinfo.class);
		DeleteResult result=mongoTemplate.remove(curretUserInfo);
		if(result.getDeletedCount()>0)
			return "success";
		else
			return "Failed deleting UserInfo";
	}

	public String editBanksForUser(BankInfo bankinfo) {
		Query query = new Query();
		query.addCriteria(Criteria.where("bankname").is(bankinfo.getBankname()));
		query.addCriteria(Criteria.where("userinfo").is(bankinfo.getUserinfo()));
		BankInfo curretBankInfo=mongoTemplate.findOne(query, BankInfo.class);
		Javers javers = JaversBuilder.javers().build();
		Diff diff = javers.compare(curretBankInfo, bankinfo);
		Update update = new Update();
		for(int i=0;i<diff.getChanges().size();i++) {
			ValueChange change = diff.getChangesByType(ValueChange.class).get(i);
			if(!change.getPropertyName().equalsIgnoreCase("_id"))
				update.set(change.getPropertyName(), change.getRight());
		}

		UpdateResult result=mongoTemplate.updateFirst(query, update, BankInfo.class);
		if(result.getMatchedCount()==0)
			return "Failed Updating";
		else
			return "success";
	}

	public String deleteBanksForUser(BankInfo bankinfo) {
		DeleteResult result=mongoTemplate.remove(bankinfo);
		if(result.getDeletedCount()>0)
			return "success";
		else
			return "Failed deleting UserInfo";
	}

	public BankInfo getBankInfoById(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id));
		return mongoTemplate.findOne(query, BankInfo.class);
	}

	public List<BankInfo> getAllBanksByUserInfo(Userinfo userinfo) {
		return bankinfoRepo.findByuserinfo(userinfo);
	}

	public String updateBalanceForCard(Userinfo userinfo, Double amount,String cardNo) {
		Query query = new Query();
		query.addCriteria(Criteria.where("cardNo").is(cardNo));
		query.addCriteria(Criteria.where("userinfo").is(userinfo));
		BankInfo curretBankInfo=mongoTemplate.findOne(query, BankInfo.class);
		Update update = new Update();
		update.set("balance", curretBankInfo.getBalance()-amount);
		UpdateResult result=mongoTemplate.updateFirst(query, update, BankInfo.class);
		if(result.getMatchedCount()==0)
			return "Failed Updating";
		else
			return "success";
	}
	

}
