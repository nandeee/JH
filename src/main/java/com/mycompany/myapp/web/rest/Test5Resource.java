package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Test5;
import com.mycompany.myapp.repository.Test5Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Test5.
 */
@RestController
@RequestMapping("/api")
public class Test5Resource {

    private final Logger log = LoggerFactory.getLogger(Test5Resource.class);

    @Inject
    private Test5Repository test5Repository;

    /**
     * POST  /test5s -> Create a new test5.
     */
    @RequestMapping(value = "/test5s",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Test5> create(@Valid @RequestBody Test5 test5) throws URISyntaxException {
        log.debug("REST request to save Test5 : {}", test5);
        if (test5.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new test5 cannot already have an ID").body(null);
        }
        Test5 result = test5Repository.save(test5);
        return ResponseEntity.created(new URI("/api/test5s/" + test5.getId())).body(result);
    }

    /**
     * PUT  /test5s -> Updates an existing test5.
     */
    @RequestMapping(value = "/test5s",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Test5> update(@Valid @RequestBody Test5 test5) throws URISyntaxException {
        log.debug("REST request to update Test5 : {}", test5);
        if (test5.getId() == null) {
            return create(test5);
        }
        Test5 result = test5Repository.save(test5);
        return ResponseEntity.ok().body(result);
    }

    /**
     * GET  /test5s -> get all the test5s.
     */
    @RequestMapping(value = "/test5s",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Test5> getAll() {
        log.debug("REST request to get all Test5s");
        return test5Repository.findAll();
    }

    /**
     * GET  /test5s/:id -> get the "id" test5.
     */
    @RequestMapping(value = "/test5s/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Test5> get(@PathVariable Long id) {
        log.debug("REST request to get Test5 : {}", id);
        return Optional.ofNullable(test5Repository.findOne(id))
            .map(test5 -> new ResponseEntity<>(
                test5,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /test5s/:id -> delete the "id" test5.
     */
    @RequestMapping(value = "/test5s/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Test5 : {}", id);
        test5Repository.delete(id);
    }
}
