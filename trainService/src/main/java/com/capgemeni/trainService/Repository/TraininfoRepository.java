package com.capgemeni.trainService.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.capgemeni.trainService.Entity.Traininfo;
public interface TraininfoRepository extends MongoRepository<Traininfo, Integer>
{
	
	public List<Traininfo> findBytrainname(String trainname);

	public List<Traininfo> findByfrom(String from);
	 
}
