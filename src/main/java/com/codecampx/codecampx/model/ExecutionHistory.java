package com.codecampx.codecampx.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExecutionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private  String language;

    @Column(nullable = false,length = 3000)
    private String code;

    private String output;

    private boolean status;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private AppUser user;
}
