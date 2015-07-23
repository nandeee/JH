package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Test8;
import com.mycompany.myapp.repository.Test8Repository;

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
 * Test class for the Test8Resource REST controller.
 *
 * @see Test8Resource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Test8ResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";

    private static final Integer DEFAULT_AGE = 2;
    private static final Integer UPDATED_AGE = 3;

    private static final LocalDate DEFAULT_DATE = new LocalDate(0L);
    private static final LocalDate UPDATED_DATE = new LocalDate();

    private static final DateTime DEFAULT_TIME = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_TIME = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_TIME_STR = dateTimeFormatter.print(DEFAULT_TIME);

    @Inject
    private Test8Repository test8Repository;

    private MockMvc restTest8MockMvc;

    private Test8 test8;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Test8Resource test8Resource = new Test8Resource();
        ReflectionTestUtils.setField(test8Resource, "test8Repository", test8Repository);
        this.restTest8MockMvc = MockMvcBuilders.standaloneSetup(test8Resource).build();
    }

    @Before
    public void initTest() {
        test8 = new Test8();
        test8.setName(DEFAULT_NAME);
        test8.setAge(DEFAULT_AGE);
        test8.setDate(DEFAULT_DATE);
        test8.setTime(DEFAULT_TIME);
    }

    @Test
    @Transactional
    public void createTest8() throws Exception {
        int databaseSizeBeforeCreate = test8Repository.findAll().size();

        // Create the Test8
        restTest8MockMvc.perform(post("/api/test8s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(test8)))
                .andExpect(status().isCreated());

        // Validate the Test8 in the database
        List<Test8> test8s = test8Repository.findAll();
        assertThat(test8s).hasSize(databaseSizeBeforeCreate + 1);
        Test8 testTest8 = test8s.get(test8s.size() - 1);
        assertThat(testTest8.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTest8.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testTest8.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testTest8.getTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_TIME);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = test8Repository.findAll().size();
        // set the field null
        test8.setName(null);

        // Create the Test8, which fails.
        restTest8MockMvc.perform(post("/api/test8s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(test8)))
                .andExpect(status().isBadRequest());

        List<Test8> test8s = test8Repository.findAll();
        assertThat(test8s).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAgeIsRequired() throws Exception {
        int databaseSizeBeforeTest = test8Repository.findAll().size();
        // set the field null
        test8.setAge(null);

        // Create the Test8, which fails.
        restTest8MockMvc.perform(post("/api/test8s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(test8)))
                .andExpect(status().isBadRequest());

        List<Test8> test8s = test8Repository.findAll();
        assertThat(test8s).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = test8Repository.findAll().size();
        // set the field null
        test8.setDate(null);

        // Create the Test8, which fails.
        restTest8MockMvc.perform(post("/api/test8s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(test8)))
                .andExpect(status().isBadRequest());

        List<Test8> test8s = test8Repository.findAll();
        assertThat(test8s).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = test8Repository.findAll().size();
        // set the field null
        test8.setTime(null);

        // Create the Test8, which fails.
        restTest8MockMvc.perform(post("/api/test8s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(test8)))
                .andExpect(status().isBadRequest());

        List<Test8> test8s = test8Repository.findAll();
        assertThat(test8s).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTest8s() throws Exception {
        // Initialize the database
        test8Repository.saveAndFlush(test8);

        // Get all the test8s
        restTest8MockMvc.perform(get("/api/test8s"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(test8.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
                .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME_STR)));
    }

    @Test
    @Transactional
    public void getTest8() throws Exception {
        // Initialize the database
        test8Repository.saveAndFlush(test8);

        // Get the test8
        restTest8MockMvc.perform(get("/api/test8s/{id}", test8.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(test8.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME_STR));
    }

    @Test
    @Transactional
    public void getNonExistingTest8() throws Exception {
        // Get the test8
        restTest8MockMvc.perform(get("/api/test8s/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTest8() throws Exception {
        // Initialize the database
        test8Repository.saveAndFlush(test8);

		int databaseSizeBeforeUpdate = test8Repository.findAll().size();

        // Update the test8
        test8.setName(UPDATED_NAME);
        test8.setAge(UPDATED_AGE);
        test8.setDate(UPDATED_DATE);
        test8.setTime(UPDATED_TIME);
        restTest8MockMvc.perform(put("/api/test8s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(test8)))
                .andExpect(status().isOk());

        // Validate the Test8 in the database
        List<Test8> test8s = test8Repository.findAll();
        assertThat(test8s).hasSize(databaseSizeBeforeUpdate);
        Test8 testTest8 = test8s.get(test8s.size() - 1);
        assertThat(testTest8.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTest8.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testTest8.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testTest8.getTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_TIME);
    }

    @Test
    @Transactional
    public void deleteTest8() throws Exception {
        // Initialize the database
        test8Repository.saveAndFlush(test8);

		int databaseSizeBeforeDelete = test8Repository.findAll().size();

        // Get the test8
        restTest8MockMvc.perform(delete("/api/test8s/{id}", test8.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Test8> test8s = test8Repository.findAll();
        assertThat(test8s).hasSize(databaseSizeBeforeDelete - 1);
    }
}
