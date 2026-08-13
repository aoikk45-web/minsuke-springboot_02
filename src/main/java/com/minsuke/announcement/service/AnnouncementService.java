package com.minsuke.announcement.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.minsuke.announcement.dto.AnnouncementCardDTO;
import com.minsuke.announcement.dto.AnnouncementDetailDTO;
import com.minsuke.announcement.dto.AnnouncementForm;
import com.minsuke.announcement.entity.Announcement;
import com.minsuke.announcement.entity.AnnouncementRead;
import com.minsuke.announcement.exception.AnnouncementAccessDeniedException;
import com.minsuke.announcement.exception.AnnouncementNotFoundException;
import com.minsuke.announcement.repository.AnnouncementReadRepository;
import com.minsuke.announcement.repository.AnnouncementRepository;
import com.minsuke.auth.domain.Role;
import com.minsuke.auth.security.MinsukeUserDetails;

@Service
public class AnnouncementService {

    private static final ZoneId ZONE = ZoneId.of("Asia/Tokyo");

    private final AnnouncementRepository announcementRepository;
    private final AnnouncementReadRepository announcementReadRepository;

    public AnnouncementService(
            AnnouncementRepository announcementRepository,
            AnnouncementReadRepository announcementReadRepository) {
        this.announcementRepository = announcementRepository;
        this.announcementReadRepository = announcementReadRepository;
    }

    @Transactional(readOnly = true)
    public List<AnnouncementCardDTO> list(MinsukeUserDetails user) {
        requireAuthenticated(user);
        Set<Long> readIds = announcementReadRepository.findReadAnnouncementIdsByUserId(user.getUser().getId());
        return announcementRepository.findAllByOrderByPublishedAtDescIdDesc().stream()
                .map(a -> toCard(a, readIds.contains(a.getId())))
                .toList();
    }

    @Transactional
    public AnnouncementDetailDTO getDetail(Long id, MinsukeUserDetails user) {
        requireAuthenticated(user);
        Announcement announcement = findOrThrow(id);
        markRead(announcement.getId(), user.getUser().getId());
        return toDetail(announcement, true);
    }

    @Transactional(readOnly = true)
    public AnnouncementForm toForm(Long id) {
        return toForm(findOrThrow(id));
    }

    @Transactional
    public Long create(MinsukeUserDetails user, AnnouncementForm form) {
        requireAdmin(user);
        Instant now = Instant.now();
        Announcement announcement = new Announcement();
        applyForm(announcement, form);
        announcement.setCreatedByUserId(user.getUser().getId());
        announcement.setPublishedAt(now);
        announcement.setCreatedAt(now);
        announcement.setUpdatedAt(now);
        return announcementRepository.save(announcement).getId();
    }

    @Transactional
    public void update(MinsukeUserDetails user, Long id, AnnouncementForm form) {
        requireAdmin(user);
        Announcement announcement = findOrThrow(id);
        applyForm(announcement, form);
        announcement.setUpdatedAt(Instant.now());
        announcementRepository.save(announcement);
    }

    @Transactional
    public void delete(MinsukeUserDetails user, Long id) {
        requireAdmin(user);
        Announcement announcement = findOrThrow(id);
        announcementRepository.delete(announcement);
    }

    @Transactional(readOnly = true)
    public long countUnread(MinsukeUserDetails user) {
        if (user == null) {
            return 0L;
        }
        return announcementRepository.countUnreadByUserId(user.getUser().getId());
    }

    private void markRead(Long announcementId, Long userId) {
        if (announcementReadRepository.existsByAnnouncementIdAndUserId(announcementId, userId)) {
            return;
        }
        AnnouncementRead read = new AnnouncementRead();
        read.setAnnouncementId(announcementId);
        read.setUserId(userId);
        read.setReadAt(Instant.now());
        announcementReadRepository.save(read);
    }

    private Announcement findOrThrow(Long id) {
        return announcementRepository.findById(id).orElseThrow(AnnouncementNotFoundException::new);
    }

    private void requireAuthenticated(MinsukeUserDetails user) {
        if (user == null) {
            throw new AnnouncementAccessDeniedException();
        }
    }

    private void requireAdmin(MinsukeUserDetails user) {
        if (user == null || user.getUser().getRole() != Role.ADMIN) {
            throw new AnnouncementAccessDeniedException();
        }
    }

    private void applyForm(Announcement announcement, AnnouncementForm form) {
        announcement.setTitle(form.getTitle().trim());
        announcement.setBody(form.getBody().trim());
    }

    private AnnouncementForm toForm(Announcement announcement) {
        AnnouncementForm form = new AnnouncementForm();
        form.setTitle(announcement.getTitle());
        form.setBody(announcement.getBody());
        return form;
    }

    private AnnouncementCardDTO toCard(Announcement announcement, boolean read) {
        AnnouncementCardDTO dto = new AnnouncementCardDTO();
        dto.setId(announcement.getId());
        dto.setTitle(announcement.getTitle());
        dto.setPublishedAt(LocalDateTime.ofInstant(announcement.getPublishedAt(), ZONE));
        dto.setRead(read);
        return dto;
    }

    private AnnouncementDetailDTO toDetail(Announcement announcement, boolean read) {
        AnnouncementDetailDTO dto = new AnnouncementDetailDTO();
        dto.setId(announcement.getId());
        dto.setTitle(announcement.getTitle());
        dto.setBody(announcement.getBody());
        dto.setPublishedAt(LocalDateTime.ofInstant(announcement.getPublishedAt(), ZONE));
        dto.setRead(read);
        return dto;
    }
}
