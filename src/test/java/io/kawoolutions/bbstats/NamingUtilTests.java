package io.kawoolutions.bbstats;

import java.util.stream.Stream;

import io.kawoolutions.bbstats.entity.Club;
import io.kawoolutions.bbstats.entity.Season;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import io.kawoolutions.bbstats.util.NamingUtil;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NamingUtilTests {

    public static Stream<Arguments> provideWholeNumberPairsForVerification() {
        return Stream.of(
            Arguments.of(0, "0th"),
            Arguments.of(1, "1st"),
            Arguments.of(2, "2nd"),
            Arguments.of(3, "3rd"),
            Arguments.of(4, "4th"),
            Arguments.of(11, "11th"),
            Arguments.of(12, "12th"),
            Arguments.of(13, "13th"),
            Arguments.of(14, "14th"),

            Arguments.of(100, "100th"),
            Arguments.of(101, "101st"),
            Arguments.of(102, "102nd"),
            Arguments.of(103, "103rd"),
            Arguments.of(104, "104th"),
            Arguments.of(111, "111th"),
            Arguments.of(112, "112th"),
            Arguments.of(113, "113th"),
            Arguments.of(114, "114th")
        );
    }

    @ParameterizedTest
    @MethodSource("provideWholeNumberPairsForVerification")
    public void wholeNumberShouldReturnNumberString(int number, String expectedNumberString) {
        assertEquals(expectedNumberString, NamingUtil.convertToEnglishOrdinalStringFor(number));
    }

    @Test
    @DisplayName("Negative number should throw an exception")
    public void negativeNumberShouldThrowAnException() {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> NamingUtil.convertToEnglishOrdinalStringFor(-1))
            .withMessage("Number is negative!");
    }

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
        assertEquals("Number is negative!", minusOneException.getMessage()); // https://stackoverflow.com/a/46514550/396732
    }

    public static Stream<Arguments> provideStartYearsForVerification() {
        return Stream.of(
            Arguments.of(1999, "1999/00"),
            Arguments.of(2018, "2018/19"),
            Arguments.of(2100, "2100/01")
        );
    }

    @ParameterizedTest
    @MethodSource("provideStartYearsForVerification")
    public void startYearShouldReturnLabelString(int seasonStartYear, String expectedLabel) {
        assertEquals(expectedLabel, NamingUtil.getSeasonLabelForStartYear(seasonStartYear));
    }

    public static Stream<Arguments> provideSeasonsForVerification() {
        return Stream.of(
            Arguments.of(new Season(1999), "1999/00"),
            Arguments.of(new Season(2018), "2018/19"),
            Arguments.of(new Season(2100), "2100/01")
        );
    }

    @ParameterizedTest
    @MethodSource("provideSeasonsForVerification")
    public void seasonShouldReturnLabelString(Season season, String expectedLabel) {
        assertEquals(expectedLabel, NamingUtil.getSeasonLabelFor(season));
    }

    public static Stream<Arguments> provideClubsForVerification() {
        return Stream.of(
            Arguments.of(new Club("TV Dieburg", "DIEB"), "TV Dieburg (DIEB)"),
            Arguments.of(new Club("TV Babenhausen", "BABH"), "TV Babenhausen (BABH)"),
            Arguments.of(new Club("SCC Pfungstadt", "SCCP"), "SCC Pfungstadt (SCCP)")
        );
    }

    @ParameterizedTest
    @MethodSource("provideClubsForVerification")
    public void startYearShouldReturnLabelString(Club club, String expectedLabel) {
        assertEquals(expectedLabel, NamingUtil.getClubLabelFor(club));
    }
}
