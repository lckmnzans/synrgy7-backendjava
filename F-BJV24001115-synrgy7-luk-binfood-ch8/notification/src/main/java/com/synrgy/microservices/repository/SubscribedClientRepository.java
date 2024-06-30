package com.synrgy.microservices.repository;

import com.synrgy.microservices.model.SubscribedClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SubscribedClientRepository extends JpaRepository<SubscribedClient, UUID> {
}
