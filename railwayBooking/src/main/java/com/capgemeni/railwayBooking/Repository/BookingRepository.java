package com.capgemeni.railwayBooking.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.capgemeni.railwayBooking.Entity.Bookinginfo;
public interface BookingRepository extends MongoRepository<Bookinginfo, Integer>
{
	public List<Bookinginfo> findByusername(String username);
}
