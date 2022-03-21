package pl.dskow.credit;

import java.math.BigDecimal;

public class CreditCard {

    private BigDecimal creditLimit;

    public void assignLimit(BigDecimal newCreditLimit){
        if(creditLimit != null){
            throw new CantAsssingLimitTwiceExeption();
        };
        this.creditLimit = newCreditLimit;
    }

    public BigDecimal getBalance() {
        return creditLimit;
    }
}
