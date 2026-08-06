package com.example.rundrawbe.domain.mypage.service;

import com.example.rundrawbe.domain.course.entity.CourseDraft;
import com.example.rundrawbe.domain.course.entity.DraftPoint;
import com.example.rundrawbe.domain.course.repository.CourseDraftRepository;
import com.example.rundrawbe.domain.course.repository.DraftPointRepository;
import com.example.rundrawbe.domain.mypage.exception.MypageException;
import com.example.rundrawbe.domain.mypage.exception.code.MypageErrorCode;
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

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MypageService {

    private final CommentRepository commentRepository;
    private final CourseScrapRepository courseScrapRepository;
    private final CourseRecordRepository courseRecordRepository;
    private final CourseDraftRepository courseDraftRepository;
    private final DraftPointRepository draftPointRepository;

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

    private void validateOwner(CourseDraft draft, Long memberId) {
        if (!draft.getMember().getId().equals(memberId)) {
            throw new MypageException(MypageErrorCode.DRAFT_COURSE_ACCESS_DENIED);
        }
    }
}