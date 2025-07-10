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
public class CodeSnippet {

    @Id
    private String id;

    @Column(nullable = false)
    private String language;

    @Column(nullable = false,length = 3000)
    private String code;

    @Column(nullable = false,length = 3000)
    private String description;

    private boolean isShared;

    private LocalDateTime createdAt;

    private String shareLink;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private AppUser user;

}
