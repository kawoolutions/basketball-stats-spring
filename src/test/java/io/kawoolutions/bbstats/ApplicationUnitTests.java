package io.kawoolutions.bbstats;

import io.kawoolutions.bbstats.util.NamingUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ApplicationUnitTests {

    @Nested
    class NamingUtilTests {

        @Test
        @DisplayName("Test English number strings 1st, 2nd, 3rd, 4th, 100th, 101st, 102nd, 103rd, 104th, ...")
        public void givenWholeNumber_whenConvertingToEnglishNumberString_thenVerifyCorrectness() {

            // given
            int zero = 0;
            int one = 1;
            int two = 2;
            int three = 3;
            int four = 4;
            int eleven = 11;
            int twelve = 12;
            int thirteen = 13;
            int fourteen = 14;

            int oneHundred = 100;
            int oneHundredOne = 101;
            int oneHundredTwo = 102;
            int oneHundredThree = 103;
            int oneHundredFour = 104;
            int oneHundredEleven = 111;
            int oneHundredTwelve = 112;
            int oneHundredThirteen = 113;
            int oneHundredFourteen = 114;

            // when
            String zeroth = NamingUtil.convertToEnglishOrdinalStringFor(zero);
            String first = NamingUtil.convertToEnglishOrdinalStringFor(one);
            String second = NamingUtil.convertToEnglishOrdinalStringFor(two);
            String third = NamingUtil.convertToEnglishOrdinalStringFor(three);
            String fourth = NamingUtil.convertToEnglishOrdinalStringFor(four);
            String eleventh = NamingUtil.convertToEnglishOrdinalStringFor(eleven);
            String twelfth = NamingUtil.convertToEnglishOrdinalStringFor(twelve);
            String thirteenth = NamingUtil.convertToEnglishOrdinalStringFor(thirteen);
            String fourteenth = NamingUtil.convertToEnglishOrdinalStringFor(fourteen);

            String oneHundredth = NamingUtil.convertToEnglishOrdinalStringFor(oneHundred);
            String oneHundredFirst = NamingUtil.convertToEnglishOrdinalStringFor(oneHundredOne);
            String oneHundredSecond = NamingUtil.convertToEnglishOrdinalStringFor(oneHundredTwo);
            String oneHundredThird = NamingUtil.convertToEnglishOrdinalStringFor(oneHundredThree);
            String oneHundredFourth = NamingUtil.convertToEnglishOrdinalStringFor(oneHundredFour);
            String oneHundredEleventh = NamingUtil.convertToEnglishOrdinalStringFor(oneHundredEleven);
            String oneHundredTwelfth = NamingUtil.convertToEnglishOrdinalStringFor(oneHundredTwelve);
            String oneHundredThirteenth = NamingUtil.convertToEnglishOrdinalStringFor(oneHundredThirteen);
            String oneHundredFourteenth = NamingUtil.convertToEnglishOrdinalStringFor(oneHundredFourteen);

            // then
            assertEquals("0th", zeroth);
            assertEquals("1st", first);
            assertEquals("2nd", second);
            assertEquals("3rd", third);
            assertEquals("4th", fourth);
            assertEquals("11th", eleventh);
            assertEquals("12th", twelfth);
            assertEquals("13th", thirteenth);
            assertEquals("14th", fourteenth);

            assertEquals("100th", oneHundredth);
            assertEquals("101st", oneHundredFirst);
            assertEquals("102nd", oneHundredSecond);
            assertEquals("103rd", oneHundredThird);
            assertEquals("104th", oneHundredFourth);
            assertEquals("111th", oneHundredEleventh);
            assertEquals("112th", oneHundredTwelfth);
            assertEquals("113th", oneHundredThirteenth);
            assertEquals("114th", oneHundredFourteenth);
        }

        @Test
        @DisplayName("Test English number strings for negative numbers")
        public void givenNegativeNumber_whenConvertingToEnglishNumberString_thenThrowException() {

            // given
            int minusOne = -1;

            // when
            Exception minusOneException = assertThrows(IllegalArgumentException.class, () -> NamingUtil.convertToEnglishOrdinalStringFor(minusOne));

            // then
            assertEquals(NamingUtil.NEGATIVE_NUMBER_MESSAGE, minusOneException.getMessage()); // https://stackoverflow.com/a/46514550/396732
        }
    }

    @Nested
    class GameUtilTests {

    }
}
