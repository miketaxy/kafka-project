package org.trainingkafka.messenger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.trainingkafka.messenger.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
