package foo;

public class Rules {

	/**
	 * FooBarQix Kata

Write a program that prints numbers from 1 to 100, one number per line. For each printed number, use the following rules:

if the number is divisible by 3 or contains 3, replace 3 by "Foo";
if the number is divisible by 5 or contains 5, replace 5 by "Bar";
if the number contains 7, replace by "Qix";

Example: 1 2 FooFoo 4 BarBar Foo Qix 8 Foo Bar

More details:

* divisors have high precedence, ex: 51 -> FooBar
* the content is analysed in the order they appear, ex: 53 -> BarFoo
* 13 contains 3 so we print "Foo"
* 15 is divisible by 3 and 5 and contains 5, so we print “FooBarBar”
* 33 contains 3 two times and is divisible by 3, so we print “FooFooFoo”
* 27 is divisible by 3 and contains 7, so we print "FooQix"
**/	
	
	public static final int THREE = 3;
	public static final int FIVE = 5;
	public static final int SEVEN = 7;
	
	private Rules() {}
	
	public static boolean isDivibleBy(int numberToTest, int numerator) {
		return numberToTest!=0 && numberToTest%numerator == 0;
	}
	
	public static boolean containsNumber(int numberToTest, int numberToFind) {
		String numberToTestToString = String.valueOf(numberToTest);
		return numberToTestToString.contains(String.valueOf(numberToFind));
	}

	public static String replace(int numberToTest) {
		// TODO Auto-generated method stub
		return null;
	}
}
