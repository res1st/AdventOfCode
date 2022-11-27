package de.cas.adventofcode.java19;

public class Java18 {

	public static void main(String[] args) {
		newSwitchObject("OK");
		newSwitchObject("Christmas");
		newSwitchObject(4);
		newSwitchObject(3.666D);
		
		newSwitchString("");
		newSwitchString("aha");
		newSwitchString("a");
		newSwitchString("A");
		newSwitchString("Fred");
		newSwitchString(null);
		
	}

	private static void newSwitchObject(Object obj) {
		switch (obj) {
		  case String s when s.length() > 5 -> System.out.println("S5: " + s); // java 19
		  case String s                   -> System.out.println("S: " + s.toLowerCase());
		  case Integer i                  -> System.out.println("I: " + i * i);
		  default                         -> System.out.println("DEF: " + obj.toString());
		}
	}
	
	private static void newSwitchString(String s) {
		switch (s) {
		  case ""                         -> System.out.println("empty");
		  case "A", "aha"                 -> System.out.println("Aha!");
		  case null                       -> System.out.println("Oopsi");
		  default                         -> System.out.println("def: " + s);
		}
	}
}
