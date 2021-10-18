package com.capgemeni.trainService.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemeni.trainService.Dao.TrainDao;
import com.capgemeni.trainService.Entity.Traininfo;

@Service
public class TrainService {
	@Autowired
	TrainDao trainDao;

	public List<Traininfo> getTrains() {
		return trainDao.getTrains();
	}

	public Traininfo addTrain(Traininfo traininfo) {
		return trainDao.addTrain(traininfo);
	}

	public Traininfo trainavailability(String from, String to) {
		List<Traininfo> trainList = trainDao.gettraininfobyfrom(from);
		for (int i = 0; i < trainList.size(); i++) {
			if (trainList.get(i).getFrom().equals(from) && trainList.get(i).getTo().equals(to)) {
				return trainList.get(i);
			} else {
				return null;
			}
		}
		return null;
	}

	public List<Traininfo> traininfobyname(String trainname) {
		return trainDao.gettraininfobyname(trainname);

	}

	public Traininfo updatetrain(Traininfo tinfo) {
		return trainDao.updatetrain(tinfo);
	}
}
