package pl.dskow.credit;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;

public class CreditCardTest {
    @Test
    void itAllowsToAssignCreditLimit() {
        //A Arrange / Given
        CreditCard creditCard = new CreditCard();
        //A Act / When
        creditCard.assignLimit(BigDecimal.valueOf(1000));
        //A Assert / Then / Expected
        assertEquals(BigDecimal.valueOf(1000),creditCard.getBalance());
    }

    @Test
    void itAllowsToAssignDifferentCreditLimit() {
        //A Arrange / Given
        CreditCard creditCard = new CreditCard();
        //A Act / When
        creditCard.assignLimit(BigDecimal.valueOf(2000));
        //A Assert / Then / Expected
        assertEquals(BigDecimal.valueOf(2000),creditCard.getBalance());
    }

    @Test
    void itDenyToAssignCreditLimitTwiceV1() {
        CreditCard creditCard = new CreditCard();
        creditCard.assignLimit(BigDecimal.valueOf(2000));
        try{
            creditCard.assignLimit(BigDecimal.valueOf(2000));
            fail("It should throw excepction");
        }catch(CantAsssingLimitTwiceExeption e){
            assertTrue(true);
        };
    }

    @Test
    void itDenyToAssignCreditLimitTwiceV2() {
        CreditCard creditCard = new CreditCard();
        creditCard.assignLimit(BigDecimal.valueOf(2000));
        assertThrows(CantAsssingLimitTwiceExeption.class, () -> {creditCard.assignLimit(BigDecimal.valueOf(2000));});
    }
}
