package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Test4;
import com.mycompany.myapp.repository.Test4Repository;
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
 * REST controller for managing Test4.
 */
@RestController
@RequestMapping("/api")
public class Test4Resource {

    private final Logger log = LoggerFactory.getLogger(Test4Resource.class);

    @Inject
    private Test4Repository test4Repository;

    /**
     * POST  /test4s -> Create a new test4.
     */
    @RequestMapping(value = "/test4s",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Test4> create(@Valid @RequestBody Test4 test4) throws URISyntaxException {
        log.debug("REST request to save Test4 : {}", test4);
        if (test4.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new test4 cannot already have an ID").body(null);
        }
        Test4 result = test4Repository.save(test4);
        return ResponseEntity.created(new URI("/api/test4s/" + test4.getId())).body(result);
    }

    /**
     * PUT  /test4s -> Updates an existing test4.
     */
    @RequestMapping(value = "/test4s",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Test4> update(@Valid @RequestBody Test4 test4) throws URISyntaxException {
        log.debug("REST request to update Test4 : {}", test4);
        if (test4.getId() == null) {
            return create(test4);
        }
        Test4 result = test4Repository.save(test4);
        return ResponseEntity.ok().body(result);
    }

    /**
     * GET  /test4s -> get all the test4s.
     */
    @RequestMapping(value = "/test4s",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Test4> getAll() {
        log.debug("REST request to get all Test4s");
        return test4Repository.findAll();
    }

    /**
     * GET  /test4s/:id -> get the "id" test4.
     */
    @RequestMapping(value = "/test4s/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Test4> get(@PathVariable Long id) {
        log.debug("REST request to get Test4 : {}", id);
        return Optional.ofNullable(test4Repository.findOne(id))
            .map(test4 -> new ResponseEntity<>(
                test4,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /test4s/:id -> delete the "id" test4.
     */
    @RequestMapping(value = "/test4s/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Test4 : {}", id);
        test4Repository.delete(id);
    }
}
