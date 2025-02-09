package com.eaglevision.Backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eaglevision.Backend.model.Subscription;
import com.eaglevision.Backend.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUserName(String userName);

    Boolean existsByUserName(String userName);

    Boolean existsByPhoneNumber(String phoneNumber);

    @Query(value="SELECT * from subscription s LEFT JOIN users u on s.user_id=u.user_id WHERE s.subscription_end_date_time >= NOW()",nativeQuery = true)
	List<Subscription> findActiveSubscriptions(Integer userId);
}