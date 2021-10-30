package com.capgemeni.railwayBooking.Controller;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.capgemeni.railwayBooking.Dao.BookingDao;
import com.capgemeni.railwayBooking.Entity.Bookinginfo;
import com.capgemeni.railwayBooking.Entity.Traininfo;
import com.capgemeni.railwayBooking.Entity.Userinfo;

@RunWith(SpringRunner.class)
@SpringBootTest({ "server.port:0", "eureka.client.enabled:false" })
class BookingControllerTest {
	@Autowired
	private BookingController bookingController;

	@MockBean
	BookingDao bookingDao;
	
	/*
	 * @Mock private RestTemplate restTemplate;
	 */
	
	@InjectMocks
	RestTemplate restTemplate=new RestTemplate();
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	@Test
	void testGetAllBookings() {
		List<Bookinginfo> list=new ArrayList<>();
		Bookinginfo bookinginfo = new Bookinginfo("TicketId_244976786394",new Userinfo("bhanu", "manchuri", "9581050964", "madanapalli", "User"),new Traininfo("kashmirexpress","kashmir","jamu",new Date(10/12/2021),"available",10,20,30,100.0,50.0,30.0),2,2,"A",400.0,"booked");
		list.add(bookinginfo);
		Mockito.when(bookingDao.getAllBookings(Mockito.any(Userinfo.class))).thenReturn(list);
		List<Bookinginfo> bookings = bookingController.getAllBookings(new Userinfo("bhanu", "manchuri", "9581050964", "madanapalli", "User"));
		System.out.println("Booking size" + bookings.size());
		assertNotNull(bookings);
		assertEquals(bookings.size(), 1);
	}

	@Ignore
	@Test
	void testBookSeats() {
		Traininfo traininfo=new Traininfo("kashmirexpress","kashmir","jamu",new Date(10/12/2021),"available",10,20,30,100.0,50.0,30.0);
		Bookinginfo bookinginfo = new Bookinginfo("TicketId_244976786394",new Userinfo("bhanu", "manchuri", "9581050964", "madanapalli", "User"),traininfo,2,2,"A",400.0,"booked");
		Mockito.when(bookingDao.addpassenger(Mockito.any(Bookinginfo.class))).thenReturn(bookinginfo);
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Traininfo> request = new HttpEntity<Traininfo>(traininfo, headers);
		Mockito.when(restTemplate.postForEntity(Mockito.anyString(), Mockito.any(), Mockito.eq(String.class), Mockito.<Object>any())).thenReturn(new ResponseEntity<String>("success", HttpStatus.ACCEPTED));
		String status=bookingController.bookSeats(bookinginfo);
		assertEquals(status, "success");
	}

	@Test
	void testFetchTicketDetails() {
		List<Bookinginfo> list=new ArrayList<>();
		Bookinginfo bookinginfo = new Bookinginfo("TicketId_244976786394",new Userinfo("bhanu", "manchuri", "9581050964", "madanapalli", "User"),new Traininfo("kashmirexpress","kashmir","jamu",new Date(10/12/2021),"available",10,20,30,100.0,50.0,30.0),2,2,"A",400.0,"booked");
		list.add(bookinginfo);
		Mockito.when(bookingDao.fetchTicketDetails(Mockito.anyString())).thenReturn(list);
		List<Bookinginfo> bookings = bookingController.fetchTicketDetails("TicketId_244976786394");
		System.out.println("Booking size" + bookings.size());
		assertNotNull(bookings);
		assertEquals(bookings.size(), 1);
		
	}

}
