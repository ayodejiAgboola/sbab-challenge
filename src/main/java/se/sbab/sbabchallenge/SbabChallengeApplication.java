package se.sbab.sbabchallenge;

import org.javatuples.Pair;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import se.sbab.sbabchallenge.service.SLService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class SbabChallengeApplication {

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(SbabChallengeApplication.class, args);
		SLService s = context.getBean(SLService.class);
		Pair<HashMap<Integer, Integer>, List<String>> result = s.mapLinesToStops();
		HashMap<Integer, Integer> top10Map = result.getValue0();
		List<String> stopNames = result.getValue1();
		for(Map.Entry<Integer, Integer> entry: top10Map.entrySet()){
			System.out.println("Bus line= "+entry.getKey()+"-> Bus stops="+entry.getValue());
		}
		System.out.println("List of stops for largest line: "+stopNames);
	}

}
