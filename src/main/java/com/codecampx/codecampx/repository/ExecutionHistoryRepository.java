package com.codecampx.codecampx.repository;

import com.codecampx.codecampx.model.ExecutionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExecutionHistoryRepository extends JpaRepository<ExecutionHistory,String> {
    public List<ExecutionHistory> findByUser_UserName(String userName);
}
