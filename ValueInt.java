/*
 * Copyright 2004-2013 H2 Group. Multiple-Licensed under the H2 License,
 * Version 1.0, and under the Eclipse Public License, Version 1.0
 * (http://h2database.com/html/license.html).
 * Initial Developer: H2 Group
 */
package org.h2.value;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.h2.api.ErrorCode;
import org.h2.message.DbException;
import org.h2.util.MathUtils;

/**
 * Implementation of the INT data type.
 */
public class ValueInt extends Value {

	/**
	 * The precision in digits.
	 */
	public static final int PRECISION = 10;

	/**
	 * The maximum display size of an int. Example: -2147483648
	 */
	public static final int DISPLAY_SIZE = 11;

	private static final int STATIC_SIZE = 128;
	// must be a power of 2
	private static final int DYNAMIC_SIZE = 256;
	private static final ValueInt[] STATIC_CACHE = new ValueInt[STATIC_SIZE];
	private static final ValueInt[] DYNAMIC_CACHE = new ValueInt[DYNAMIC_SIZE];

	private final int value;

	static {
		for (int i = 0; i < STATIC_SIZE; i++) {
			STATIC_CACHE[i] = new ValueInt(i);
		}
	}

	private ValueInt(int value) {
		this.value = value;
	}

	/**
	 * Get or create an int value for the given int.
	 * 
	 * @param i
	 *            the int
	 * @return the value
	 */
	public static ValueInt get(int i) {
		if (i >= 0 && i < STATIC_SIZE) {
			return STATIC_CACHE[i];
		}
		ValueInt v = DYNAMIC_CACHE[i & (DYNAMIC_SIZE - 1)];
		if (v == null || v.value != i) {
			v = new ValueInt(i);
			DYNAMIC_CACHE[i & (DYNAMIC_SIZE - 1)] = v;
		}
		return v;
	}

	@Override
	public Value add(Value v) {
		ValueInt other = (ValueInt) v;
		return checkRange((long) value + (long) other.value);
	}

	/**
	 * This method takes the Value object as input. It performs the and with the
	 * value variable of the object(Value). It returns the and'ed value.
	 */
	public Value and(Value v) {
		ValueInt other = (ValueInt) v;
		int v2 = other.value;
		int v1 = value;
		String v1_string = convertToInvertedBinary(v1);
		String v2_string = convertToInvertedBinary(v2);

		int v1_length = v1_string.length();
		int v2_length = v2_string.length();

		int index = 0;
		int jindex = 0;

		int answer = 0;
		while (index < v1_length && jindex < v2_length) {
			int bit = bit_and(v1_string.charAt(index), v2_string.charAt(jindex));
			answer = ((int) Math.pow(2, index) * bit) + answer;
			index++;
			jindex++;
		}

		return ValueInt.get(answer);

	}

	/**
	 * This method takes the Value object as input. It performs the or with the
	 * value variable of the object(Value). It returns the or'ed value.
	 */
	public Value or(Value v) {
		ValueInt other = (ValueInt) v;
		int v2 = other.value;
		int v1 = value;
		String v1_string = convertToInvertedBinary(v1);
		String v2_string = convertToInvertedBinary(v2);

		int v1_length = v1_string.length();
		int v2_length = v2_string.length();

		int index = 0;
		int jindex = 0;

		int answer = 0;
		while (index < v1_length && jindex < v2_length) {
			int bit = bit_or(v1_string.charAt(index), v2_string.charAt(jindex));
			answer = ((int) Math.pow(2, index) * bit) + answer;
			index++;
			jindex++;
		}

		while (index < v1_length) {
			int bit = bit_or(v1_string.charAt(index), '0');
			answer = ((int) Math.pow(2, index) * bit) + answer;
			index++;
		}

		while (jindex < v2_length) {
			int bit = bit_or(v1_string.charAt(jindex), '0');
			answer = ((int) Math.pow(2, jindex) * bit) + answer;
			jindex++;
		}
		return ValueInt.get(answer);

	}

