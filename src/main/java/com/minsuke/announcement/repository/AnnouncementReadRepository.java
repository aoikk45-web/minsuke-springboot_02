package com.minsuke.announcement.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.minsuke.announcement.entity.AnnouncementRead;

public interface AnnouncementReadRepository extends JpaRepository<AnnouncementRead, Long> {

    boolean existsByAnnouncementIdAndUserId(Long announcementId, Long userId);

    Optional<AnnouncementRead> findByAnnouncementIdAndUserId(Long announcementId, Long userId);

    @Query("select r.announcementId from AnnouncementRead r where r.userId = :userId")
    Set<Long> findReadAnnouncementIdsByUserId(@Param("userId") Long userId);
}
