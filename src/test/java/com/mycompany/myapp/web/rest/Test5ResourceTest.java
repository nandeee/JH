package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Test5;
import com.mycompany.myapp.repository.Test5Repository;

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
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the Test5Resource REST controller.
 *
 * @see Test5Resource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Test5ResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";

    private static final Integer DEFAULT_AGE = 2;
    private static final Integer UPDATED_AGE = 3;

    private static final DateTime DEFAULT_DATE = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_DATE = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_DATE_STR = dateTimeFormatter.print(DEFAULT_DATE);

    @Inject
    private Test5Repository test5Repository;

    private MockMvc restTest5MockMvc;

    private Test5 test5;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Test5Resource test5Resource = new Test5Resource();
        ReflectionTestUtils.setField(test5Resource, "test5Repository", test5Repository);
        this.restTest5MockMvc = MockMvcBuilders.standaloneSetup(test5Resource).build();
    }

    @Before
    public void initTest() {
        test5 = new Test5();
        test5.setName(DEFAULT_NAME);
        test5.setAge(DEFAULT_AGE);
        test5.setDate(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createTest5() throws Exception {
        int databaseSizeBeforeCreate = test5Repository.findAll().size();

        // Create the Test5
        restTest5MockMvc.perform(post("/api/test5s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(test5)))
                .andExpect(status().isCreated());

        // Validate the Test5 in the database
        List<Test5> test5s = test5Repository.findAll();
        assertThat(test5s).hasSize(databaseSizeBeforeCreate + 1);
        Test5 testTest5 = test5s.get(test5s.size() - 1);
        assertThat(testTest5.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTest5.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testTest5.getDate().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = test5Repository.findAll().size();
        // set the field null
        test5.setName(null);

        // Create the Test5, which fails.
        restTest5MockMvc.perform(post("/api/test5s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(test5)))
                .andExpect(status().isBadRequest());

        List<Test5> test5s = test5Repository.findAll();
        assertThat(test5s).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAgeIsRequired() throws Exception {
        int databaseSizeBeforeTest = test5Repository.findAll().size();
        // set the field null
        test5.setAge(null);

        // Create the Test5, which fails.
        restTest5MockMvc.perform(post("/api/test5s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(test5)))
                .andExpect(status().isBadRequest());

        List<Test5> test5s = test5Repository.findAll();
        assertThat(test5s).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = test5Repository.findAll().size();
        // set the field null
        test5.setDate(null);

        // Create the Test5, which fails.
        restTest5MockMvc.perform(post("/api/test5s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(test5)))
                .andExpect(status().isBadRequest());

        List<Test5> test5s = test5Repository.findAll();
        assertThat(test5s).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTest5s() throws Exception {
        // Initialize the database
        test5Repository.saveAndFlush(test5);

        // Get all the test5s
        restTest5MockMvc.perform(get("/api/test5s"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(test5.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE_STR)));
    }

    @Test
    @Transactional
    public void getTest5() throws Exception {
        // Initialize the database
        test5Repository.saveAndFlush(test5);

        // Get the test5
        restTest5MockMvc.perform(get("/api/test5s/{id}", test5.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(test5.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingTest5() throws Exception {
        // Get the test5
        restTest5MockMvc.perform(get("/api/test5s/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTest5() throws Exception {
        // Initialize the database
        test5Repository.saveAndFlush(test5);

		int databaseSizeBeforeUpdate = test5Repository.findAll().size();

        // Update the test5
        test5.setName(UPDATED_NAME);
        test5.setAge(UPDATED_AGE);
        test5.setDate(UPDATED_DATE);
        restTest5MockMvc.perform(put("/api/test5s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(test5)))
                .andExpect(status().isOk());

        // Validate the Test5 in the database
        List<Test5> test5s = test5Repository.findAll();
        assertThat(test5s).hasSize(databaseSizeBeforeUpdate);
        Test5 testTest5 = test5s.get(test5s.size() - 1);
        assertThat(testTest5.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTest5.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testTest5.getDate().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void deleteTest5() throws Exception {
        // Initialize the database
        test5Repository.saveAndFlush(test5);

		int databaseSizeBeforeDelete = test5Repository.findAll().size();

        // Get the test5
        restTest5MockMvc.perform(delete("/api/test5s/{id}", test5.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Test5> test5s = test5Repository.findAll();
        assertThat(test5s).hasSize(databaseSizeBeforeDelete - 1);
    }
}
