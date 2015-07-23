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
 * A Test9.
 */
@Entity
@Table(name = "TEST9")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Test9 implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 0, max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "age", nullable = false)
    private Integer age;

    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "time", nullable = false)
    private DateTime time;

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public DateTime getTime() {
        return time;
    }

    public void setTime(DateTime time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Test9 test9 = (Test9) o;

        if ( ! Objects.equals(id, test9.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Test9{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", age='" + age + "'" +
                ", date='" + date + "'" +
                ", time='" + time + "'" +
                '}';
    }
}
