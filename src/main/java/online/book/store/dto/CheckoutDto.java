package online.book.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import online.book.store.builder.AbstractCheckoutBuilder;
import online.book.store.entity.Checkout;
import org.springframework.beans.factory.annotation.Value;
import org.thymeleaf.spring5.expression.Fields;

import javax.persistence.Column;
import java.lang.reflect.Field;


@AllArgsConstructor
@Getter
@Setter
public class CheckoutDto extends AbstractCheckoutBuilder {


    private String firstName;
    private String lastName;
    private String address;

    private String zip;
    private String number;
    private String exp;

    private String sessionid;
    

    //implementation of builder


    @Override
    public AbstractCheckoutBuilder builder() {
        return this;
    }

    @Override
    public AbstractCheckoutBuilder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    @Override
    public AbstractCheckoutBuilder lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Override
    public AbstractCheckoutBuilder address(String address) {
        this.address = address;
        return this;
    }

    @Override
    public AbstractCheckoutBuilder zip(String zip) {
        this.zip = zip;
        return this;
    }

    @Override
    public AbstractCheckoutBuilder number(String number) {
        this.number = number;
        return this;
    }

    @Override
    public AbstractCheckoutBuilder exp(String exp) {
        this.exp = exp;
        return this;
    }

    @Override
    public boolean containsNull() {
        Field[] fields = this.getClass().getFields();
        int index = 0;
        while(index != fields.length) {
            if (fields[index] == null) return true;
            index ++ ;
        }
    return false;
    }

    @Override
    public Checkout build() {
        if(!containsNull()){
            return new Checkout(this.firstName, this.lastName, this.address,
                    this.zip, this.number, this.exp);
        }
        return null;
    }

    public Checkout doCheckoutBuilder(){
        return builder().
                firstName(this.firstName).
                lastName(this.lastName).
                address(this.address).
                zip(this.zip).number(this.number).
                exp(this.exp).build();
    }
}
