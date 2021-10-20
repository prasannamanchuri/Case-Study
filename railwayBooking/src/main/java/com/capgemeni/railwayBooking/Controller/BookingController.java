package com.capgemeni.railwayBooking.Controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.capgemeni.railwayBooking.Entity.Bookinginfo;
import com.capgemeni.railwayBooking.Entity.Traininfo;
import com.capgemeni.railwayBooking.Service.BookingService;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.Applications;

@RestController
@RequestMapping("/booking")
public class BookingController {

	@Autowired
	BookingService bookingService;

	
	 // @Autowired 
	  //RestTemplate restTemplate = new RestTemplate();
	 
		/*
		 * @Autowired DiscoveryClient discoveryClient;
		 * 
		 * @Autowired private EurekaClient eurekaClient;
		 */

	@PostMapping("/traininfobyname")
	public Traininfo[] traininfobyname(@RequestParam String trainname) {
		BookingController bookingcontroller = new BookingController();
		return bookingcontroller.gettraininfobytrainname(trainname);
	}

	@GetMapping("/trains")
	public Traininfo[] trains() {
		 String url="http://localhost:8100/trainService/train/trains";
		/*Application application = eurekaClient.getApplication("trainService");
		InstanceInfo instanceInfo = application.getInstances().get(0);
		String url = "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/" + "train/trains";
		System.out.println("URL" + url);*/
		 RestTemplate restTemplate = new RestTemplate();
		Traininfo[] listTraininfo = restTemplate.getForEntity(url, Traininfo[].class).getBody();
		return listTraininfo;
	}

	@PostMapping("/addpassengers")
	public @ResponseBody String addpassengers(@RequestParam String username, @RequestParam String trainname,
			@RequestParam int noofadult, @RequestParam int noofchildren, @RequestParam String typeofclass) {
		Bookinginfo bookinginfo = new Bookinginfo();
		bookinginfo.setUsername(username);
		bookinginfo.setTrainname(trainname);
		bookinginfo.setNoofadult(noofadult);
		bookinginfo.setNoofchildren(noofchildren);
		bookinginfo.setTypeofclass(typeofclass);
		Traininfo[] trainnameinfo = gettraininfobytrainname(trainname);
		Map<String, Object> result = bookingService.addpassengers(bookinginfo, trainnameinfo);
		System.out.println("Result......."+result);
		if (((String) result.get("status")).equals("booking success")) {
			String url="http://localhost:8100/trainService/train/updatetraininfo";
			/*Application application = eurekaClient.getApplication("trainService");
			InstanceInfo instanceInfo = application.getInstances().get(0);
			String url = "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/"
					+ "train/updatetraininfo";
			System.out.println("URL" + url);*/
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			Traininfo trainInfo = (Traininfo) result.get("Traininfo");
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<Traininfo> listTraininfo = restTemplate.postForEntity(url, trainInfo, Traininfo.class);
			Traininfo traininfo = listTraininfo.getBody();
		}
		return (String) result.get("status");

	}

	private Traininfo[] gettraininfobytrainname(String trainname) {
	   String url="http://localhost:8100/trainService/train/traininfobyname";
	   /*List<InstanceInfo> instances=discoveryClient.getInstancesById("trainService");
		InstanceInfo instanceInfo=instances.get(0);
		String url="http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/"
		+ "train/updatetraininfo";
		System.out.println("URL" + url);
		/*Application application = eurekaClient.getApplication("trainService");
		InstanceInfo instanceInfo = application.getInstances().get(0);
		String url = "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/"
				+ "train/traininfobyname";
		System.out.println("URL" + url);*/
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("trainname", trainname);
		
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		RestTemplate restTemplate = new RestTemplate();
		Traininfo[] listTraininfo = restTemplate.postForEntity(url, request, Traininfo[].class).getBody();
		return listTraininfo;
	}
	@PostMapping("/cancelation")
	public @ResponseBody String cancelation(@RequestParam String username, @RequestParam String trainname,@RequestParam  int adultCount,@RequestParam  int childCount, @RequestParam String typeofclass)
	{
		Traininfo[] trainnameinfo = gettraininfobytrainname(trainname);
		Map<String, Object> result = bookingService.cancelation(trainname,username,trainnameinfo,adultCount,childCount,typeofclass);
		if (((String) result.get("status")).equals("booking success")) {
			String url="http://localhost:8100/trainService/train/updatetraininfo";
			/*List<InstanceInfo> instances=discoveryClient.getInstancesById("trainService");
			InstanceInfo instanceInfo=instances.get(0);
			String url="http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/"
			+ "train/updatetraininfo";
			System.out.println("URL" + url);
			Application application = eurekaClient.getApplication("trainService");
			InstanceInfo instanceInfo = application.getInstances().get(0);
			String url = "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/"
					+ "train/updatetraininfo";
			System.out.println("URL" + url);*/
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			Traininfo trainInfo = (Traininfo) result.get("Traininfo");
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<Traininfo> listTraininfo = restTemplate.postForEntity(url, trainInfo, Traininfo.class);
			Traininfo traininfo = listTraininfo.getBody();
		}
		return (String) result.get("status");
	}
}
	
