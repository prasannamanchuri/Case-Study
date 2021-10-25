package com.capgemeni.railwayBooking.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.capgemeni.railwayBooking.Entity.Bookinginfo;
public interface BookingRepository extends MongoRepository<Bookinginfo, String>
{
	@Query(value = "{'userinfo.username': ?0}", fields = "{'userinfo' : 0}")
	public List<Bookinginfo> findByuserinfo(String username);

	public List<Bookinginfo> findByticketNo(String ticketNo);
}
