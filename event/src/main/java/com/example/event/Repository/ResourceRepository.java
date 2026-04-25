package com.example.event.Repository;

import com.example.event.Model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {

    List<Resource> findByType(Resource.ResourceType type);

    List<Resource> findByAvailableTrue();

    List<Resource> findByTypeAndAvailableTrue(Resource.ResourceType type);

    boolean existsByNameIgnoreCase(String name);
}
