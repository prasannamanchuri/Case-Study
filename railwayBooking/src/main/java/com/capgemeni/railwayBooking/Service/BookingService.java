package com.capgemeni.railwayBooking.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemeni.railwayBooking.Dao.BookingDao;
import com.capgemeni.railwayBooking.Entity.Bookinginfo;
import com.capgemeni.railwayBooking.Entity.Traininfo;

@Service
public class BookingService {
	@Autowired
	BookingDao bookingDao;

	public Map<String, Object> addpassengers(Bookinginfo bookinginfo, Traininfo[] traininfobyname) {
		Map<String, Object> map = new HashMap<>();
		String status = "";
		double amount = 0;
		Traininfo tinfo = new Traininfo();
		int passengercount = bookinginfo.getNoofadult() + bookinginfo.getNoofchildren();
		if (traininfobyname.length == 0) {
			status = "no trains found";
		} else {
			if (passengercount <= 6) {
				for (int i = 0; i < traininfobyname.length; i++) {
					if (traininfobyname[i].getTrainname().equals(bookinginfo.getTrainname())) {
						tinfo = traininfobyname[i];
						switch (bookinginfo.getTypeofclass()) {
						case "A":
							if (passengercount <= traininfobyname[i].getClass_a_seats()) {
								amount = passengercount * traininfobyname[i].getClass_a_amount();
								tinfo.setClass_a_seats(traininfobyname[i].getClass_a_seats() - passengercount);
							} else {
								map.put(status, "no ticket available in class A");
								return map;
							}
							break;
						case "B":
							if (passengercount <= traininfobyname[i].getClass_b_seats()) {
								amount = passengercount * traininfobyname[i].getClass_b_amount();
								tinfo.setClass_b_seats(traininfobyname[i].getClass_b_seats() - passengercount);
							} else {
								map.put(status, "no ticket available in class B");
								return map;
							}
							break;

						case "C":
							if (passengercount <= traininfobyname[i].getClass_c_seats()) {
								amount = passengercount * traininfobyname[i].getClass_c_amount();
								tinfo.setClass_c_seats(traininfobyname[i].getClass_c_seats() - passengercount);
							} else {
								map.put(status, "no ticket available in class C");
								return map;
							}
							break;
						default:
							status = "select Suitable class";

						}

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

		}
		map.put("status", status);
		map.put("Traininfo", tinfo);
		return map;
	}

	public  Map<String, Object> cancelation(String trainname, String username, Traininfo[] trainnameinfo, int adultCount,
			int childCount) {
		Map<String, Object> map = new HashMap<>();
		String status = "";
		double amount = 0;
		Traininfo tinfo = trainnameinfo[0];
		Bookinginfo bookingdetails = bookingDao.bookinglist(trainname, username);
		Bookinginfo actualbookinginfo = bookingdetails;
		int adult = actualbookinginfo.getNoofadult();
		int child = actualbookinginfo.getNoofchildren();
		if (adultCount > adult && childCount > actualbookinginfo.getNoofchildren())
			status="enter valid count for cancelation";
		else {
			actualbookinginfo.setNoofadult(adult - adultCount);
			actualbookinginfo.setNoofchildren(child - childCount);
			int count=adultCount+childCount;
			
			if (tinfo.getTrainname().equals(trainname)) 
				switch (actualbookinginfo.getTypeofclass()) {
				case "A":
					amount = (actualbookinginfo.getPrice()) - (count * tinfo.getClass_a_amount());
					tinfo.setClass_a_seats(trainnameinfo[0].getClass_a_seats()-count);
					
					break;
				case "B":
					amount = (actualbookinginfo.getPrice()) - (count * tinfo.getClass_b_amount());
					tinfo.setClass_b_seats(trainnameinfo[0].getClass_b_seats()-count);
					break;
				case "C":
					amount = (actualbookinginfo.getPrice()) - (count * tinfo.getClass_c_amount());
					tinfo.setClass_c_seats(trainnameinfo[0].getClass_c_seats()-count);
					break;
				default:
					status = "select Suitable class";

				}
			if(status.isEmpty()) {
				actualbookinginfo.setPrice(amount);
				if(null!= bookingDao.updatepassenger(actualbookinginfo))
				status="success";
			else
				status="cancelation failed";

			}
		}

		map.put("status", status);
		map.put("Traininfo", tinfo);
		return map;
	}

}