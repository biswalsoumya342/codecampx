package com.codecampx.codecampx.repository;

import com.codecampx.codecampx.model.ExecutionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExecutionHistoryRepository extends JpaRepository<ExecutionHistory,String> {
}
