package com.capgemeni.railwayBooking.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.capgemeni.railwayBooking.Entity.BankInfo;
import com.capgemeni.railwayBooking.Entity.Bookinginfo;
import com.capgemeni.railwayBooking.Entity.Traininfo;
import com.capgemeni.railwayBooking.Entity.Userinfo;
import com.capgemeni.railwayBooking.Service.BookingService;

@RestController
@RequestMapping("/booking")
@CrossOrigin(origins = "*")
public class BookingController {

	@Autowired
	BookingService bookingService;

	@PostMapping("/traininfobyname")
	public Traininfo[] traininfobyname(@RequestParam String trainname) {
		return gettraininfobytrainname(trainname);
	}
	
	@PostMapping("/getAllBookings")
	public List<Bookinginfo> getAllBookings(@RequestBody Userinfo userinfo) {
		return bookingService.getAllBookings(userinfo);
	}

	@GetMapping("/trains")
	public Traininfo[] trains() {
		String url="http://localhost:8100/trainService/train/trains";
		RestTemplate restTemplate = new RestTemplate();
		Traininfo[] listTraininfo = restTemplate.getForEntity(url, Traininfo[].class).getBody();
		return listTraininfo;
	}
	
	@PostMapping("/fetchTrainsFromAndTo")
	public Traininfo[] fetchTrainsFromAndTo(@RequestParam String from,@RequestParam String to) {
		String url="http://localhost:8100/trainService/train/fetchTrainsFromAndTo";
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, String> map=new LinkedMultiValueMap<String, String>();
		map.add("from", from);
		map.add("to", to);
		Traininfo[] listTraininfo = restTemplate.postForEntity(url, map,Traininfo[].class).getBody();
		return listTraininfo;
	}

	@PostMapping("/bookSeats")
	public @ResponseBody String bookSeats(@RequestPart("bookingInfo") Bookinginfo bookingInfo,@RequestParam String cardNo) {
		String status="";
		Map<String, Object> result = bookingService.addpassengers(bookingInfo);
		System.out.println("Result......."+result);
		if (((String) result.get("status")).equals("booking success")) {
			String url="http://localhost:8100/trainService/train/updatetraininfo";
			Traininfo trainInfo = (Traininfo) result.get("Traininfo");
			MultiValueMap<String, Traininfo> map = new LinkedMultiValueMap<String, Traininfo>();
			map.add("traininfo", trainInfo);
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Traininfo> request = new HttpEntity<Traininfo>(trainInfo, headers);
			String trainUpdateStatus = restTemplate.postForEntity(url, request, String.class).getBody();
			if(!trainUpdateStatus.equalsIgnoreCase("success"))
				status=trainUpdateStatus;
			else
				status=(String) result.get("status");
			BankInfo[] bankList=getAllBanksByUserInfo(((Userinfo) result.get("userinfo")));
			for(BankInfo bank:bankList) {
				if(bank.getCardNo().equalsIgnoreCase(cardNo)) {
					bank.setBalance(bank.getBalance()-((Double) result.get("price")));
					String userStatus=updateBalance(bank);
					if(!userStatus.equalsIgnoreCase("success"))
						status=userStatus;
					else
						status=(String) result.get("status");
				}
			}
		}
		return status;

	}
	
	private BankInfo[] getAllBanksByUserInfo(Userinfo userinfo) {
		String url="http://localhost:8100/railwayAuthentication/auth/getAllBanksByUserInfo";
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.postForEntity(url, userinfo, BankInfo[].class).getBody();
	}
	
	private String updateBalance(BankInfo bankinfo) {
		String url="http://localhost:8100/railwayAuthentication/auth/editBanksForUser";
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.postForEntity(url, bankinfo, String.class).getBody();
	}

	private Traininfo[] gettraininfobytrainname(String trainname) {
	   String url="http://localhost:8100/trainService/train/traininfobyname";
	   MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
	   map.add("trainname", trainname);
	   RestTemplate restTemplate = new RestTemplate();
	   Traininfo[] listTraininfo = restTemplate.postForEntity(url, map, Traininfo[].class).getBody();
	   return listTraininfo;
	}
	
	private Userinfo getUserinfobytrainname(String username) {
		   String url="http://localhost:8100/railwayAuthentication/auth/getUserinfoByUsername";
		   MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		   map.add("username", username);
		   RestTemplate restTemplate = new RestTemplate();
		   return restTemplate.postForEntity(url, map, Userinfo.class).getBody();
	}
	
	
	@PostMapping("/editBooking")
	public @ResponseBody String editBooking(@RequestBody Bookinginfo bookinginfo)
	{
		String status="";
		Map<String, Object> result = bookingService.editBooking(bookinginfo);
		if (((String) result.get("status")).equals("success")) {
			String url="http://localhost:8100/trainService/train/updatetraininfo";
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			Traininfo trainInfo = (Traininfo) result.get("Traininfo");
			RestTemplate restTemplate = new RestTemplate();
			String trainUpdateStatus = restTemplate.postForEntity(url, trainInfo, String.class).getBody();
			if(!trainUpdateStatus.equalsIgnoreCase("success"))
				status=trainUpdateStatus;
			else
				status=(String) result.get("status");
		}
		return status;
	}
	
	@PostMapping("/cancelBooking")
	public @ResponseBody String cancelBooking(@RequestBody Bookinginfo bookinginfo)
	{
		String status=bookingService.cancelBooking(bookinginfo);
		if (status.equalsIgnoreCase("success")) {
			String url="http://localhost:8100/trainService/train/updatetraininfo";
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			Traininfo trainInfo = bookinginfo.getTrainInfo();
			RestTemplate restTemplate = new RestTemplate();
			status = restTemplate.postForEntity(url, trainInfo, String.class).getBody();
		}
		return status;
	}
	
	@PostMapping("/fetchTicketDetails")
	public @ResponseBody List<Bookinginfo> fetchTicketDetails(@RequestParam String ticketNo) {
		return bookingService.fetchTicketDetails(ticketNo);
	}
}
	
