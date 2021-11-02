package com.capgemeni.railwayAuthentication.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.capgemeni.railwayAuthentication.Entity.BankInfo;
import com.capgemeni.railwayAuthentication.Entity.Userinfo;

public interface BankInfoRepository extends MongoRepository<BankInfo, String>{
	public List<BankInfo> findBybankname(String bankname);
	public List<BankInfo> findByuserinfo(Userinfo userinfo);
	
	
}
