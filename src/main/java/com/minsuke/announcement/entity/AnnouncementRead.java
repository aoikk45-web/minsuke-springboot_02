package com.minsuke.announcement.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "announcement_reads",
        uniqueConstraints = @UniqueConstraint(
                name = "uq_announcement_reads_announcement_user",
                columnNames = {"announcement_id", "user_id"}))
@Getter
@Setter
@NoArgsConstructor
public class AnnouncementRead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "announcement_id", nullable = false)
    private Long announcementId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "read_at", nullable = false)
    private Instant readAt;
}
