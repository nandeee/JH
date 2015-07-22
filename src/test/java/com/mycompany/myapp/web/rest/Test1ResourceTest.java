package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Test1;
import com.mycompany.myapp.repository.Test1Repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.joda.time.LocalDate;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the Test1Resource REST controller.
 *
 * @see Test1Resource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Test1ResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private static final String DEFAULT_FIRSTNAME = "SAMPLE_TEXT";
    private static final String UPDATED_FIRSTNAME = "UPDATED_TEXT";

    private static final Integer DEFAULT_AGE = 0;
    private static final Integer UPDATED_AGE = 1;

    private static final LocalDate DEFAULT_DATE = new LocalDate(0L);
    private static final LocalDate UPDATED_DATE = new LocalDate();

    private static final DateTime DEFAULT_TIME = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_TIME = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_TIME_STR = dateTimeFormatter.print(DEFAULT_TIME);

    private static final Boolean DEFAULT_FLAG = false;
    private static final Boolean UPDATED_FLAG = true;

    @Inject
    private Test1Repository test1Repository;

    private MockMvc restTest1MockMvc;

    private Test1 test1;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Test1Resource test1Resource = new Test1Resource();
        ReflectionTestUtils.setField(test1Resource, "test1Repository", test1Repository);
        this.restTest1MockMvc = MockMvcBuilders.standaloneSetup(test1Resource).build();
    }

    @Before
    public void initTest() {
        test1 = new Test1();
        test1.setFirstname(DEFAULT_FIRSTNAME);
        test1.setAge(DEFAULT_AGE);
        test1.setDate(DEFAULT_DATE);
        test1.setTime(DEFAULT_TIME);
        test1.setFlag(DEFAULT_FLAG);
    }

    @Test
    @Transactional
    public void createTest1() throws Exception {
        int databaseSizeBeforeCreate = test1Repository.findAll().size();

        // Create the Test1
        restTest1MockMvc.perform(post("/api/test1s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(test1)))
                .andExpect(status().isCreated());

        // Validate the Test1 in the database
        List<Test1> test1s = test1Repository.findAll();
        assertThat(test1s).hasSize(databaseSizeBeforeCreate + 1);
        Test1 testTest1 = test1s.get(test1s.size() - 1);
        assertThat(testTest1.getFirstname()).isEqualTo(DEFAULT_FIRSTNAME);
        assertThat(testTest1.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testTest1.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testTest1.getTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_TIME);
        assertThat(testTest1.getFlag()).isEqualTo(DEFAULT_FLAG);
    }

    @Test
    @Transactional
    public void getAllTest1s() throws Exception {
        // Initialize the database
        test1Repository.saveAndFlush(test1);

        // Get all the test1s
        restTest1MockMvc.perform(get("/api/test1s"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(test1.getId().intValue())))
                .andExpect(jsonPath("$.[*].firstname").value(hasItem(DEFAULT_FIRSTNAME.toString())))
                .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
                .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME_STR)))
                .andExpect(jsonPath("$.[*].flag").value(hasItem(DEFAULT_FLAG.booleanValue())));
    }

    @Test
    @Transactional
    public void getTest1() throws Exception {
        // Initialize the database
        test1Repository.saveAndFlush(test1);

        // Get the test1
        restTest1MockMvc.perform(get("/api/test1s/{id}", test1.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(test1.getId().intValue()))
            .andExpect(jsonPath("$.firstname").value(DEFAULT_FIRSTNAME.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME_STR))
            .andExpect(jsonPath("$.flag").value(DEFAULT_FLAG.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTest1() throws Exception {
        // Get the test1
        restTest1MockMvc.perform(get("/api/test1s/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTest1() throws Exception {
        // Initialize the database
        test1Repository.saveAndFlush(test1);

		int databaseSizeBeforeUpdate = test1Repository.findAll().size();

        // Update the test1
        test1.setFirstname(UPDATED_FIRSTNAME);
        test1.setAge(UPDATED_AGE);
        test1.setDate(UPDATED_DATE);
        test1.setTime(UPDATED_TIME);
        test1.setFlag(UPDATED_FLAG);
        restTest1MockMvc.perform(put("/api/test1s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(test1)))
                .andExpect(status().isOk());

        // Validate the Test1 in the database
        List<Test1> test1s = test1Repository.findAll();
        assertThat(test1s).hasSize(databaseSizeBeforeUpdate);
        Test1 testTest1 = test1s.get(test1s.size() - 1);
        assertThat(testTest1.getFirstname()).isEqualTo(UPDATED_FIRSTNAME);
        assertThat(testTest1.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testTest1.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testTest1.getTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_TIME);
        assertThat(testTest1.getFlag()).isEqualTo(UPDATED_FLAG);
    }

    @Test
    @Transactional
    public void deleteTest1() throws Exception {
        // Initialize the database
        test1Repository.saveAndFlush(test1);

		int databaseSizeBeforeDelete = test1Repository.findAll().size();

        // Get the test1
        restTest1MockMvc.perform(delete("/api/test1s/{id}", test1.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Test1> test1s = test1Repository.findAll();
        assertThat(test1s).hasSize(databaseSizeBeforeDelete - 1);
    }
}
