package com.capgemeni.trainService.Controller;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.capgemeni.trainService.Entity.Traininfo;
import com.capgemeni.trainService.Service.TrainService;
@RestController
@RequestMapping("/train")
public class TrainController
{
	@Autowired
	TrainService trainService;
	
	@GetMapping("/trains")
    public List<Traininfo> getAllTrains(){
        return trainService.getTrains();
    }
	
	@RequestMapping(value="/addTrain",method=RequestMethod.POST)
	public @ResponseBody Traininfo addTrain(@RequestParam String trainname,@RequestParam String from,@RequestParam String to,@RequestParam String date,@RequestParam String status,@RequestParam int class_a_seats,@RequestParam int class_b_seats,@RequestParam int class_c_seats,@RequestParam int class_a_amount,@RequestParam int class_b_amount,@RequestParam int class_c_amount) throws ParseException {
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		Traininfo traininfo=new Traininfo(trainname, from, to, sdf.parse(date),status,class_a_seats, class_b_seats, class_c_seats, class_a_amount, class_b_amount, class_c_amount);
		
		return trainService.addTrain(traininfo);
	}
	@PostMapping("/trainavailability")
	public @ResponseBody Traininfo trainavailability(@RequestParam String from,@RequestParam String to)
	{
		return trainService.trainavailability(from,to);
	}
	@PostMapping("/traininfobyname")
	public @ResponseBody List<Traininfo> traininfobyname(@RequestParam String trainname)
	{
		return trainService.traininfobyname(trainname);
	}


}
