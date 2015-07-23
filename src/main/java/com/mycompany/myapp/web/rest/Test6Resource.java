package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Test6;
import com.mycompany.myapp.repository.Test6Repository;
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
 * REST controller for managing Test6.
 */
@RestController
@RequestMapping("/api")
public class Test6Resource {

    private final Logger log = LoggerFactory.getLogger(Test6Resource.class);

    @Inject
    private Test6Repository test6Repository;

    /**
     * POST  /test6s -> Create a new test6.
     */
    @RequestMapping(value = "/test6s",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Test6> create(@Valid @RequestBody Test6 test6) throws URISyntaxException {
        log.debug("REST request to save Test6 : {}", test6);
        if (test6.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new test6 cannot already have an ID").body(null);
        }
        Test6 result = test6Repository.save(test6);
        return ResponseEntity.created(new URI("/api/test6s/" + test6.getId())).body(result);
    }

    /**
     * PUT  /test6s -> Updates an existing test6.
     */
    @RequestMapping(value = "/test6s",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Test6> update(@Valid @RequestBody Test6 test6) throws URISyntaxException {
        log.debug("REST request to update Test6 : {}", test6);
        if (test6.getId() == null) {
            return create(test6);
        }
        Test6 result = test6Repository.save(test6);
        return ResponseEntity.ok().body(result);
    }

    /**
     * GET  /test6s -> get all the test6s.
     */
    @RequestMapping(value = "/test6s",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Test6> getAll() {
        log.debug("REST request to get all Test6s");
        return test6Repository.findAll();
    }

    /**
     * GET  /test6s/:id -> get the "id" test6.
     */
    @RequestMapping(value = "/test6s/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Test6> get(@PathVariable Long id) {
        log.debug("REST request to get Test6 : {}", id);
        return Optional.ofNullable(test6Repository.findOne(id))
            .map(test6 -> new ResponseEntity<>(
                test6,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /test6s/:id -> delete the "id" test6.
     */
    @RequestMapping(value = "/test6s/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Test6 : {}", id);
        test6Repository.delete(id);
    }
}
