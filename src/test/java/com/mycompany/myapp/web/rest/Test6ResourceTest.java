package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Test6;
import com.mycompany.myapp.repository.Test6Repository;

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
 * Test class for the Test6Resource REST controller.
 *
 * @see Test6Resource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Test6ResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";

    private static final Integer DEFAULT_AGE = 5;
    private static final Integer UPDATED_AGE = 6;

    private static final DateTime DEFAULT_DATE = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_DATE = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_DATE_STR = dateTimeFormatter.print(DEFAULT_DATE);

    @Inject
    private Test6Repository test6Repository;

    private MockMvc restTest6MockMvc;

    private Test6 test6;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Test6Resource test6Resource = new Test6Resource();
        ReflectionTestUtils.setField(test6Resource, "test6Repository", test6Repository);
        this.restTest6MockMvc = MockMvcBuilders.standaloneSetup(test6Resource).build();
    }

    @Before
    public void initTest() {
        test6 = new Test6();
        test6.setName(DEFAULT_NAME);
        test6.setAge(DEFAULT_AGE);
        test6.setDate(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createTest6() throws Exception {
        int databaseSizeBeforeCreate = test6Repository.findAll().size();

        // Create the Test6
        restTest6MockMvc.perform(post("/api/test6s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(test6)))
                .andExpect(status().isCreated());

        // Validate the Test6 in the database
        List<Test6> test6s = test6Repository.findAll();
        assertThat(test6s).hasSize(databaseSizeBeforeCreate + 1);
        Test6 testTest6 = test6s.get(test6s.size() - 1);
        assertThat(testTest6.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTest6.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testTest6.getDate().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = test6Repository.findAll().size();
        // set the field null
        test6.setName(null);

        // Create the Test6, which fails.
        restTest6MockMvc.perform(post("/api/test6s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(test6)))
                .andExpect(status().isBadRequest());

        List<Test6> test6s = test6Repository.findAll();
        assertThat(test6s).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAgeIsRequired() throws Exception {
        int databaseSizeBeforeTest = test6Repository.findAll().size();
        // set the field null
        test6.setAge(null);

        // Create the Test6, which fails.
        restTest6MockMvc.perform(post("/api/test6s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(test6)))
                .andExpect(status().isBadRequest());

        List<Test6> test6s = test6Repository.findAll();
        assertThat(test6s).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = test6Repository.findAll().size();
        // set the field null
        test6.setDate(null);

        // Create the Test6, which fails.
        restTest6MockMvc.perform(post("/api/test6s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(test6)))
                .andExpect(status().isBadRequest());

        List<Test6> test6s = test6Repository.findAll();
        assertThat(test6s).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTest6s() throws Exception {
        // Initialize the database
        test6Repository.saveAndFlush(test6);

        // Get all the test6s
        restTest6MockMvc.perform(get("/api/test6s"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(test6.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE_STR)));
    }

    @Test
    @Transactional
    public void getTest6() throws Exception {
        // Initialize the database
        test6Repository.saveAndFlush(test6);

        // Get the test6
        restTest6MockMvc.perform(get("/api/test6s/{id}", test6.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(test6.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingTest6() throws Exception {
        // Get the test6
        restTest6MockMvc.perform(get("/api/test6s/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTest6() throws Exception {
        // Initialize the database
        test6Repository.saveAndFlush(test6);

		int databaseSizeBeforeUpdate = test6Repository.findAll().size();

        // Update the test6
        test6.setName(UPDATED_NAME);
        test6.setAge(UPDATED_AGE);
        test6.setDate(UPDATED_DATE);
        restTest6MockMvc.perform(put("/api/test6s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(test6)))
                .andExpect(status().isOk());

        // Validate the Test6 in the database
        List<Test6> test6s = test6Repository.findAll();
        assertThat(test6s).hasSize(databaseSizeBeforeUpdate);
        Test6 testTest6 = test6s.get(test6s.size() - 1);
        assertThat(testTest6.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTest6.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testTest6.getDate().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void deleteTest6() throws Exception {
        // Initialize the database
        test6Repository.saveAndFlush(test6);

		int databaseSizeBeforeDelete = test6Repository.findAll().size();

        // Get the test6
        restTest6MockMvc.perform(delete("/api/test6s/{id}", test6.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Test6> test6s = test6Repository.findAll();
        assertThat(test6s).hasSize(databaseSizeBeforeDelete - 1);
    }
}
