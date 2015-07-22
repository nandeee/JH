package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A Test4.
 */
@Entity
@Table(name = "TEST4")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Test4 implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 0, max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    @Column(name = "one", length = 20, nullable = false)
    private String one;

    @Column(name = "two")
    private Integer two;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "three", nullable = false)
    private Long three;

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

    public Long getThree() {
        return three;
    }

    public void setThree(Long three) {
        this.three = three;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Test4 test4 = (Test4) o;

        if ( ! Objects.equals(id, test4.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Test4{" +
                "id=" + id +
                ", one='" + one + "'" +
                ", two='" + two + "'" +
                ", three='" + three + "'" +
                '}';
    }
}
