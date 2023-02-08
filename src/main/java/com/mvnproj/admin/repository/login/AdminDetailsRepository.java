package com.mvnproj.admin.repository.login;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mvnproj.admin.entity.login.TblAdminDetails;

@Repository
@Transactional
public interface AdminDetailsRepository extends JpaRepository<TblAdminDetails, Long>{
	
	public TblAdminDetails findByAdminName(String name);
	
	public TblAdminDetails findByAdminNameAndAdminPassword(String adminName,String adminPassword);

	@Modifying(clearAutomatically = true)
	@Query(value = "ALTER TABLE tbl_admin_login_details AUTO_INCREMENT = 1", nativeQuery = true)
    void truncateTable();
}
