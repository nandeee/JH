package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Test4;
import com.mycompany.myapp.repository.Test4Repository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the Test4Resource REST controller.
 *
 * @see Test4Resource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Test4ResourceTest {

    private static final String DEFAULT_ONE = "SAMPLE_TEXT";
    private static final String UPDATED_ONE = "UPDATED_TEXT";

    private static final Integer DEFAULT_TWO = 0;
    private static final Integer UPDATED_TWO = 1;

    private static final Long DEFAULT_THREE = 0L;
    private static final Long UPDATED_THREE = 1L;

    @Inject
    private Test4Repository test4Repository;

    private MockMvc restTest4MockMvc;

    private Test4 test4;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Test4Resource test4Resource = new Test4Resource();
        ReflectionTestUtils.setField(test4Resource, "test4Repository", test4Repository);
        this.restTest4MockMvc = MockMvcBuilders.standaloneSetup(test4Resource).build();
    }

    @Before
    public void initTest() {
        test4 = new Test4();
        test4.setOne(DEFAULT_ONE);
        test4.setTwo(DEFAULT_TWO);
        test4.setThree(DEFAULT_THREE);
    }

    @Test
    @Transactional
    public void createTest4() throws Exception {
        int databaseSizeBeforeCreate = test4Repository.findAll().size();

        // Create the Test4
        restTest4MockMvc.perform(post("/api/test4s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(test4)))
                .andExpect(status().isCreated());

        // Validate the Test4 in the database
        List<Test4> test4s = test4Repository.findAll();
        assertThat(test4s).hasSize(databaseSizeBeforeCreate + 1);
        Test4 testTest4 = test4s.get(test4s.size() - 1);
        assertThat(testTest4.getOne()).isEqualTo(DEFAULT_ONE);
        assertThat(testTest4.getTwo()).isEqualTo(DEFAULT_TWO);
        assertThat(testTest4.getThree()).isEqualTo(DEFAULT_THREE);
    }

    @Test
    @Transactional
    public void checkOneIsRequired() throws Exception {
        int databaseSizeBeforeTest = test4Repository.findAll().size();
        // set the field null
        test4.setOne(null);

        // Create the Test4, which fails.
        restTest4MockMvc.perform(post("/api/test4s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(test4)))
                .andExpect(status().isBadRequest());

        List<Test4> test4s = test4Repository.findAll();
        assertThat(test4s).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkThreeIsRequired() throws Exception {
        int databaseSizeBeforeTest = test4Repository.findAll().size();
        // set the field null
        test4.setThree(null);

        // Create the Test4, which fails.
        restTest4MockMvc.perform(post("/api/test4s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(test4)))
                .andExpect(status().isBadRequest());

        List<Test4> test4s = test4Repository.findAll();
        assertThat(test4s).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTest4s() throws Exception {
        // Initialize the database
        test4Repository.saveAndFlush(test4);

        // Get all the test4s
        restTest4MockMvc.perform(get("/api/test4s"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(test4.getId().intValue())))
                .andExpect(jsonPath("$.[*].one").value(hasItem(DEFAULT_ONE.toString())))
                .andExpect(jsonPath("$.[*].two").value(hasItem(DEFAULT_TWO)))
                .andExpect(jsonPath("$.[*].three").value(hasItem(DEFAULT_THREE.intValue())));
    }

    @Test
    @Transactional
    public void getTest4() throws Exception {
        // Initialize the database
        test4Repository.saveAndFlush(test4);

        // Get the test4
        restTest4MockMvc.perform(get("/api/test4s/{id}", test4.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(test4.getId().intValue()))
            .andExpect(jsonPath("$.one").value(DEFAULT_ONE.toString()))
            .andExpect(jsonPath("$.two").value(DEFAULT_TWO))
            .andExpect(jsonPath("$.three").value(DEFAULT_THREE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTest4() throws Exception {
        // Get the test4
        restTest4MockMvc.perform(get("/api/test4s/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTest4() throws Exception {
        // Initialize the database
        test4Repository.saveAndFlush(test4);

		int databaseSizeBeforeUpdate = test4Repository.findAll().size();

        // Update the test4
        test4.setOne(UPDATED_ONE);
        test4.setTwo(UPDATED_TWO);
        test4.setThree(UPDATED_THREE);
        restTest4MockMvc.perform(put("/api/test4s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(test4)))
                .andExpect(status().isOk());

        // Validate the Test4 in the database
        List<Test4> test4s = test4Repository.findAll();
        assertThat(test4s).hasSize(databaseSizeBeforeUpdate);
        Test4 testTest4 = test4s.get(test4s.size() - 1);
        assertThat(testTest4.getOne()).isEqualTo(UPDATED_ONE);
        assertThat(testTest4.getTwo()).isEqualTo(UPDATED_TWO);
        assertThat(testTest4.getThree()).isEqualTo(UPDATED_THREE);
    }

    @Test
    @Transactional
    public void deleteTest4() throws Exception {
        // Initialize the database
        test4Repository.saveAndFlush(test4);

		int databaseSizeBeforeDelete = test4Repository.findAll().size();

        // Get the test4
        restTest4MockMvc.perform(delete("/api/test4s/{id}", test4.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Test4> test4s = test4Repository.findAll();
        assertThat(test4s).hasSize(databaseSizeBeforeDelete - 1);
    }
}
