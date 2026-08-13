package com.minsuke.announcement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.minsuke.announcement.entity.Announcement;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    List<Announcement> findAllByOrderByPublishedAtDescIdDesc();

    @Query("""
            select count(a) from Announcement a
            where not exists (
                select 1 from AnnouncementRead r
                where r.announcementId = a.id and r.userId = :userId
            )
            """)
    long countUnreadByUserId(@Param("userId") Long userId);
}
