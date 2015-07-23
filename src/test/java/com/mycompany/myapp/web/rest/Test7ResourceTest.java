package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Test7;
import com.mycompany.myapp.repository.Test7Repository;

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
 * Test class for the Test7Resource REST controller.
 *
 * @see Test7Resource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Test7ResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";

    private static final Integer DEFAULT_AGE = 0;
    private static final Integer UPDATED_AGE = 1;

    private static final DateTime DEFAULT_DATE = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_DATE = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_DATE_STR = dateTimeFormatter.print(DEFAULT_DATE);

    private static final LocalDate DEFAULT_ETAD = new LocalDate(0L);
    private static final LocalDate UPDATED_ETAD = new LocalDate();

    @Inject
    private Test7Repository test7Repository;

    private MockMvc restTest7MockMvc;

    private Test7 test7;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Test7Resource test7Resource = new Test7Resource();
        ReflectionTestUtils.setField(test7Resource, "test7Repository", test7Repository);
        this.restTest7MockMvc = MockMvcBuilders.standaloneSetup(test7Resource).build();
    }

    @Before
    public void initTest() {
        test7 = new Test7();
        test7.setName(DEFAULT_NAME);
        test7.setAge(DEFAULT_AGE);
        test7.setDate(DEFAULT_DATE);
        test7.setEtad(DEFAULT_ETAD);
    }

    @Test
    @Transactional
    public void createTest7() throws Exception {
        int databaseSizeBeforeCreate = test7Repository.findAll().size();

        // Create the Test7
        restTest7MockMvc.perform(post("/api/test7s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(test7)))
                .andExpect(status().isCreated());

        // Validate the Test7 in the database
        List<Test7> test7s = test7Repository.findAll();
        assertThat(test7s).hasSize(databaseSizeBeforeCreate + 1);
        Test7 testTest7 = test7s.get(test7s.size() - 1);
        assertThat(testTest7.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTest7.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testTest7.getDate().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_DATE);
        assertThat(testTest7.getEtad()).isEqualTo(DEFAULT_ETAD);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = test7Repository.findAll().size();
        // set the field null
        test7.setName(null);

        // Create the Test7, which fails.
        restTest7MockMvc.perform(post("/api/test7s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(test7)))
                .andExpect(status().isBadRequest());

        List<Test7> test7s = test7Repository.findAll();
        assertThat(test7s).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAgeIsRequired() throws Exception {
        int databaseSizeBeforeTest = test7Repository.findAll().size();
        // set the field null
        test7.setAge(null);

        // Create the Test7, which fails.
        restTest7MockMvc.perform(post("/api/test7s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(test7)))
                .andExpect(status().isBadRequest());

        List<Test7> test7s = test7Repository.findAll();
        assertThat(test7s).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = test7Repository.findAll().size();
        // set the field null
        test7.setDate(null);

        // Create the Test7, which fails.
        restTest7MockMvc.perform(post("/api/test7s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(test7)))
                .andExpect(status().isBadRequest());

        List<Test7> test7s = test7Repository.findAll();
        assertThat(test7s).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEtadIsRequired() throws Exception {
        int databaseSizeBeforeTest = test7Repository.findAll().size();
        // set the field null
        test7.setEtad(null);

        // Create the Test7, which fails.
        restTest7MockMvc.perform(post("/api/test7s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(test7)))
                .andExpect(status().isBadRequest());

        List<Test7> test7s = test7Repository.findAll();
        assertThat(test7s).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTest7s() throws Exception {
        // Initialize the database
        test7Repository.saveAndFlush(test7);

        // Get all the test7s
        restTest7MockMvc.perform(get("/api/test7s"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(test7.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE_STR)))
                .andExpect(jsonPath("$.[*].etad").value(hasItem(DEFAULT_ETAD.toString())));
    }

    @Test
    @Transactional
    public void getTest7() throws Exception {
        // Initialize the database
        test7Repository.saveAndFlush(test7);

        // Get the test7
        restTest7MockMvc.perform(get("/api/test7s/{id}", test7.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(test7.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE_STR))
            .andExpect(jsonPath("$.etad").value(DEFAULT_ETAD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTest7() throws Exception {
        // Get the test7
        restTest7MockMvc.perform(get("/api/test7s/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTest7() throws Exception {
        // Initialize the database
        test7Repository.saveAndFlush(test7);

		int databaseSizeBeforeUpdate = test7Repository.findAll().size();

        // Update the test7
        test7.setName(UPDATED_NAME);
        test7.setAge(UPDATED_AGE);
        test7.setDate(UPDATED_DATE);
        test7.setEtad(UPDATED_ETAD);
        restTest7MockMvc.perform(put("/api/test7s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(test7)))
                .andExpect(status().isOk());

        // Validate the Test7 in the database
        List<Test7> test7s = test7Repository.findAll();
        assertThat(test7s).hasSize(databaseSizeBeforeUpdate);
        Test7 testTest7 = test7s.get(test7s.size() - 1);
        assertThat(testTest7.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTest7.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testTest7.getDate().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_DATE);
        assertThat(testTest7.getEtad()).isEqualTo(UPDATED_ETAD);
    }

    @Test
    @Transactional
    public void deleteTest7() throws Exception {
        // Initialize the database
        test7Repository.saveAndFlush(test7);

		int databaseSizeBeforeDelete = test7Repository.findAll().size();

        // Get the test7
        restTest7MockMvc.perform(delete("/api/test7s/{id}", test7.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Test7> test7s = test7Repository.findAll();
        assertThat(test7s).hasSize(databaseSizeBeforeDelete - 1);
    }
}
