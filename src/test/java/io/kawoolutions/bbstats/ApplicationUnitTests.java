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
    class GameUtilTests {

        @Test
        @DisplayName("Test English number strings 1st, 2nd, 3rd, 4th, 100th, 101st, 102nd, 103rd, 104th, ...")
        public void testEnglishNumberStrings() {

            // given
            int minusOne = -1;

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
            Exception minusOneException = assertThrows(IllegalArgumentException.class, () -> NamingUtil.englishOrdinalStringFor(minusOne));
            String zeroth = NamingUtil.englishOrdinalStringFor(zero);
            String first = NamingUtil.englishOrdinalStringFor(one);
            String second = NamingUtil.englishOrdinalStringFor(two);
            String third = NamingUtil.englishOrdinalStringFor(three);
            String fourth = NamingUtil.englishOrdinalStringFor(four);
            String eleventh = NamingUtil.englishOrdinalStringFor(eleven);
            String twelfth = NamingUtil.englishOrdinalStringFor(twelve);
            String thirteenth = NamingUtil.englishOrdinalStringFor(thirteen);
            String fourteenth = NamingUtil.englishOrdinalStringFor(fourteen);

            String oneHundredth = NamingUtil.englishOrdinalStringFor(oneHundred);
            String oneHundredFirst = NamingUtil.englishOrdinalStringFor(oneHundredOne);
            String oneHundredSecond = NamingUtil.englishOrdinalStringFor(oneHundredTwo);
            String oneHundredThird = NamingUtil.englishOrdinalStringFor(oneHundredThree);
            String oneHundredFourth = NamingUtil.englishOrdinalStringFor(oneHundredFour);
            String oneHundredEleventh = NamingUtil.englishOrdinalStringFor(oneHundredEleven);
            String oneHundredTwelfth = NamingUtil.englishOrdinalStringFor(oneHundredTwelve);
            String oneHundredThirteenth = NamingUtil.englishOrdinalStringFor(oneHundredThirteen);
            String oneHundredFourteenth = NamingUtil.englishOrdinalStringFor(oneHundredFourteen);

            // then
            assertEquals("Number is negative!", minusOneException.getMessage()); // https://stackoverflow.com/a/46514550/396732
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
    }

    @Nested
    class NamingUtilTests {

    }
}
