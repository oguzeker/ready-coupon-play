package com.bilyoner.assignment.balanceapi.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.text.MessageFormat;

@Slf4j
@UtilityClass
public final class BigDecimalUtil {

    private static final int BIGDECIMAL_COMPARE_FIRST_VALUE_IS_GREATER = 1;
    private static final int BIGDECIMAL_COMPARE_VALUES_ARE_EQUAL = 0;
    private static final int BIGDECIMAL_COMPARE_SECOND_VALUE_IS_GREATER = -1;

    public enum BDComparisonResult { FIRST_VALUE_GREATER, SECOND_VALUE_GREATER, VALUES_EQUAL }

    public static BDComparisonResult compare(BigDecimal firstValue, BigDecimal secondValue) {
        int comparisonResult = firstValue.compareTo(secondValue);
        if (comparisonResult == BIGDECIMAL_COMPARE_VALUES_ARE_EQUAL) {
            return BDComparisonResult.VALUES_EQUAL;
        } else if (comparisonResult == BIGDECIMAL_COMPARE_FIRST_VALUE_IS_GREATER) {
            return BDComparisonResult.FIRST_VALUE_GREATER;
        } else if (comparisonResult == BIGDECIMAL_COMPARE_SECOND_VALUE_IS_GREATER) {
            return BDComparisonResult.SECOND_VALUE_GREATER;
        }
        throw new IllegalArgumentException(MessageFormat.format("Could not compare BigDecimal values {0} and {1}",
                firstValue, secondValue));
    }

}
