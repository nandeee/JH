package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Test2;
import com.mycompany.myapp.repository.Test2Repository;
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
 * REST controller for managing Test2.
 */
@RestController
@RequestMapping("/api")
public class Test2Resource {

    private final Logger log = LoggerFactory.getLogger(Test2Resource.class);

    @Inject
    private Test2Repository test2Repository;

    /**
     * POST  /test2s -> Create a new test2.
     */
    @RequestMapping(value = "/test2s",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Test2> create(@Valid @RequestBody Test2 test2) throws URISyntaxException {
        log.debug("REST request to save Test2 : {}", test2);
        if (test2.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new test2 cannot already have an ID").body(null);
        }
        Test2 result = test2Repository.save(test2);
        return ResponseEntity.created(new URI("/api/test2s/" + test2.getId())).body(result);
    }

    /**
     * PUT  /test2s -> Updates an existing test2.
     */
    @RequestMapping(value = "/test2s",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Test2> update(@Valid @RequestBody Test2 test2) throws URISyntaxException {
        log.debug("REST request to update Test2 : {}", test2);
        if (test2.getId() == null) {
            return create(test2);
        }
        Test2 result = test2Repository.save(test2);
        return ResponseEntity.ok().body(result);
    }

    /**
     * GET  /test2s -> get all the test2s.
     */
    @RequestMapping(value = "/test2s",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Test2> getAll() {
        log.debug("REST request to get all Test2s");
        return test2Repository.findAll();
    }

    /**
     * GET  /test2s/:id -> get the "id" test2.
     */
    @RequestMapping(value = "/test2s/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Test2> get(@PathVariable Long id) {
        log.debug("REST request to get Test2 : {}", id);
        return Optional.ofNullable(test2Repository.findOne(id))
            .map(test2 -> new ResponseEntity<>(
                test2,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /test2s/:id -> delete the "id" test2.
     */
    @RequestMapping(value = "/test2s/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Test2 : {}", id);
        test2Repository.delete(id);
    }
}
