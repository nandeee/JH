package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Test8;
import com.mycompany.myapp.repository.Test8Repository;
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
 * REST controller for managing Test8.
 */
@RestController
@RequestMapping("/api")
public class Test8Resource {

    private final Logger log = LoggerFactory.getLogger(Test8Resource.class);

    @Inject
    private Test8Repository test8Repository;

    /**
     * POST  /test8s -> Create a new test8.
     */
    @RequestMapping(value = "/test8s",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Test8> create(@Valid @RequestBody Test8 test8) throws URISyntaxException {
        log.debug("REST request to save Test8 : {}", test8);
        if (test8.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new test8 cannot already have an ID").body(null);
        }
        Test8 result = test8Repository.save(test8);
        return ResponseEntity.created(new URI("/api/test8s/" + test8.getId())).body(result);
    }

    /**
     * PUT  /test8s -> Updates an existing test8.
     */
    @RequestMapping(value = "/test8s",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Test8> update(@Valid @RequestBody Test8 test8) throws URISyntaxException {
        log.debug("REST request to update Test8 : {}", test8);
        if (test8.getId() == null) {
            return create(test8);
        }
        Test8 result = test8Repository.save(test8);
        return ResponseEntity.ok().body(result);
    }

    /**
     * GET  /test8s -> get all the test8s.
     */
    @RequestMapping(value = "/test8s",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Test8> getAll() {
        log.debug("REST request to get all Test8s");
        return test8Repository.findAll();
    }

    /**
     * GET  /test8s/:id -> get the "id" test8.
     */
    @RequestMapping(value = "/test8s/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Test8> get(@PathVariable Long id) {
        log.debug("REST request to get Test8 : {}", id);
        return Optional.ofNullable(test8Repository.findOne(id))
            .map(test8 -> new ResponseEntity<>(
                test8,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /test8s/:id -> delete the "id" test8.
     */
    @RequestMapping(value = "/test8s/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Test8 : {}", id);
        test8Repository.delete(id);
    }
}
