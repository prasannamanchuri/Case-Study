package com.capgemeni.trainService.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

	public String addTrain(Traininfo traininfo) {
		return trainDao.addTrain(traininfo);
	}

	public List<Traininfo> fetchTrainsFromAndTo(String from, String to) {
		List<Traininfo> list=new ArrayList<>();
		List<Traininfo> trainList = trainDao.gettraininfobyfrom(from);
		for (int i = 0; i < trainList.size(); i++) {
			if (trainList.get(i).getFrom().equals(from) && trainList.get(i).getTo().equals(to)) {
				list.add(trainList.get(i));
			}
		}
		return list;
	}

	public List<Traininfo> traininfobyname(String trainname) {
		return trainDao.gettraininfobyname(trainname);

	}

	public String updatetrain(Traininfo tinfo) {
		return trainDao.updatetrain(tinfo);
	}

	public String deleteTrainByName(String trainname) {
		return trainDao.deleteTrainByName(trainname);
	}

	public List<Traininfo> searchTrains(Map<String, String> map) {
		return trainDao.searchTrains(map);
	}
}
