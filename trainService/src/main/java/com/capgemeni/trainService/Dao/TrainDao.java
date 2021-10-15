package com.capgemeni.trainService.Dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.capgemeni.trainService.Entity.Traininfo;
import com.capgemeni.trainService.Repository.TraininfoRepository;
@Component
public class TrainDao {
	@Autowired
	TraininfoRepository traininfoRepo;
	public Traininfo addTrain(Traininfo traininfo) {
		return traininfoRepo.save(traininfo);
	}

	public List<Traininfo> getTrains() {
		return traininfoRepo.findAll();
	}
	public List<Traininfo> gettraininfobyfrom(String from) {
		return traininfoRepo.findByfrom(from);
	}

	public List<Traininfo> gettraininfobyname(String trainname) {
		return traininfoRepo.findBytrainname(trainname);
	}

}