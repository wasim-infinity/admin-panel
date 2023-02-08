package com.mvnproj.admin.repository.urlshortner;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mvnproj.admin.entity.urlshortner.ApiLog;

@Repository
public interface ApiLogRepository extends JpaRepository<ApiLog, Long> {

	Page<ApiLog> findAll(Pageable pageable);

	@Query(value = "from ApiLog where cast(id as string) like concat( '%', ?1, '%')")
	List<ApiLog> findAllById(long id, Pageable pageable);
	
	@Query(value = "from ApiLog where cast(userId as string) like concat( '%', ?1, '%')")
	List<ApiLog> findAllByUserId(long userId, Pageable pageable);
	
	@Query(value="from ApiLog where cast(id as string) like concat( '%', ?1, '%') and cast(userId as string) like concat( '%', ?2, '%')")
	List<ApiLog> findAllByIdAndUserId(long id, long userId, Pageable pageable);
	
	@Query(value="from ApiLog where cast(id as string) like concat( '%', ?2, '%') and cast(userId as string) like concat( '%', ?1, '%')")
	List<ApiLog> findAllByUserIdAndId(long userId, long id, Pageable pageable);

}
