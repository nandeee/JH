package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Test9;
import com.mycompany.myapp.repository.Test9Repository;
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
 * REST controller for managing Test9.
 */
@RestController
@RequestMapping("/api")
public class Test9Resource {

    private final Logger log = LoggerFactory.getLogger(Test9Resource.class);

    @Inject
    private Test9Repository test9Repository;

    /**
     * POST  /test9s -> Create a new test9.
     */
    @RequestMapping(value = "/test9s",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Test9> create(@Valid @RequestBody Test9 test9) throws URISyntaxException {
        log.debug("REST request to save Test9 : {}", test9);
        if (test9.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new test9 cannot already have an ID").body(null);
        }
        Test9 result = test9Repository.save(test9);
        return ResponseEntity.created(new URI("/api/test9s/" + test9.getId())).body(result);
    }

    /**
     * PUT  /test9s -> Updates an existing test9.
     */
    @RequestMapping(value = "/test9s",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Test9> update(@Valid @RequestBody Test9 test9) throws URISyntaxException {
        log.debug("REST request to update Test9 : {}", test9);
        if (test9.getId() == null) {
            return create(test9);
        }
        Test9 result = test9Repository.save(test9);
        return ResponseEntity.ok().body(result);
    }

    /**
     * GET  /test9s -> get all the test9s.
     */
    @RequestMapping(value = "/test9s",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Test9> getAll() {
        log.debug("REST request to get all Test9s");
        return test9Repository.findAll();
    }

    /**
     * GET  /test9s/:id -> get the "id" test9.
     */
    @RequestMapping(value = "/test9s/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Test9> get(@PathVariable Long id) {
        log.debug("REST request to get Test9 : {}", id);
        return Optional.ofNullable(test9Repository.findOne(id))
            .map(test9 -> new ResponseEntity<>(
                test9,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /test9s/:id -> delete the "id" test9.
     */
    @RequestMapping(value = "/test9s/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Test9 : {}", id);
        test9Repository.delete(id);
    }
}
