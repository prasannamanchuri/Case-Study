package com.capgemeni.railwayBooking.Dao;

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

import com.capgemeni.railwayBooking.Entity.Bookinginfo;
import com.capgemeni.railwayBooking.Entity.Traininfo;
import com.capgemeni.railwayBooking.Entity.Userinfo;
import com.capgemeni.railwayBooking.Repository.BookingRepository;
import com.mongodb.client.result.DeleteResult;

@Component
public class BookingDao {
	
	@Autowired
	BookingRepository bookingRepo;
	
	private final MongoTemplate mongoTemplate;

    public BookingDao(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
	
	
	public Bookinginfo addpassenger(Bookinginfo bookinginfo) {
		return bookingRepo.save(bookinginfo);
	}
	public Bookinginfo bookinglist(Traininfo traininfo,Userinfo userinfo) 
	{
		Query query = new Query();
		query.addCriteria(Criteria.where("trainInfo").is(traininfo));
		query.addCriteria(Criteria.where("userinfo").is(userinfo));
		Bookinginfo curretBookingInfo=mongoTemplate.findOne(query, Bookinginfo.class);
	    return curretBookingInfo;
	}
	public Bookinginfo editBooking(Bookinginfo bookinginfo) {
		
		/*
		 * Query query = new Query();
		 * query.addCriteria(Criteria.where("trainInfo").is(bookinginfo.getTrainInfo()))
		 * ;;
		 * query.addCriteria(Criteria.where("userinfo").is(bookinginfo.getUserinfo()));
		 * Bookinginfo curretBookingInfo=mongoTemplate.findOne(query,
		 * Bookinginfo.class);
		 */
			/*
			 * 
			 * 
			 * Javers javers = JaversBuilder.javers().build(); Diff diff =
			 * javers.compare(curretBookingInfo, bookinginfo); Update update = new Update();
			 * for(int i=0;i<diff.getChanges().size();i++) { ValueChange change =
			 * diff.getChangesByType(ValueChange.class).get(i);
			 * update.set(change.getPropertyName(), change.getRight()); }
			 * 
			 * mongoTemplate.upsert(query, update, Bookinginfo.class); return
			 * mongoTemplate.findOne(query, Bookinginfo.class);
			 */
			 
		 
			
			/*
			 * bookinginfo.setUserinfo(curretBookingInfo.getUserinfo());
			 * bookinginfo.setTrainInfo(curretBookingInfo.getTrainInfo());
			 */
		return bookingRepo.save(bookinginfo);
			 
	}


	public List<Bookinginfo> getAllBookings(Userinfo userinfo) {
		Query query = new Query();
		query.addCriteria(Criteria.where("userinfo").is(userinfo));
		return mongoTemplate.find(query, Bookinginfo.class);
	}


	public String cancelBooking(Bookinginfo bookinginfo) {
		DeleteResult result=mongoTemplate.remove(bookinginfo);
		if(result.getDeletedCount()>0)
			return "success";
		else
			return "Failed Cancelling Slot";
	}


	public List<Bookinginfo> fetchTicketDetails(String ticketNo) {
		Query query = new Query();
		query.addCriteria(Criteria.where("ticketNo").is(ticketNo));
		return mongoTemplate.find(query, Bookinginfo.class);
	}


}
