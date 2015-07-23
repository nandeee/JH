package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Test9;
import com.mycompany.myapp.repository.Test9Repository;

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
 * Test class for the Test9Resource REST controller.
 *
 * @see Test9Resource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Test9ResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";

    private static final Integer DEFAULT_AGE = 0;
    private static final Integer UPDATED_AGE = 1;

    private static final LocalDate DEFAULT_DATE = new LocalDate(0L);
    private static final LocalDate UPDATED_DATE = new LocalDate();

    private static final DateTime DEFAULT_TIME = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_TIME = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_TIME_STR = dateTimeFormatter.print(DEFAULT_TIME);

    @Inject
    private Test9Repository test9Repository;

    private MockMvc restTest9MockMvc;

    private Test9 test9;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Test9Resource test9Resource = new Test9Resource();
        ReflectionTestUtils.setField(test9Resource, "test9Repository", test9Repository);
        this.restTest9MockMvc = MockMvcBuilders.standaloneSetup(test9Resource).build();
    }

    @Before
    public void initTest() {
        test9 = new Test9();
        test9.setName(DEFAULT_NAME);
        test9.setAge(DEFAULT_AGE);
        test9.setDate(DEFAULT_DATE);
        test9.setTime(DEFAULT_TIME);
    }

    @Test
    @Transactional
    public void createTest9() throws Exception {
        int databaseSizeBeforeCreate = test9Repository.findAll().size();

        // Create the Test9
        restTest9MockMvc.perform(post("/api/test9s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(test9)))
                .andExpect(status().isCreated());

        // Validate the Test9 in the database
        List<Test9> test9s = test9Repository.findAll();
        assertThat(test9s).hasSize(databaseSizeBeforeCreate + 1);
        Test9 testTest9 = test9s.get(test9s.size() - 1);
        assertThat(testTest9.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTest9.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testTest9.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testTest9.getTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_TIME);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = test9Repository.findAll().size();
        // set the field null
        test9.setName(null);

        // Create the Test9, which fails.
        restTest9MockMvc.perform(post("/api/test9s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(test9)))
                .andExpect(status().isBadRequest());

        List<Test9> test9s = test9Repository.findAll();
        assertThat(test9s).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAgeIsRequired() throws Exception {
        int databaseSizeBeforeTest = test9Repository.findAll().size();
        // set the field null
        test9.setAge(null);

        // Create the Test9, which fails.
        restTest9MockMvc.perform(post("/api/test9s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(test9)))
                .andExpect(status().isBadRequest());

        List<Test9> test9s = test9Repository.findAll();
        assertThat(test9s).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = test9Repository.findAll().size();
        // set the field null
        test9.setDate(null);

        // Create the Test9, which fails.
        restTest9MockMvc.perform(post("/api/test9s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(test9)))
                .andExpect(status().isBadRequest());

        List<Test9> test9s = test9Repository.findAll();
        assertThat(test9s).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = test9Repository.findAll().size();
        // set the field null
        test9.setTime(null);

        // Create the Test9, which fails.
        restTest9MockMvc.perform(post("/api/test9s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(test9)))
                .andExpect(status().isBadRequest());

        List<Test9> test9s = test9Repository.findAll();
        assertThat(test9s).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTest9s() throws Exception {
        // Initialize the database
        test9Repository.saveAndFlush(test9);

        // Get all the test9s
        restTest9MockMvc.perform(get("/api/test9s"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(test9.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
                .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME_STR)));
    }

    @Test
    @Transactional
    public void getTest9() throws Exception {
        // Initialize the database
        test9Repository.saveAndFlush(test9);

        // Get the test9
        restTest9MockMvc.perform(get("/api/test9s/{id}", test9.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(test9.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME_STR));
    }

    @Test
    @Transactional
    public void getNonExistingTest9() throws Exception {
        // Get the test9
        restTest9MockMvc.perform(get("/api/test9s/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTest9() throws Exception {
        // Initialize the database
        test9Repository.saveAndFlush(test9);

		int databaseSizeBeforeUpdate = test9Repository.findAll().size();

        // Update the test9
        test9.setName(UPDATED_NAME);
        test9.setAge(UPDATED_AGE);
        test9.setDate(UPDATED_DATE);
        test9.setTime(UPDATED_TIME);
        restTest9MockMvc.perform(put("/api/test9s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(test9)))
                .andExpect(status().isOk());

        // Validate the Test9 in the database
        List<Test9> test9s = test9Repository.findAll();
        assertThat(test9s).hasSize(databaseSizeBeforeUpdate);
        Test9 testTest9 = test9s.get(test9s.size() - 1);
        assertThat(testTest9.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTest9.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testTest9.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testTest9.getTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_TIME);
    }

    @Test
    @Transactional
    public void deleteTest9() throws Exception {
        // Initialize the database
        test9Repository.saveAndFlush(test9);

		int databaseSizeBeforeDelete = test9Repository.findAll().size();

        // Get the test9
        restTest9MockMvc.perform(delete("/api/test9s/{id}", test9.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Test9> test9s = test9Repository.findAll();
        assertThat(test9s).hasSize(databaseSizeBeforeDelete - 1);
    }
}
