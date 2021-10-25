package com.capgemeni.trainService.Controller;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.capgemeni.trainService.Entity.Traininfo;
import com.capgemeni.trainService.Service.TrainService;

@RestController
@RequestMapping("/train")
@CrossOrigin(origins = "*")
public class TrainController {
	@Autowired
	TrainService trainService;

	@GetMapping("/trains")
	public List<Traininfo> getAllTrains() {
		return trainService.getTrains();
	}

	@RequestMapping(value = "/addTrain", method = RequestMethod.POST)
	public @ResponseBody String addTrain(@RequestBody Traininfo traininfo)
			throws ParseException {
		return trainService.addTrain(traininfo);
	}
	
	@RequestMapping(value="/fetchTrainsFromAndTo",method=RequestMethod.POST)
	public @ResponseBody List<Traininfo> fetchTrainsFromAndTo(@RequestParam String from,@RequestParam String to) {
		return trainService.fetchTrainsFromAndTo(from,to);
	}

	@PostMapping("/traininfobyname")
	public @ResponseBody List<Traininfo> traininfobyname(@RequestParam String trainname) {
		return trainService.traininfobyname(trainname);
	}

	@RequestMapping(value = "/updatetraininfo", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String updatetraininfo(@RequestBody Traininfo traininfo) {
		return trainService.updatetrain(traininfo);
	}
	
	@PostMapping("/deleteTrainByName")
	public @ResponseBody String deleteTrainByName(@RequestParam String trainname) {
		return trainService.deleteTrainByName(trainname);
	}
	
	@PostMapping("/searchTrains")
	public @ResponseBody List<Traininfo> searchTrains(@RequestParam Map<String,String> map) {
		return trainService.searchTrains(map);
	}

}
