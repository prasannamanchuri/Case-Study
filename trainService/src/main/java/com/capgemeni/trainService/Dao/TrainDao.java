package com.capgemeni.trainService.Dao;

import java.util.List;

import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Change;
import org.javers.core.diff.Diff;
import org.javers.core.diff.changetype.ValueChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.capgemeni.trainService.Entity.Traininfo;
import com.capgemeni.trainService.Repository.TraininfoRepository;

@Component
public class TrainDao {
	@Autowired
	TraininfoRepository traininfoRepo;

	@Autowired
	private MongoTemplate mongoTemplate;

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

	public Traininfo updatetrain(Traininfo tInfo) {
		Query query = new Query();
		query.addCriteria(Criteria.where("trainname").is(tInfo.getTrainname()));
		Traininfo curretTrainInfo=mongoTemplate.findOne(query, Traininfo.class);
		Javers javers = JaversBuilder.javers().build();
		Diff diff = javers.compare(curretTrainInfo, tInfo);
		Update update = new Update();
		for(int i=0;i<diff.getChanges().size();i++) {
			ValueChange change = diff.getChangesByType(ValueChange.class).get(i);
			update.set(change.getPropertyName(), change.getRight());
		}

		mongoTemplate.upsert(query, update, Traininfo.class);
		return mongoTemplate.findOne(query, Traininfo.class);
	}
	
	

}