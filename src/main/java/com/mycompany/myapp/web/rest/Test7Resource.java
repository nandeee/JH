package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Test7;
import com.mycompany.myapp.repository.Test7Repository;
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
 * REST controller for managing Test7.
 */
@RestController
@RequestMapping("/api")
public class Test7Resource {

    private final Logger log = LoggerFactory.getLogger(Test7Resource.class);

    @Inject
    private Test7Repository test7Repository;

    /**
     * POST  /test7s -> Create a new test7.
     */
    @RequestMapping(value = "/test7s",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Test7> create(@Valid @RequestBody Test7 test7) throws URISyntaxException {
        log.debug("REST request to save Test7 : {}", test7);
        if (test7.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new test7 cannot already have an ID").body(null);
        }
        Test7 result = test7Repository.save(test7);
        return ResponseEntity.created(new URI("/api/test7s/" + test7.getId())).body(result);
    }

    /**
     * PUT  /test7s -> Updates an existing test7.
     */
    @RequestMapping(value = "/test7s",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Test7> update(@Valid @RequestBody Test7 test7) throws URISyntaxException {
        log.debug("REST request to update Test7 : {}", test7);
        if (test7.getId() == null) {
            return create(test7);
        }
        Test7 result = test7Repository.save(test7);
        return ResponseEntity.ok().body(result);
    }

    /**
     * GET  /test7s -> get all the test7s.
     */
    @RequestMapping(value = "/test7s",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Test7> getAll() {
        log.debug("REST request to get all Test7s");
        return test7Repository.findAll();
    }

    /**
     * GET  /test7s/:id -> get the "id" test7.
     */
    @RequestMapping(value = "/test7s/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Test7> get(@PathVariable Long id) {
        log.debug("REST request to get Test7 : {}", id);
        return Optional.ofNullable(test7Repository.findOne(id))
            .map(test7 -> new ResponseEntity<>(
                test7,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /test7s/:id -> delete the "id" test7.
     */
    @RequestMapping(value = "/test7s/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Test7 : {}", id);
        test7Repository.delete(id);
    }
}
