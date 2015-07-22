package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Test2;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Test2 entity.
 */
public interface Test2Repository extends JpaRepository<Test2,Long> {

}
