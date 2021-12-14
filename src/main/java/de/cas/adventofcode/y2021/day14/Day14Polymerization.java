package de.cas.adventofcode.y2021.day14;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import de.cas.adventofcode.util.AdventUtil;

public class Day14Polymerization {
	private static final String FILENAME = "day14_testinput.txt";

	
	public static void main(String[] args) throws IOException {
		Day14Polymerization day14 = new Day14Polymerization();
		List<String> input = AdventUtil.readFileByLineAsString("2021", FILENAME);
		List<List<String>> inputLists = AdventUtil.splitAtEmptyLines(input);
		String template = inputLists.get(0).get(0);
		System.out.println("Template: " + template);
		
		List<String> rulesList = inputLists.get(1);
		Map<String, String> rules = rulesList.stream().map(str -> str.split(" -> ")).collect(Collectors.toMap(array -> array[0], array -> array[1]));
		System.out.println("Rules: " + rules);
		
		// part 1: 10 rounds
		// part 2: 40 rounds
		day14.count(template, rules);
	}


	private void count(String template, Map<String, String> rules) {
		Map<String, Long> countedTokens = new HashMap<>();
		
		String newTemplate = template;

		for(int round=0; round<40; round++) {
			System.out.println("Starting round " + round);
			StringBuilder templateBuilder = new StringBuilder(Math.min(Integer.MAX_VALUE, newTemplate.length()*2));
			
			for (int i=0; i<newTemplate.length()-1; i++) {
				String newToken = newTemplate.substring(i, i+2);
//				System.out.println("new token: " + newToken);
				
				 char[] chars = newToken.toCharArray();
				 String firstChar = String.valueOf(chars[0]);
				 
				 String transformedToken = rules.get(newToken);
//				 System.out.println("transformedToken: " +  transformedToken);

				 templateBuilder.append(firstChar);
				 templateBuilder.append(transformedToken);
			}
			appendLastChar(newTemplate, templateBuilder);
			
			newTemplate = templateBuilder.toString();
//			System.out.println("newTemplate round " + round + ": " +  newTemplate);
		}
		
		newTemplate.codePoints().mapToObj(c -> String.valueOf((char) c)).forEach(s -> countedTokens.merge(s, 1L, (prev, one) -> prev+one));
		
		System.out.println("countedTokens: " +  countedTokens);

		long result = countedTokens.values().stream().mapToLong(e -> e).max().getAsLong() - countedTokens.values().stream().mapToLong(e -> e).min().getAsLong();
		System.out.println("Result: " + result);
	}


	private void appendLastChar(String newTemplate, StringBuilder templateBuilder) {
		char lastChar = newTemplate.charAt(newTemplate.length()-1);
		templateBuilder.append(lastChar);
	}
}
