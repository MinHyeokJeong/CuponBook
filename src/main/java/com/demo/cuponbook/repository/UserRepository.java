package com.demo.cuponbook.repository;

import com.demo.cuponbook.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // < db에 들어갈 객체 형식, pk 데이터 타입>
public interface UserRepository extends JpaRepository<UserEntity, String> {
}
