package se.sbab.sbabchallenge;

import org.javatuples.Pair;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import se.sbab.sbabchallenge.service.SLService;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class SbabChallengeApplication {

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(SbabChallengeApplication.class, args);
		SLService s = context.getBean(SLService.class);
		Pair<HashMap<Integer, Integer>, List<String>> result = s.getSortedMapAndStopNames();
		HashMap<Integer, Integer> sortedMap = result.getValue0();
		List<String> stopNames = result.getValue1();
		int top10 = 10;
		System.out.println("Bus lines with the 10 highest stops are: ");
		Iterator<Map.Entry<Integer, Integer>> iterator = sortedMap.entrySet().iterator();
		while (iterator.hasNext()&&top10>0){
			Map.Entry<Integer, Integer> entry = iterator.next();
			System.out.println("Bus line= "+entry.getKey()+"-> Bus stops="+entry.getValue());
			top10--;
		}
		System.out.println("List of stops for largest line: "+stopNames);
	}

}
