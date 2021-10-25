package com.capgemeni.trainService.Dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

import com.capgemeni.trainService.Entity.Traininfo;
import com.capgemeni.trainService.Repository.TraininfoRepository;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

@Component
public class TrainDao {
	@Autowired
	TraininfoRepository traininfoRepo;

	@Autowired
	private MongoTemplate mongoTemplate;

	public String addTrain(Traininfo traininfo) {
		Traininfo tInfo= traininfoRepo.save(traininfo);
		if(tInfo!=null)
			return "success";
		else
			return "Failed Adding Train details";
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

	public String updatetrain(Traininfo tInfo) {
		Query query = new Query();
		query.addCriteria(Criteria.where("trainname").is(tInfo.getTrainname()));
		Traininfo curretTrainInfo=mongoTemplate.findOne(query, Traininfo.class);
		Javers javers = JaversBuilder.javers().build();
		Diff diff = javers.compare(curretTrainInfo, tInfo);
		Update update = new Update();
		for(int i=0;i<diff.getChanges().size();i++) {
			ValueChange change = diff.getChangesByType(ValueChange.class).get(i);
			if(!change.getPropertyName().equalsIgnoreCase("_id"))
				update.set(change.getPropertyName(), change.getRight());
		}

		UpdateResult result=mongoTemplate.updateFirst(query, update, Traininfo.class);
		if(result.getMatchedCount()==0)
			return "Failed Updating";
		else
			return "success";
	}

	public String deleteTrainByName(String trainname) {
		Query query = new Query();
		query.addCriteria(Criteria.where("trainname").is(trainname));
		Traininfo curretTrainInfo=mongoTemplate.findOne(query, Traininfo.class);
		DeleteResult result=mongoTemplate.remove(curretTrainInfo);
		if(result.getDeletedCount()>0)
			return "success";
		else
			return "Failed deleting TrainInfo";
	}

	public List<Traininfo> searchTrains(Map<String, String> map) {
		Query query = new Query();
		query.addCriteria(Criteria.where("from").is(map.get("from")));
		query.addCriteria(Criteria.where("to").is(map.get("to")));
		Date startDate=new Date(map.get("seldate"));
		System.out.println(getNextDate(startDate).toString());
		Date endDate=getNextDate(startDate);
		query.addCriteria(Criteria.where("date").gte(startDate).lt(endDate));
		List<Traininfo> trainList= mongoTemplate.find(query, Traininfo.class);
		return trainList;
	}
	
	public static Date getNextDate(Date startDate) {
		final Calendar calendar = Calendar.getInstance();
		  calendar.setTime(startDate);
		  calendar.add(Calendar.DAY_OF_YEAR, 1);
		  return calendar.getTime(); 
		  
		}
	
	

}