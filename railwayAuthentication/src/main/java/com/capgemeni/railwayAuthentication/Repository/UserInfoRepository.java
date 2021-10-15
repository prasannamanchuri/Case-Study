package com.capgemeni.railwayAuthentication.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import com.capgemeni.railwayAuthentication.Entity.Userinfo;

public interface UserInfoRepository extends MongoRepository<Userinfo, Integer>{
	/*@Modifying
	@Query("select * from Userinfo where UserName=:UserNameandPassword=:Password")
	 public void updateBalance(@Param("Username") String
	 Username,@Param("Password") String password);*/
	public List<Userinfo> findByusername(String Username);
	 
	
	
}
