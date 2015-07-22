package com.mycompany.myapp.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mycompany.myapp.domain.util.CustomLocalDateSerializer;
import com.mycompany.myapp.domain.util.ISO8601LocalDateDeserializer;
import com.mycompany.myapp.domain.util.CustomDateTimeDeserializer;
import com.mycompany.myapp.domain.util.CustomDateTimeSerializer;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;


/**
 * A Test3.
 */
@Entity
@Table(name = "TEST3")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Test3 implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 1, max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    @Column(name = "one", length = 20, nullable = false)
    private String one;

    @Column(name = "two")
    private Integer two;

    @Column(name = "three", precision=10, scale=2)
    private BigDecimal three;

    @Column(name = "four")
    private Long four;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    @Column(name = "five")
    private LocalDate five;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "six")
    private DateTime six;

    @Column(name = "seven")
    private Boolean seven;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOne() {
        return one;
    }

    public void setOne(String one) {
        this.one = one;
    }

    public Integer getTwo() {
        return two;
    }

    public void setTwo(Integer two) {
        this.two = two;
    }

    public BigDecimal getThree() {
        return three;
    }

    public void setThree(BigDecimal three) {
        this.three = three;
    }

    public Long getFour() {
        return four;
    }

    public void setFour(Long four) {
        this.four = four;
    }

    public LocalDate getFive() {
        return five;
    }

    public void setFive(LocalDate five) {
        this.five = five;
    }

    public DateTime getSix() {
        return six;
    }

    public void setSix(DateTime six) {
        this.six = six;
    }

    public Boolean getSeven() {
        return seven;
    }

    public void setSeven(Boolean seven) {
        this.seven = seven;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Test3 test3 = (Test3) o;

        if ( ! Objects.equals(id, test3.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Test3{" +
                "id=" + id +
                ", one='" + one + "'" +
                ", two='" + two + "'" +
                ", three='" + three + "'" +
                ", four='" + four + "'" +
                ", five='" + five + "'" +
                ", six='" + six + "'" +
                ", seven='" + seven + "'" +
                '}';
    }
}
