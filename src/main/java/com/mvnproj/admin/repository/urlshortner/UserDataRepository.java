package com.mvnproj.admin.repository.urlshortner;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mvnproj.admin.entity.urlshortner.ApiLog;
import com.mvnproj.admin.entity.urlshortner.UserData;

@Repository
public interface UserDataRepository extends JpaRepository<UserData, Long>{

	public UserData findByUserName(String name);

	public UserData findByUserNameAndPassword(String userName, String password);
	
	Page<UserData> findAll(Pageable pageable);

	@Query(value = "from UserData where email like concat( '%', ?1, '%')")
	public List<UserData> findAllByEmail(String email, Pageable pageable);

	@Query(value = "from UserData where cast(phone as string) like concat( '%', ?1, '%')")
	public List<UserData> findAllByPhone(int phone, Pageable pageable);

	@Query(value = "from UserData where userName like concat( '%', ?1, '%')")
	public List<UserData> findAllByUserName(String userName, Pageable pageable);

	@Query(value = "from UserData where email like concat( '%', ?1, '%') and cast(phone as string) like concat( '%', ?2, '%')")
	public List<UserData> findAllByEmailAndPhone(String email, int phone, Pageable pageable);

	@Query(value = "from UserData where email like concat( '%', ?2, '%') and cast(phone as string) like concat( '%', ?1, '%')")
	public List<UserData> findAllByPhoneAndEmail(int phone, String email, Pageable pageable);

	@Query(value = "from UserData where email like concat( '%', ?1, '%') and userName like concat( '%', ?2, '%')")
	public List<UserData> findAllByEmailAndUserName(String email, String userName, Pageable pageable);

	@Query(value = "from UserData where email like concat( '%', ?2, '%') and userName like concat( '%', ?1, '%')")
	public List<UserData> findAllByUserNameAndEmail(String userName, String email, Pageable pageable);

	@Query(value = "from UserData where cast(phone as string) like concat( '%', ?1, '%') and userName like concat( '%', ?2, '%')")
	public List<UserData> findAllByPhoneAndUserName(int phone, String userName, Pageable pageable);

	@Query(value = "from UserData where cast(phone as string) like concat( '%', ?2, '%') and userName like concat( '%', ?1, '%')")
	public List<UserData> findAllByUserNameAndPhone(String userName, int phone, Pageable pageable);
	
}
