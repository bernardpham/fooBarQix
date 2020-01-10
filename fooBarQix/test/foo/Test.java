package foo;

import org.junit.Assert;

public class Test {

	/**
	 * FooBarQix Kata

		Write a program that prints numbers from 1 to 100, one number per line. For each printed number, use the following rules:
		
		if the number is divisible by 3 or contains 3, replace 3 by "Foo";
		if the number is divisible by 5 or contains 5, replace 5 by "Bar";
		if the number contains 7, replace by "Qix";
		
		Example: 1 2 FooFoo 4 BarBar Foo Qix 8 Foo Bar
	 **/

	
	@org.junit.Test
	public void testDivisible() {
		
		Assert.assertEquals(true, Rules.isDivibleBy(3, Rules.THREE));
		Assert.assertEquals(true, Rules.isDivibleBy(33, Rules.THREE));
		Assert.assertEquals(false, Rules.isDivibleBy(5, Rules.THREE));
		Assert.assertEquals(false, Rules.isDivibleBy(0, Rules.THREE));
		
		Assert.assertEquals(true, Rules.isDivibleBy(5, Rules.FIVE));
		Assert.assertEquals(true, Rules.isDivibleBy(5555, Rules.FIVE));
		Assert.assertEquals(false, Rules.isDivibleBy(354, Rules.FIVE));
		Assert.assertEquals(false, Rules.isDivibleBy(0, Rules.FIVE));
	}
	
	@org.junit.Test
	public void testContains() {
		Assert.assertEquals(Rules.FOO, Rules.containsNumber(3));
		Assert.assertEquals(Rules.FOO + Rules.FOO, Rules.containsNumber(313));
		Assert.assertEquals(Rules.BAR, Rules.containsNumber(5));
		Assert.assertEquals("", Rules.containsNumber(0));
		Assert.assertEquals(Rules.BAR + Rules.BAR, Rules.containsNumber(515));
	}
	
	/**
		More details:
		* divisors have high precedence, ex: 51 -> FooBar
		* the content is analysed in the order they appear, ex: 53 -> BarFoo
		* 13 contains 3 so we print "Foo"
		* 15 is divisible by 3 and 5 and contains 5, so we print “FooBarBar”
		* 33 contains 3 two times and is divisible by 3, so we print “FooFooFoo”
		* 27 is divisible by 3 and contains 7, so we print "FooQix"
	**/	
	
	//divisors have high precedence, ex: 51 -> FooBar
	@org.junit.Test
	public void testHighPreference() {
		Assert.assertEquals("FooBar", Rules.replace(51));
	}
	
	//the content is analysed in the order they appear, ex: 53 -> BarFoo
	@org.junit.Test
	public void testOrderOfAppearance() {
		Assert.assertEquals("BarFoo", Rules.replace(53));
	}
	
	//13 contains 3 so we print "Foo"
	@org.junit.Test
	public void testContainsReplacement() {
		Assert.assertEquals("Foo", Rules.replace(13));
	}

	//15 is divisible by 3 and 5 and contains 5, so we print “FooBarBar”
	@org.junit.Test
	public void testDivisibleAndContainsReplacement() {
		Assert.assertEquals("FooBarBar", Rules.replace(15));
	}

	//33 contains 3 two times and is divisible by 3, so we print “FooFooFoo”
	@org.junit.Test
	public void testMultipleDivisibleAndContainsReplacement() {
		Assert.assertEquals("FooFooFoo", Rules.replace(33));
	}

	//27 is divisible by 3 and contains 7, so we print "FooQix"
	@org.junit.Test
	public void testMultipleDivisibleAndContainsSevenReplacement() {
		Assert.assertEquals("FooQix", Rules.replace(27));
	}
}
