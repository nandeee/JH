package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Test3;
import com.mycompany.myapp.repository.Test3Repository;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the Test3Resource REST controller.
 *
 * @see Test3Resource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Test3ResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private static final String DEFAULT_ONE = "SAMPLE_TEXT";
    private static final String UPDATED_ONE = "UPDATED_TEXT";

    private static final Integer DEFAULT_TWO = 0;
    private static final Integer UPDATED_TWO = 1;

    private static final BigDecimal DEFAULT_THREE = new BigDecimal(0);
    private static final BigDecimal UPDATED_THREE = new BigDecimal(1);

    private static final Long DEFAULT_FOUR = 0L;
    private static final Long UPDATED_FOUR = 1L;

    private static final LocalDate DEFAULT_FIVE = new LocalDate(0L);
    private static final LocalDate UPDATED_FIVE = new LocalDate();

    private static final DateTime DEFAULT_SIX = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_SIX = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_SIX_STR = dateTimeFormatter.print(DEFAULT_SIX);

    private static final Boolean DEFAULT_SEVEN = false;
    private static final Boolean UPDATED_SEVEN = true;

    @Inject
    private Test3Repository test3Repository;

    private MockMvc restTest3MockMvc;

    private Test3 test3;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Test3Resource test3Resource = new Test3Resource();
        ReflectionTestUtils.setField(test3Resource, "test3Repository", test3Repository);
        this.restTest3MockMvc = MockMvcBuilders.standaloneSetup(test3Resource).build();
    }

    @Before
    public void initTest() {
        test3 = new Test3();
        test3.setOne(DEFAULT_ONE);
        test3.setTwo(DEFAULT_TWO);
        test3.setThree(DEFAULT_THREE);
        test3.setFour(DEFAULT_FOUR);
        test3.setFive(DEFAULT_FIVE);
        test3.setSix(DEFAULT_SIX);
        test3.setSeven(DEFAULT_SEVEN);
    }

    @Test
    @Transactional
    public void createTest3() throws Exception {
        int databaseSizeBeforeCreate = test3Repository.findAll().size();

        // Create the Test3
        restTest3MockMvc.perform(post("/api/test3s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(test3)))
                .andExpect(status().isCreated());

        // Validate the Test3 in the database
        List<Test3> test3s = test3Repository.findAll();
        assertThat(test3s).hasSize(databaseSizeBeforeCreate + 1);
        Test3 testTest3 = test3s.get(test3s.size() - 1);
        assertThat(testTest3.getOne()).isEqualTo(DEFAULT_ONE);
        assertThat(testTest3.getTwo()).isEqualTo(DEFAULT_TWO);
        assertThat(testTest3.getThree()).isEqualTo(DEFAULT_THREE);
        assertThat(testTest3.getFour()).isEqualTo(DEFAULT_FOUR);
        assertThat(testTest3.getFive()).isEqualTo(DEFAULT_FIVE);
        assertThat(testTest3.getSix().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_SIX);
        assertThat(testTest3.getSeven()).isEqualTo(DEFAULT_SEVEN);
    }

    @Test
    @Transactional
    public void checkOneIsRequired() throws Exception {
        int databaseSizeBeforeTest = test3Repository.findAll().size();
        // set the field null
        test3.setOne(null);

        // Create the Test3, which fails.
        restTest3MockMvc.perform(post("/api/test3s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(test3)))
                .andExpect(status().isBadRequest());

        List<Test3> test3s = test3Repository.findAll();
        assertThat(test3s).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTest3s() throws Exception {
        // Initialize the database
        test3Repository.saveAndFlush(test3);

        // Get all the test3s
        restTest3MockMvc.perform(get("/api/test3s"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(test3.getId().intValue())))
                .andExpect(jsonPath("$.[*].one").value(hasItem(DEFAULT_ONE.toString())))
                .andExpect(jsonPath("$.[*].two").value(hasItem(DEFAULT_TWO)))
                .andExpect(jsonPath("$.[*].three").value(hasItem(DEFAULT_THREE.intValue())))
                .andExpect(jsonPath("$.[*].four").value(hasItem(DEFAULT_FOUR.intValue())))
                .andExpect(jsonPath("$.[*].five").value(hasItem(DEFAULT_FIVE.toString())))
                .andExpect(jsonPath("$.[*].six").value(hasItem(DEFAULT_SIX_STR)))
                .andExpect(jsonPath("$.[*].seven").value(hasItem(DEFAULT_SEVEN.booleanValue())));
    }

    @Test
    @Transactional
    public void getTest3() throws Exception {
        // Initialize the database
        test3Repository.saveAndFlush(test3);

        // Get the test3
        restTest3MockMvc.perform(get("/api/test3s/{id}", test3.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(test3.getId().intValue()))
            .andExpect(jsonPath("$.one").value(DEFAULT_ONE.toString()))
            .andExpect(jsonPath("$.two").value(DEFAULT_TWO))
            .andExpect(jsonPath("$.three").value(DEFAULT_THREE.intValue()))
            .andExpect(jsonPath("$.four").value(DEFAULT_FOUR.intValue()))
            .andExpect(jsonPath("$.five").value(DEFAULT_FIVE.toString()))
            .andExpect(jsonPath("$.six").value(DEFAULT_SIX_STR))
            .andExpect(jsonPath("$.seven").value(DEFAULT_SEVEN.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTest3() throws Exception {
        // Get the test3
        restTest3MockMvc.perform(get("/api/test3s/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTest3() throws Exception {
        // Initialize the database
        test3Repository.saveAndFlush(test3);

		int databaseSizeBeforeUpdate = test3Repository.findAll().size();

        // Update the test3
        test3.setOne(UPDATED_ONE);
        test3.setTwo(UPDATED_TWO);
        test3.setThree(UPDATED_THREE);
        test3.setFour(UPDATED_FOUR);
        test3.setFive(UPDATED_FIVE);
        test3.setSix(UPDATED_SIX);
        test3.setSeven(UPDATED_SEVEN);
        restTest3MockMvc.perform(put("/api/test3s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(test3)))
                .andExpect(status().isOk());

        // Validate the Test3 in the database
        List<Test3> test3s = test3Repository.findAll();
        assertThat(test3s).hasSize(databaseSizeBeforeUpdate);
        Test3 testTest3 = test3s.get(test3s.size() - 1);
        assertThat(testTest3.getOne()).isEqualTo(UPDATED_ONE);
        assertThat(testTest3.getTwo()).isEqualTo(UPDATED_TWO);
        assertThat(testTest3.getThree()).isEqualTo(UPDATED_THREE);
        assertThat(testTest3.getFour()).isEqualTo(UPDATED_FOUR);
        assertThat(testTest3.getFive()).isEqualTo(UPDATED_FIVE);
        assertThat(testTest3.getSix().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_SIX);
        assertThat(testTest3.getSeven()).isEqualTo(UPDATED_SEVEN);
    }

    @Test
    @Transactional
    public void deleteTest3() throws Exception {
        // Initialize the database
        test3Repository.saveAndFlush(test3);

		int databaseSizeBeforeDelete = test3Repository.findAll().size();

        // Get the test3
        restTest3MockMvc.perform(delete("/api/test3s/{id}", test3.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Test3> test3s = test3Repository.findAll();
        assertThat(test3s).hasSize(databaseSizeBeforeDelete - 1);
    }
}
