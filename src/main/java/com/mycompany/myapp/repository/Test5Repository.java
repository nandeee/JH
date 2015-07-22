package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Test5;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Test5 entity.
 */
public interface Test5Repository extends JpaRepository<Test5,Long> {

}
