package com.hyunryungkim.whatgoeswhere.models;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ParameterizedTestUsingFields {
	
	@ParameterizedTest
	@MethodSource("provideStringsForIsBlank")
	void isBlank_ShouldReturnTrueForNullOrBlankStrings(String input, boolean expected) {
	    assertEquals(expected, Strings.isBlank(input));
	}
	
	private static Stream<Arguments> provideStringsForIsBlank() {
	    return Stream.of(
	      Arguments.of(null, true),
	      Arguments.of("", true),
	      Arguments.of("  ", true),
	      Arguments.of("not blank", false)
	    );
	}
	
	
	@ParameterizedTest
	@MethodSource // hmm, no method name ...
	void isBlank_ShouldReturnTrueForNullOrBlankStringsOneArgument(String input) {
	    assertTrue(Strings.isBlank(input));
	}

	private static Stream<String> isBlank_ShouldReturnTrueForNullOrBlankStringsOneArgument() {
	    return Stream.of(null, "", "  ");
	}
}
class Strings {
    public static boolean isBlank(String input) {
        return input == null || input.trim().isEmpty();
    }
}
