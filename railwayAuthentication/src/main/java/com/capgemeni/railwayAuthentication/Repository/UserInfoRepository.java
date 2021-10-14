package com.capgemeni.railwayAuthentication.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.capgemeni.railwayAuthentication.Entity.Userinfo;

public interface UserInfoRepository extends MongoRepository<Userinfo, Integer>{
	public List<Userinfo> findById(int UserId);
	
	/*
	 * @Modifying
	 * 
	 * @Query("update bankaccount set accountbalance=:accountBalance where accountId=:accountId"
	 * ) public void updateBalance(@Param("accountId") int
	 * accountId,@Param("accountBalance") double accountBalance);
	 */
}
