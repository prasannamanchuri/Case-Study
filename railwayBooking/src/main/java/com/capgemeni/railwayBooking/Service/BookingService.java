package com.capgemeni.railwayBooking.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemeni.railwayBooking.Dao.BookingDao;
import com.capgemeni.railwayBooking.Entity.Bookinginfo;
import com.capgemeni.railwayBooking.Entity.Traininfo;
import com.capgemeni.railwayBooking.Entity.Userinfo;

@Service
public class BookingService {
	@Autowired
	BookingDao bookingDao;

	public Map<String, Object> addpassengers(Bookinginfo bookinginfo) {
		Map<String, Object> map = new HashMap<>();
		String status = "";
		double amount = 0;
		Traininfo tinfo = new Traininfo();
		int passengercount = bookinginfo.getNoofadult() + bookinginfo.getNoofchildren();
		if (passengercount <= 6) {
			if (bookinginfo.getTrainInfo().getTrainname().equals(bookinginfo.getTrainInfo().getTrainname())) {
				tinfo = bookinginfo.getTrainInfo();
				String type="";
				if(bookinginfo.getTypeofclass().contains("-")) {
					type=bookinginfo.getTypeofclass().split("-")[0];
					bookinginfo.setTypeofclass(type);
				}
				else
					type=bookinginfo.getTypeofclass();
				switch (type) {
				case "A":
					if (passengercount <= bookinginfo.getTrainInfo().getClass_a_seats()) {
						amount = passengercount * bookinginfo.getTrainInfo().getClass_a_amount();
						tinfo.setClass_a_seats(bookinginfo.getTrainInfo().getClass_a_seats() - passengercount);
					} else {
						map.put("status", "no ticket available in class A");
						return map;
					}
					break;
				case "B":
					if (passengercount <= bookinginfo.getTrainInfo().getClass_b_seats()) {
						amount = passengercount * bookinginfo.getTrainInfo().getClass_b_amount();
						tinfo.setClass_b_seats(bookinginfo.getTrainInfo().getClass_b_seats() - passengercount);
					} else {
						map.put("status", "no ticket available in class B");
						return map;
					}
					break;

				case "C":
					if (passengercount <= bookinginfo.getTrainInfo().getClass_c_seats()) {
						amount = passengercount * bookinginfo.getTrainInfo().getClass_c_amount();
						tinfo.setClass_c_seats(bookinginfo.getTrainInfo().getClass_c_seats() - passengercount);
					} else {
						map.put("status", "no ticket available in class C");
						return map;
					}
					break;
				default:
					status = "select Suitable class";

				}

			}
	
			bookinginfo.setPrice(amount);
			Bookinginfo bkInfo = bookingDao.addpassenger(bookinginfo);
			if (bkInfo == null) {
				status = "booking failed";
			} else {
				status = "booking success";
			}
		} else {
			status = "you can book max of 6 tickets with one username";
		}
		map.put("status", status);
		map.put("Traininfo", tinfo);
		Userinfo user=bookinginfo.getUserinfo();
		map.put("userinfo", user);
		map.put("price", amount);
		return map;
	}

	public  Map<String, Object> editBooking(Bookinginfo bookingInfo) {
		Map<String, Object> map = new HashMap<>();
		String status = "";
		double amount = 0;
		Traininfo tinfo=bookingInfo.getTrainInfo();
		Bookinginfo actualbookinginfo = bookingDao.bookinglist(bookingInfo.getTrainInfo(), bookingInfo.getUserinfo());
		if (actualbookinginfo.getNoofadult() > bookingInfo.getNoofadult() && actualbookinginfo.getNoofchildren() > bookingInfo.getNoofchildren())
			status="enter valid count for cancelation";
		else {
				actualbookinginfo.setNoofadult(bookingInfo.getNoofadult());
				actualbookinginfo.setNoofchildren(bookingInfo.getNoofchildren());
				int count=bookingInfo.getNoofadult()+bookingInfo.getNoofchildren();
				switch (bookingInfo.getTypeofclass()) {
					case "A":
						amount = (actualbookinginfo.getPrice()) - (count * tinfo.getClass_a_amount());
						tinfo.setClass_a_seats(tinfo.getClass_a_seats()-count);
						
						break;
					case "B":
						amount = (actualbookinginfo.getPrice()) - (count * tinfo.getClass_b_amount());
						tinfo.setClass_b_seats(tinfo.getClass_b_seats()-count);
						break;
					case "C":
						amount = (actualbookinginfo.getPrice()) - (count * tinfo.getClass_c_amount());
						tinfo.setClass_c_seats(tinfo.getClass_c_seats()-count);
						break;
					default:
						status = "select Suitable class";
				}
			if(status.isEmpty()) {
				actualbookinginfo.setPrice(amount);
				if(null!= bookingDao.editBooking(actualbookinginfo))
				status="success";
			}
			else
				status="cancelation failed";
		}
		map.put("status", status);
		map.put("Traininfo", tinfo);
		return map;
	}

	public List<Bookinginfo> getAllBookings(Userinfo userinfo) {
		return bookingDao.getAllBookings(userinfo);
	}

	public String cancelBooking(Bookinginfo bookinginfo) {
		Bookinginfo binfo= bookingDao.editBooking(bookinginfo);
		if(binfo!=null)
			return "success";
		else
			return "Failed cancelling ticket";
	}

	public List<Bookinginfo> fetchTicketDetails(String ticketNo) {
		return bookingDao.fetchTicketDetails(ticketNo);
	}

}