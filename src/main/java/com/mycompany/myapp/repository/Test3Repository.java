package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Test3;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Test3 entity.
 */
public interface Test3Repository extends JpaRepository<Test3,Long> {

}
