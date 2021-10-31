package com.capgemeni.trainService.Controller;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import com.capgemeni.trainService.Dao.TrainDao;
import com.capgemeni.trainService.Entity.Traininfo;
import com.capgemeni.trainService.Repository.TraininfoRepository;
@RunWith(SpringRunner.class)
@SpringBootTest({ "server.port:0", "eureka.client.enabled:false" })
class TrainControllerTest {
	@Autowired
	private TrainController trainController;

	@MockBean
	TraininfoRepository traininfoRepo;
	
	@MockBean
	TrainDao mock_trainDao;
	
	@Test
	void testGetAllTrains() {
		List<Traininfo> list=new ArrayList<>();
		Traininfo traininfo = new Traininfo("kashmirexpress","kashmir","jamu",new Date(10/12/2021),"available",10,20,30,100.0,50.0,30.0);
		list.add(traininfo);
		Mockito.when(mock_trainDao.getTrains()).thenReturn(list);
		List<Traininfo> users = trainController.getAllTrains();
		System.out.println("USers size" + users.size());
		assertNotNull(users);
		assertEquals(users.size(), 1);
	}
	@Test
	void testAddTrain_success() throws Exception  {
		
			Traininfo traininfo = new Traininfo("kashmirexpress","kashmir","jamu",new Date(10/12/2021),"available",10,20,30,100.0,50.0,30.0);
			List<Traininfo>  trainList=new ArrayList<>();
			trainList.add(traininfo);
			Mockito.when(mock_trainDao.addTrain(traininfo)).thenReturn("success");
			String result=trainController.addTrain(traininfo);
			assertEquals(result, "success");
	}
	@Test
	void testAddTrain_failure() throws Exception  {
		
		Traininfo traininfo = new Traininfo("kashmirexpress","kashmir","jamu",new Date(10/12/2021),"available",10,20,30,100.0,50.0,30.0);
		List<Traininfo>  trainList=new ArrayList<>();
		trainList.add(traininfo);
		Mockito.when(mock_trainDao.addTrain(traininfo)).thenReturn("Failed Adding Train details");
		String result=trainController.addTrain(traininfo);
		assertEquals(result,"Failed Adding Train details");
	}

    @Ignore
	@Test
	void testFetchTrainsFromAndTo_success() throws ParseException {
		Traininfo traininfo = new Traininfo("kashmirexpress","kashmir","jamu",new Date(10/12/2021),"available",10,20,30,100.0,50.0,30.0);
		List<Traininfo>  trainList=new ArrayList<>();
		 trainList.add(traininfo);
		Mockito.when(mock_trainDao.gettraininfobyname("kashmir")).thenReturn(trainList);
		List<Traininfo> list=trainController.fetchTrainsFromAndTo("kashmir","jamu");
		assertNotNull(list);
		assertEquals(list.size(),1);
		assertEquals(list.get(0).getFrom(),"kashmir");
		
		
	}
	@Test
	void testTraininfobyname() {
		Traininfo traininfo = new Traininfo("kashmirexpress","kashmir","jamu",new Date(10/12/2021),"available",10,20,30,100.0,50.0,30.0);
		List<Traininfo>  trainList=new ArrayList<>();
		 trainList.add(traininfo);
		 Mockito.when(mock_trainDao.gettraininfobyname("kashmirexpress")).thenReturn(trainList);
		 List<Traininfo> list=trainController.traininfobyname("kashmirexpress");
		 assertNotNull(list);
		 assertEquals(list.size(),1);
		 assertEquals(list.get(0).getTrainname(),"kashmirexpress");
			
	}

	@Test
	void testUpdatetraininfo() {
		Traininfo traininfo = new Traininfo("kashmirexpress","kashmir","jamu",new Date(10/12/2021),"available",10,20,30,100.0,50.0,30.0);
		List<Traininfo>  trainList=new ArrayList<>();
		trainList.add(traininfo);
		Mockito.when(mock_trainDao.updatetrain(traininfo)).thenReturn("success");
		String result=trainController.updatetraininfo(traininfo);
		assertEquals(result, "success");
	}
	@Ignore
	@Test
	void testDeleteTrainByName() {
		fail("Not yet implemented");
	}
	@Ignore
	@Test
	void testSearchTrains() {
		fail("Not yet implemented");
	}

}
