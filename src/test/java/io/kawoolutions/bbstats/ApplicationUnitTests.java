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
            int oneHundred = 100;
            int oneHundredOne = 101;
            int oneHundredTwo = 102;
            int oneHundredThree = 103;
            int oneHundredFour = 104;

            // when
            String zeroth = NamingUtil.englishOrdinalStringFor(zero);
            String first = NamingUtil.englishOrdinalStringFor(one);
            String second = NamingUtil.englishOrdinalStringFor(two);
            String third = NamingUtil.englishOrdinalStringFor(three);
            String fourth = NamingUtil.englishOrdinalStringFor(four);
            String oneHundredth = NamingUtil.englishOrdinalStringFor(oneHundred);
            String oneHundredFirst = NamingUtil.englishOrdinalStringFor(oneHundredOne);
            String oneHundredSecond = NamingUtil.englishOrdinalStringFor(oneHundredTwo);
            String oneHundredThird = NamingUtil.englishOrdinalStringFor(oneHundredThree);
            String oneHundredFourth = NamingUtil.englishOrdinalStringFor(oneHundredFour);

            // then
//            assertThrows()
            assertEquals("0th", zeroth);
            assertEquals("1st", first);
            assertEquals("2nd", second);
            assertEquals("3rd", third);
            assertEquals("4th", fourth);
            assertEquals("100th", oneHundredth);
            assertEquals("101st", oneHundredFirst);
            assertEquals("102nd", oneHundredSecond);
            assertEquals("103rd", oneHundredThird);
            assertEquals("104th", oneHundredFourth);
        }
    }

    @Nested
    class NamingUtilTests {

    }
}
