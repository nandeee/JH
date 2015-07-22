package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Test2;
import com.mycompany.myapp.repository.Test2Repository;

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
 * Test class for the Test2Resource REST controller.
 *
 * @see Test2Resource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Test2ResourceTest {


    private static final Integer DEFAULT_AGE = 10;
    private static final Integer UPDATED_AGE = 11;

    @Inject
    private Test2Repository test2Repository;

    private MockMvc restTest2MockMvc;

    private Test2 test2;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Test2Resource test2Resource = new Test2Resource();
        ReflectionTestUtils.setField(test2Resource, "test2Repository", test2Repository);
        this.restTest2MockMvc = MockMvcBuilders.standaloneSetup(test2Resource).build();
    }

    @Before
    public void initTest() {
        test2 = new Test2();
        test2.setAge(DEFAULT_AGE);
    }

    @Test
    @Transactional
    public void createTest2() throws Exception {
        int databaseSizeBeforeCreate = test2Repository.findAll().size();

        // Create the Test2
        restTest2MockMvc.perform(post("/api/test2s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(test2)))
                .andExpect(status().isCreated());

        // Validate the Test2 in the database
        List<Test2> test2s = test2Repository.findAll();
        assertThat(test2s).hasSize(databaseSizeBeforeCreate + 1);
        Test2 testTest2 = test2s.get(test2s.size() - 1);
        assertThat(testTest2.getAge()).isEqualTo(DEFAULT_AGE);
    }

    @Test
    @Transactional
    public void checkAgeIsRequired() throws Exception {
        int databaseSizeBeforeTest = test2Repository.findAll().size();
        // set the field null
        test2.setAge(null);

        // Create the Test2, which fails.
        restTest2MockMvc.perform(post("/api/test2s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(test2)))
                .andExpect(status().isBadRequest());

        List<Test2> test2s = test2Repository.findAll();
        assertThat(test2s).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTest2s() throws Exception {
        // Initialize the database
        test2Repository.saveAndFlush(test2);

        // Get all the test2s
        restTest2MockMvc.perform(get("/api/test2s"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(test2.getId().intValue())))
                .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)));
    }

    @Test
    @Transactional
    public void getTest2() throws Exception {
        // Initialize the database
        test2Repository.saveAndFlush(test2);

        // Get the test2
        restTest2MockMvc.perform(get("/api/test2s/{id}", test2.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(test2.getId().intValue()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE));
    }

    @Test
    @Transactional
    public void getNonExistingTest2() throws Exception {
        // Get the test2
        restTest2MockMvc.perform(get("/api/test2s/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTest2() throws Exception {
        // Initialize the database
        test2Repository.saveAndFlush(test2);

		int databaseSizeBeforeUpdate = test2Repository.findAll().size();

        // Update the test2
        test2.setAge(UPDATED_AGE);
        restTest2MockMvc.perform(put("/api/test2s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(test2)))
                .andExpect(status().isOk());

        // Validate the Test2 in the database
        List<Test2> test2s = test2Repository.findAll();
        assertThat(test2s).hasSize(databaseSizeBeforeUpdate);
        Test2 testTest2 = test2s.get(test2s.size() - 1);
        assertThat(testTest2.getAge()).isEqualTo(UPDATED_AGE);
    }

    @Test
    @Transactional
    public void deleteTest2() throws Exception {
        // Initialize the database
        test2Repository.saveAndFlush(test2);

		int databaseSizeBeforeDelete = test2Repository.findAll().size();

        // Get the test2
        restTest2MockMvc.perform(delete("/api/test2s/{id}", test2.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Test2> test2s = test2Repository.findAll();
        assertThat(test2s).hasSize(databaseSizeBeforeDelete - 1);
    }
}
