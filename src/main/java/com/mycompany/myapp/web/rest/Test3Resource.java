package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Test3;
import com.mycompany.myapp.repository.Test3Repository;
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
 * REST controller for managing Test3.
 */
@RestController
@RequestMapping("/api")
public class Test3Resource {

    private final Logger log = LoggerFactory.getLogger(Test3Resource.class);

    @Inject
    private Test3Repository test3Repository;

    /**
     * POST  /test3s -> Create a new test3.
     */
    @RequestMapping(value = "/test3s",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Test3> create(@Valid @RequestBody Test3 test3) throws URISyntaxException {
        log.debug("REST request to save Test3 : {}", test3);
        if (test3.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new test3 cannot already have an ID").body(null);
        }
        Test3 result = test3Repository.save(test3);
        return ResponseEntity.created(new URI("/api/test3s/" + test3.getId())).body(result);
    }

    /**
     * PUT  /test3s -> Updates an existing test3.
     */
    @RequestMapping(value = "/test3s",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Test3> update(@Valid @RequestBody Test3 test3) throws URISyntaxException {
        log.debug("REST request to update Test3 : {}", test3);
        if (test3.getId() == null) {
            return create(test3);
        }
        Test3 result = test3Repository.save(test3);
        return ResponseEntity.ok().body(result);
    }

    /**
     * GET  /test3s -> get all the test3s.
     */
    @RequestMapping(value = "/test3s",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Test3> getAll() {
        log.debug("REST request to get all Test3s");
        return test3Repository.findAll();
    }

    /**
     * GET  /test3s/:id -> get the "id" test3.
     */
    @RequestMapping(value = "/test3s/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Test3> get(@PathVariable Long id) {
        log.debug("REST request to get Test3 : {}", id);
        return Optional.ofNullable(test3Repository.findOne(id))
            .map(test3 -> new ResponseEntity<>(
                test3,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /test3s/:id -> delete the "id" test3.
     */
    @RequestMapping(value = "/test3s/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Test3 : {}", id);
        test3Repository.delete(id);
    }
}