	/**
	 * This method takes the Value object as input. It performs the xor with the
	 * value variable of the object(Value). It returns the xored value.
	 */
	public Value xor(Value v) {
		ValueInt other = (ValueInt) v;
		int v2 = other.value;
		int v1 = value;
		String v1_string = convertToInvertedBinary(v1);
		String v2_string = convertToInvertedBinary(v2);

		int v1_length = v1_string.length();
		int v2_length = v2_string.length();

		int index = 0;
		int jindex = 0;

		int answer = 0;
		while (index < v1_length && jindex < v2_length) {
			int bit = bit_xor(v1_string.charAt(index), v2_string.charAt(jindex));
			answer = ((int) Math.pow(2, index) * bit) + answer;
			index++;
			jindex++;
		}

		while (index < v1_length) {
			int bit = bit_or(v1_string.charAt(index), '0');
			answer = ((int) Math.pow(2, index) * bit) + answer;
			index++;
		}

		while (jindex < v2_length) {
			int bit = bit_or(v1_string.charAt(jindex), '0');
			answer = ((int) Math.pow(2, jindex) * bit) + answer;
			jindex++;
		}
		return ValueInt.get(answer);

	}

	/**
	 * This method takes two characters as input and returns the and value of
	 * it.
	 * 
	 * @param i
	 *            input character
	 * @param j
	 *            input character
	 * @return and value(either 0 or 1)
	 */
	private static int bit_and(char i, char j) {
		if (i == '1' && j == '1') {
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * This method takes two characters as input and returns the or value of it.
	 * 
	 * @param i
	 *            input character
	 * @param j
	 *            input character
	 * @return or value(either 0 or 1)
	 */
	private static int bit_or(char i, char j) {
		if (i == '1' || j == '1') {
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * This method takes two characters as input and returns the xor value of
	 * it.
	 * 
	 * @param i
	 *            input character
	 * @param j
	 *            input character
	 * @return xor value(either 0 or 1)
	 */
	private static int bit_xor(char i, char j) {
		if (i == j) {
			return 0;
		} else {
			return 1;
		}
	}

	/**
	 * This method converts an integer to a binary number and returns it in an
	 * inverted format. (2)=(10) in Binary. This method will return 01.
	 * 
	 * @param x
	 *            integer value
	 * @return inverted binary in String format
	 */
	private static String convertToInvertedBinary(int x) {
		String output = "";
		int n = x;

		while (n != 0) {
			int mod = n % 2;
			output = output + mod;
			n = n / 2;
		}
		return output;
	}

	private static ValueInt checkRange(long x) {
		if (x < Integer.MIN_VALUE || x > Integer.MAX_VALUE) {
			throw DbException.get(ErrorCode.NUMERIC_VALUE_OUT_OF_RANGE_1,
					Long.toString(x));
		}
		return ValueInt.get((int) x);
	}

	@Override
	public int getSignum() {
		return Integer.signum(value);
	}

	@Override
	public Value negate() {
		return checkRange(-(long) value);
	}

	@Override
	public Value subtract(Value v) {
		ValueInt other = (ValueInt) v;
		return checkRange((long) value - (long) other.value);
	}

	@Override
	public Value multiply(Value v) {
		ValueInt other = (ValueInt) v;
		return checkRange((long) value * (long) other.value);
	}

	@Override
	public Value divide(Value v) {
		ValueInt other = (ValueInt) v;
		if (other.value == 0) {
			throw DbException.get(ErrorCode.DIVISION_BY_ZERO_1, getSQL());
		}
		return ValueInt.get(value / other.value);
	}

	@Override
	public Value modulus(Value v) {
		ValueInt other = (ValueInt) v;
		if (other.value == 0) {
			throw DbException.get(ErrorCode.DIVISION_BY_ZERO_1, getSQL());
		}
		return ValueInt.get(value % other.value);
	}

	@Override
	public String getSQL() {
		return getString();
	}

	@Override
	public int getType() {
		return Value.INT;
	}

	@Override
	public int getInt() {
		return value;
	}

	@Override
	public long getLong() {
		return value;
	}

	@Override
	protected int compareSecure(Value o, CompareMode mode) {
		ValueInt v = (ValueInt) o;
		return MathUtils.compareInt(value, v.value);
	}

	@Override
	public String getString() {
		return String.valueOf(value);
	}

	@Override
	public long getPrecision() {
		return PRECISION;
	}

	@Override
	public int hashCode() {
		return value;
	}

	@Override
	public Object getObject() {
		return value;
	}

	@Override
	public void set(PreparedStatement prep, int parameterIndex)
			throws SQLException {
		prep.setInt(parameterIndex, value);
	}

	@Override
	public int getDisplaySize() {
		return DISPLAY_SIZE;
	}

	@Override
	public boolean equals(Object other) {
		return other instanceof ValueInt && value == ((ValueInt) other).value;
	}

}
