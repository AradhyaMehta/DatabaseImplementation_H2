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
 * Implementation of the SMALLINT data type.
 */
public class ValueShort extends Value {

    /**
     * The precision in digits.
     */
    static final int PRECISION = 5;

    /**
     * The maximum display size of a short.
     * Example: -32768
     */
    static final int DISPLAY_SIZE = 6;

    private final short value;

    private ValueShort(short value) {
        this.value = value;
    }

    @Override
    public Value add(Value v) {
        ValueShort other = (ValueShort) v;
        return checkRange(value + other.value);
    }

	/**
	 * This method takes the Value object as input. It performs the and with the
	 * value variable of the object(Value). It returns the and'ed value.
	 */
	public Value and(Value v) {
		ValueShort other = (ValueShort) v;
		short v2 = other.value;
		short v1 = value;
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
		ValueShort other = (ValueShort) v;
		short v2 = other.value;
		short v1 = value;
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
		ValueShort other = (ValueShort) v;
		short v2 = other.value;
		short v1 = value;
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


    private static ValueShort checkRange(int x) {
        if (x < Short.MIN_VALUE || x > Short.MAX_VALUE) {
            throw DbException.get(ErrorCode.NUMERIC_VALUE_OUT_OF_RANGE_1,
                    Integer.toString(x));
        }
        return ValueShort.get((short) x);
    }

    @Override
    public int getSignum() {
        return Integer.signum(value);
    }

    @Override
    public Value negate() {
        return checkRange(-(int) value);
    }

    @Override
    public Value subtract(Value v) {
        ValueShort other = (ValueShort) v;
        return checkRange(value - other.value);
    }

    @Override
    public Value multiply(Value v) {
        ValueShort other = (ValueShort) v;
        return checkRange(value * other.value);
    }

    @Override
    public Value divide(Value v) {
        ValueShort other = (ValueShort) v;
        if (other.value == 0) {
            throw DbException.get(ErrorCode.DIVISION_BY_ZERO_1, getSQL());
        }
        return ValueShort.get((short) (value / other.value));
    }

    @Override
    public Value modulus(Value v) {
        ValueShort other = (ValueShort) v;
        if (other.value == 0) {
            throw DbException.get(ErrorCode.DIVISION_BY_ZERO_1, getSQL());
        }
        return ValueShort.get((short) (value % other.value));
    }

    @Override
    public String getSQL() {
        return getString();
    }

    @Override
    public int getType() {
        return Value.SHORT;
    }

    @Override
    public short getShort() {
        return value;
    }

    @Override
    protected int compareSecure(Value o, CompareMode mode) {
        ValueShort v = (ValueShort) o;
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
        return Short.valueOf(value);
    }

    @Override
    public void set(PreparedStatement prep, int parameterIndex)
            throws SQLException {
        prep.setShort(parameterIndex, value);
    }

    /**
     * Get or create a short value for the given short.
     *
     * @param i the short
     * @return the value
     */
    public static ValueShort get(short i) {
        return (ValueShort) Value.cache(new ValueShort(i));
    }

    @Override
    public int getDisplaySize() {
        return DISPLAY_SIZE;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof ValueShort && value == ((ValueShort) other).value;
    }

}
