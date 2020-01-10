package foo;

public class Rules {

	public static final int THREE = 3;
	public static final char THREE_CHAR = '3';
	public static final int FIVE = 5;
	public static final char FIVE_CHAR = '5';
	public static final char SEVEN_CHAR = '7';
	
	public static final String FOO = "Foo";
	public static final String BAR = "Bar";
	public static final String QIX = "Qix";
	
	private Rules() {}
	
	public static boolean isDivibleBy(int numberToTest, int numerator) {
		return numberToTest!=0 && numberToTest%numerator == 0;
	}
	
	public static String isDivibleBy(int numberToTest) {
		StringBuilder s = new StringBuilder();
		
		if(isDivibleBy(numberToTest, THREE)) {
			s.append(FOO);
		}
		
		if(isDivibleBy(numberToTest, FIVE)) {
			s.append(BAR);
		}
		
		return String.valueOf(s);
	}
	
	public static String containsNumber(int numberToTest) {
		
		String numberToTestToString = String.valueOf(numberToTest);
		StringBuilder s = new StringBuilder();
		
		for(int i = 0; i < numberToTestToString.length(); i++) {
			
			switch (numberToTestToString.charAt(i)) {
			case THREE_CHAR:
				s.append(FOO);
				break;
			case FIVE_CHAR:
				s.append(BAR);
				break;
			case SEVEN_CHAR:
				s.append(QIX);
				break;
			default:
				break;
			}
		}
		return String.valueOf(s);
	}
	
	public static String replace(int numberToTest) {
		
		StringBuilder replacementValue = new StringBuilder();
		
		String divisibleValue = isDivibleBy(numberToTest);

		if(!divisibleValue.isEmpty()) {
			replacementValue.append(divisibleValue);
		}
		
		String containsValue = containsNumber(numberToTest);
		
		if(!containsValue.isEmpty()) {
			replacementValue.append(containsValue);
		} 
		
		if(divisibleValue.isEmpty() && containsValue.isEmpty()) {
			replacementValue.append(numberToTest);
		}
		
		return String.valueOf(replacementValue);
	}
}
