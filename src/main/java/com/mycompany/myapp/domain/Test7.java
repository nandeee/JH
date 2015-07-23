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
import java.util.Objects;


/**
 * A Test7.
 */
@Entity
@Table(name = "TEST7")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Test7 implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 5, max = 20)
    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @NotNull
    @Min(value = 0)
    @Max(value = 10)
    @Column(name = "age", nullable = false)
    private Integer age;

    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "date", nullable = false)
    private DateTime date;

    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    @Column(name = "etad", nullable = false)
    private LocalDate etad;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public LocalDate getEtad() {
        return etad;
    }

    public void setEtad(LocalDate etad) {
        this.etad = etad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Test7 test7 = (Test7) o;

        if ( ! Objects.equals(id, test7.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Test7{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", age='" + age + "'" +
                ", date='" + date + "'" +
                ", etad='" + etad + "'" +
                '}';
    }
}
