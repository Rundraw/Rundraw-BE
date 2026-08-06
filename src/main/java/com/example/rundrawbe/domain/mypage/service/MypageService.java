package com.example.rundrawbe.domain.mypage.service;

import com.example.rundrawbe.domain.course.entity.CourseDraft;
import com.example.rundrawbe.domain.course.entity.DraftPoint;
import com.example.rundrawbe.domain.course.repository.CourseDraftRepository;
import com.example.rundrawbe.domain.course.repository.DraftPointRepository;
import com.example.rundrawbe.domain.mypage.converter.MypageConverter;
import com.example.rundrawbe.domain.mypage.dto.MypageResDTO;
import com.example.rundrawbe.domain.mypage.exception.MypageException;
import com.example.rundrawbe.domain.mypage.exception.code.MypageErrorCode;
import com.example.rundrawbe.domain.course.entity.Course;
import com.example.rundrawbe.domain.mypage.repository.MypageCourseLookupRepository;
import com.example.rundrawbe.domain.mypage.repository.MypageDraftShareRepository;
import com.example.rundrawbe.domain.ranking.entity.Comment;
import com.example.rundrawbe.domain.ranking.entity.CourseScrap;
import com.example.rundrawbe.domain.ranking.repository.CommentRepository;
import com.example.rundrawbe.domain.ranking.repository.CourseScrapRepository;
import com.example.rundrawbe.domain.record.entity.CourseRecord;
import com.example.rundrawbe.domain.record.repository.CourseRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MypageService {

    private final CommentRepository commentRepository;
    private final CourseScrapRepository courseScrapRepository;
    private final CourseRecordRepository courseRecordRepository;
    private final CourseDraftRepository courseDraftRepository;
    private final DraftPointRepository draftPointRepository;
    private final MypageDraftShareRepository mypageDraftShareRepository; // ★ 추가
    private final MypageCourseLookupRepository mypageCourseLookupRepository; // ★ 추가

    public List<Comment> getMyComments(Long memberId) {
        return commentRepository.findByMember_IdAndDeletedAtIsNullOrderByCreatedAtDesc(memberId);
    }

    public List<CourseScrap> getMyScrapCourses(Long memberId) {
        return courseScrapRepository.findByMember_Id(memberId);
    }

    public List<CourseRecord> getMyCourseRecords(Long memberId, boolean completedOnly) {
        if (completedOnly) {
            return courseRecordRepository.findByMember_IdAndIsCompletedTrue(memberId);
        }
        return courseRecordRepository.findByMember_Id(memberId);
    }

    public List<CourseDraft> getMyDraftCourses(Long memberId) {
        return courseDraftRepository.findByMember_Id(memberId);
    }

    // ★ 추가: 완주 여부(isCompleted) + 승격된 courseId까지 포함한 내가 그린 코스 목록 DTO 조회
    public MypageResDTO.DraftCourseListDTO getMyDraftCoursesWithStatus(Long memberId) {
        List<CourseDraft> drafts = courseDraftRepository.findByMember_Id(memberId);

        Set<Long> completedDraftIds = courseRecordRepository
                .findByMember_IdAndIsCompletedTrue(memberId)
                .stream()
                .map(record -> record.getCourseDraft().getId())
                .collect(Collectors.toSet());

        List<Long> draftIds = drafts.stream().map(CourseDraft::getId).collect(Collectors.toList());
        Map<Long, Long> draftIdToCourseIdMap = mypageCourseLookupRepository
                .findByCourseDraft_IdIn(draftIds)
                .stream()
                .collect(Collectors.toMap(
                        course -> course.getCourseDraft().getId(),
                        Course::getId
                ));

        return MypageConverter.toDraftCourseListDTO(drafts, completedDraftIds, draftIdToCourseIdMap);
    }

    public CourseDraft getMyDraftCourseDetail(Long memberId, Long draftCourseId) {
        CourseDraft draft = courseDraftRepository.findById(draftCourseId)
                .orElseThrow(() -> new MypageException(MypageErrorCode.DRAFT_COURSE_NOT_FOUND));
        validateOwner(draft, memberId);
        return draft;
    }

    public List<DraftPoint> getDraftPoints(Long draftCourseId) {
        return draftPointRepository.findByCourseDraft_IdOrderBySequenceAsc(draftCourseId);
    }

    @Transactional
    public CourseDraft updateDraftCourseName(Long memberId, Long draftCourseId, String name) {
        CourseDraft draft = courseDraftRepository.findById(draftCourseId)
                .orElseThrow(() -> new MypageException(MypageErrorCode.DRAFT_COURSE_NOT_FOUND));
        validateOwner(draft, memberId);
        draft.updateName(name);
        return draft;
    }

    @Transactional
    public void deleteDraftCourse(Long memberId, Long draftCourseId) {
        CourseDraft draft = courseDraftRepository.findById(draftCourseId)
                .orElseThrow(() -> new MypageException(MypageErrorCode.DRAFT_COURSE_NOT_FOUND));
        validateOwner(draft, memberId);
        courseDraftRepository.delete(draft);
    }

    // ★ 추가: 그린 코스 공유 상태 토글 (완주 기록 있어야만 허용)
    @Transactional
    public boolean toggleDraftSharing(Long memberId, Long draftCourseId) {
        CourseDraft draft = courseDraftRepository.findById(draftCourseId)
                .orElseThrow(() -> new MypageException(MypageErrorCode.DRAFT_COURSE_NOT_FOUND));
        validateOwner(draft, memberId);

        boolean hasCompletedRecord = courseRecordRepository
                .existsByMember_IdAndCourseDraft_IdAndIsCompletedTrue(memberId, draftCourseId);
        if (!hasCompletedRecord) {
            throw new MypageException(MypageErrorCode.DRAFT_COURSE_NOT_COMPLETED);
        }

        boolean newSharingStatus = !Boolean.TRUE.equals(draft.getIsSharing());
        mypageDraftShareRepository.updateSharingStatus(draftCourseId, memberId, newSharingStatus);
        return newSharingStatus;
    }

    private void validateOwner(CourseDraft draft, Long memberId) {
        if (!draft.getMember().getId().equals(memberId)) {
            throw new MypageException(MypageErrorCode.DRAFT_COURSE_ACCESS_DENIED);
        }
    }
}