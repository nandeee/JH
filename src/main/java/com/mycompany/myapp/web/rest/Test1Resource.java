package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Test1;
import com.mycompany.myapp.repository.Test1Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Test1.
 */
@RestController
@RequestMapping("/api")
public class Test1Resource {

    private final Logger log = LoggerFactory.getLogger(Test1Resource.class);

    @Inject
    private Test1Repository test1Repository;

    /**
     * POST  /test1s -> Create a new test1.
     */
    @RequestMapping(value = "/test1s",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Test1> create(@RequestBody Test1 test1) throws URISyntaxException {
        log.debug("REST request to save Test1 : {}", test1);
        if (test1.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new test1 cannot already have an ID").body(null);
        }
        Test1 result = test1Repository.save(test1);
        return ResponseEntity.created(new URI("/api/test1s/" + test1.getId())).body(result);
    }

    /**
     * PUT  /test1s -> Updates an existing test1.
     */
    @RequestMapping(value = "/test1s",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Test1> update(@RequestBody Test1 test1) throws URISyntaxException {
        log.debug("REST request to update Test1 : {}", test1);
        if (test1.getId() == null) {
            return create(test1);
        }
        Test1 result = test1Repository.save(test1);
        return ResponseEntity.ok().body(result);
    }

    /**
     * GET  /test1s -> get all the test1s.
     */
    @RequestMapping(value = "/test1s",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Test1> getAll() {
        log.debug("REST request to get all Test1s");
        return test1Repository.findAll();
    }

    /**
     * GET  /test1s/:id -> get the "id" test1.
     */
    @RequestMapping(value = "/test1s/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Test1> get(@PathVariable Long id) {
        log.debug("REST request to get Test1 : {}", id);
        return Optional.ofNullable(test1Repository.findOne(id))
            .map(test1 -> new ResponseEntity<>(
                test1,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /test1s/:id -> delete the "id" test1.
     */
    @RequestMapping(value = "/test1s/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Test1 : {}", id);
        test1Repository.delete(id);
    }
}
