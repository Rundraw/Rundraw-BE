package com.example.rundrawbe.domain.ranking.service;

import com.example.rundrawbe.domain.course.entity.Course;
import com.example.rundrawbe.domain.course.enums.LevelType;
import com.example.rundrawbe.domain.course.repository.CourseRepository;
import com.example.rundrawbe.domain.member.entity.Member;
import com.example.rundrawbe.domain.ranking.component.CourseFinder;
import com.example.rundrawbe.domain.ranking.converter.RankingConverter;
import com.example.rundrawbe.domain.ranking.dto.RankingReqDTO;
import com.example.rundrawbe.domain.ranking.dto.RankingResDTO;
import com.example.rundrawbe.domain.ranking.entity.Comment;
import com.example.rundrawbe.domain.ranking.entity.CourseLike;
import com.example.rundrawbe.domain.ranking.entity.CourseScrap;
import com.example.rundrawbe.domain.ranking.exception.RankingException;
import com.example.rundrawbe.domain.ranking.exception.code.RankingErrorCode;
import com.example.rundrawbe.domain.ranking.repository.CommentRepository;
import com.example.rundrawbe.domain.ranking.repository.CourseLikeRepository;
import com.example.rundrawbe.domain.ranking.repository.CourseScrapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.awt.*;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RankingService {

    private final CourseRepository courseRepository;
    private final CommentRepository commentRepository;
    private final CourseFinder courseFinder;
    private final CourseLikeRepository courseLikeRepository;
    private final CourseScrapRepository courseScrapRepository;

    // 댓글 작성
    public Long createComment(Long courseId, RankingReqDTO.CreateComment dto, Member member) {
        // 코스 조회
        Course course = courseFinder.findById(courseId);
        // 댓글 생성
        Comment comment = RankingConverter.toCreateComment(dto, course, member);
        commentRepository.save(comment);
        return comment.getId();
    }

    public Object updateComment(Long courseId, Long commentId, RankingReqDTO.UpdateComment dto, Member member) {
        // 코스 조회
        Course course = courseFinder.findById(courseId);
        // 댓글 조회
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RankingException(RankingErrorCode.COMMENT_NOT_FOUND));
        // 수정 권한 검토
        if (!commentRepository.existsByIdAndMember_Id(commentId, member.getId())) {
            throw new RankingException(RankingErrorCode.COMMENT_ACCESS_DENIED);
        }
        // 댓글 수정
        comment.updateComment(dto.comment());
        return null;
    }

    // 댓글 삭제
    public Object deleteComment(Long courseId, Long commentId, Member member) {
        // 코스 조회
        Course course = courseFinder.findById(courseId);
        // 댓글 조회
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RankingException(RankingErrorCode.COMMENT_NOT_FOUND));
        if (!commentRepository.existsByIdAndCourse_Id(commentId, course.getId())) {
            throw new RankingException(RankingErrorCode.COMMENT_NOT_FOUND);
        }
        if (comment.getDeletedAt() != null) {
            throw new RankingException(RankingErrorCode.COMMENT_ALREADY_DELETED);
        }
        // 수정 권한 검토
        if (!commentRepository.existsByIdAndMember_Id(commentId, member.getId())) {
            throw new RankingException(RankingErrorCode.COMMENT_ACCESS_DENIED);
        }
        // 댓글 삭제
        comment.deleteComment();
        return null;
    }


    // 댓글 조회
    public RankingResDTO.Pagination<RankingResDTO.GetComment> getComment(
            Long courseId, Integer pageSize, String cursor, String query, Member member
    ) {
        // 페이지 정보 생성
        PageRequest pageRequest = PageRequest.of(0, pageSize);
        long idCursor;
        Slice<Comment> commentList;

        // 첫 페이지 조회
        if (cursor == null||"-1".equals(cursor)) {
            commentList = commentRepository.findByCourse_IdAndDeletedAtIsNullOrderByIdDesc(courseId, pageRequest);
        } else {
            // 커서 분리
            String[] cursorSplit = cursor.split(":");
            switch (query.toLowerCase()) {
                case "id":
                    idCursor = Long.parseLong(cursorSplit[1]);
                    commentList = commentRepository.findByCourse_IdAndDeletedAtIsNullAndIdLessThanOrderByIdDesc(courseId, idCursor, pageRequest);
                    break;
                default:
                    throw new RankingException(RankingErrorCode.QUERY_NOT_VALID);
            }
        }

        // 다음 커서 계산
        String nextCursor = null;
        if (!commentList.isEmpty() && commentList.hasNext()) {
            nextCursor = "id:" + commentList.getContent().getLast().getId();
        }

        // 응답 DTO 반환
        return RankingConverter.toPagination(commentList.stream()
                        .map(comment -> RankingConverter.toGetComment(comment, member))
                        .toList(),
                commentList.hasNext(),
                nextCursor,
                commentList.getNumberOfElements()
        );
    }

    // 좋아요 생성
    public Object createLike(Long courseId, Member member) {
        Course course = courseFinder.findById(courseId);
        // 중복 방지
        if (courseLikeRepository.existsByCourse_IdAndMember_Id(course.getId(), member.getId())) {
            throw new RankingException(RankingErrorCode.LIKE_ALREADY_CREATED);
        }
        // 좋아요 생성
        CourseLike courseLike = RankingConverter.toCreateLike(course, member);
        courseLikeRepository.save(courseLike);
        return courseLike.getId();
    }

    // 좋아요 삭제
    public Object deleteLike(Long courseId, Member member) {
        Course course = courseFinder.findById(courseId);
        CourseLike courseLike = courseLikeRepository.findByCourseAndMember(course, member)
                .orElseThrow(() -> new RankingException(RankingErrorCode.LIKE_NOT_FOUND));
        courseLikeRepository.delete(courseLike);
        return null;
    }

    // 북마크 생성
    public Object createBookmark(Long courseId, Member member) {
        Course course = courseFinder.findById(courseId);
        // 중복 방지
        if (courseScrapRepository.existsByCourse_IdAndMember_Id(course.getId(), member.getId())) {
            throw new RankingException(RankingErrorCode.BOOKMARK_ALREADY_CREATED);
        }
        // 북마크 생성
        CourseScrap courseScrap = RankingConverter.toCreateScrap(course, member);
        courseScrapRepository.save(courseScrap);
        return courseScrap.getId();
    }

    // 북마크 삭제
    public Object deleteBookmark(Long courseId, Member member) {
        Course course = courseFinder.findById(courseId);
        CourseScrap courseScrap = courseScrapRepository.findByCourseAndMember(course, member)
                .orElseThrow(() -> new RankingException(RankingErrorCode.BOOKMARK_NOT_FOUND));
        courseScrapRepository.delete(courseScrap);
        return null;
    }

    // 코스 랭킹순 조회
    public RankingResDTO.Pagination<RankingResDTO.GetRanking> getRanking(
            Integer pageSize,
            String cursor
    ) {
        PageRequest pageRequest = PageRequest.of(0, pageSize);
        Slice<Course> courseList;
        // 첫 페이지 조회
        if (cursor == null || "-1".equals(cursor)) {
            courseList = courseRepository.findAllByOrderByExperienceCountDescIdDesc(pageRequest);
        } else {
            String[] cursorSplit = cursor.split(":");
            Long idCursor = Long.parseLong(cursorSplit[1]);
            courseList = courseRepository.findByIdLessThanOrderByExperienceCountDescIdDesc(idCursor, pageRequest);
        }

        String nextCursor = null;

        if (!courseList.isEmpty() && courseList.hasNext()) {
            nextCursor = "id:" + courseList.getContent().getLast().getId();
        }

        return RankingConverter.toPagination(
                courseList
                        .map(RankingConverter::toGetRanking)
                        .toList(),
                courseList.hasNext(),
                nextCursor,
                courseList.getNumberOfElements()
        );
    }

    // 코스 난이도 조회
    public RankingResDTO.Pagination<RankingResDTO.GetRanking> getLevelCourses(
            String level,
            Integer pageSize,
            String cursor
    ) {
        PageRequest pageRequest = PageRequest.of(0, pageSize);
        Slice<Course> courseList;
        Long idCursor = null;

        // 커서 처리
        if (cursor != null && !"-1".equals(cursor)) {
            String[] cursorSplit = cursor.split(":");
            idCursor = Long.parseLong(cursorSplit[1]);
        }

        // 난이도 미선택 -> 전체 조회
        if (level == null || level.isBlank()) {
            if (idCursor == null) {
                courseList = courseRepository.findAllByOrderByIdDesc(pageRequest);
            } else {
                courseList = courseRepository.findByIdLessThanOrderByIdDesc(idCursor, pageRequest);
            }
        } else {
            LevelType levelType;
            try {
                levelType = LevelType.valueOf(level.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new RankingException(RankingErrorCode.LEVEL_NOT_VALID);
            }
            if (idCursor == null) {
                courseList = courseRepository.findByLevelTag_LevelTypeOrderByIdDesc(levelType, pageRequest);
            } else {
                courseList = courseRepository.findByLevelTag_LevelTypeAndIdLessThanOrderByIdDesc(levelType, idCursor, pageRequest);
            }
        }
        String nextCursor = null;
        if (!courseList.isEmpty() && courseList.hasNext()) {
            nextCursor = "id:" + courseList.getContent().getLast().getId();
        }

        return RankingConverter.toPagination(
                courseList
                        .map(RankingConverter::toGetRanking)
                        .toList(),
                courseList.hasNext(),
                nextCursor,
                courseList.getNumberOfElements()
        );
    }


    // gps art 조회
    @Transactional(readOnly = true)
    public RankingResDTO.Pagination<RankingResDTO.GetGpsArt> getGpsArt(
            Integer pageSize,
            String cursor
    ) {
        PageRequest pageRequest = PageRequest.of(0, pageSize);

        // 좋아요 순 조회
        Slice<Course> courseList =
                courseRepository.findAllOrderByLikeCount(pageRequest);
        String nextCursor = null;
        if (!courseList.isEmpty() && courseList.hasNext()) {
            Course lastCourse = courseList.getContent().getLast();
            Integer likeCount = courseLikeRepository.countByCourse_Id(lastCourse.getId());
            nextCursor = likeCount + ":" + lastCourse.getId();
        }
        List<RankingResDTO.GetGpsArt> result =
                courseList.getContent()
                        .stream()
                        .map(course -> {
                            Integer likeCount = courseLikeRepository.countByCourse_Id(course.getId());
                            return RankingConverter.toGetGpsArt(
                                    course,
                                    likeCount
                            );
                        })
                        .toList();

        return RankingConverter.toPagination(
                result,
                courseList.hasNext(),
                nextCursor,
                courseList.getNumberOfElements()
        );
    }

    // 코스 상세 조회
    @Transactional(readOnly = true)
    public RankingResDTO.GetCourseDetail getCourseDetail(
            Integer courseId,
            Member authMember
    ) {
        Course course = courseRepository.findById(courseId.longValue())
                .orElseThrow(() -> new RankingException(RankingErrorCode.COURSE_NOT_FOUND));

        boolean isLike = courseLikeRepository.existsByCourseIdAndMemberId(course.getId(), authMember.getId());
        boolean isBookmark = courseScrapRepository.existsByCourseIdAndMemberId(course.getId(), authMember.getId());
        Integer commentCount = commentRepository.countByCourseId(courseId);
        Integer likeCount = courseLikeRepository.countByCourseId(courseId);
        Integer bookmarkCount = courseScrapRepository.countByCourseId(courseId);

        List<RankingResDTO.Point> points = course.getCourseDraft()
                .getPoints()
                .stream()
                .map(point -> RankingResDTO.Point.builder()
                                .latitude(point.getLatitude())
                                .longitude(point.getLongitude())
                                .build()
                )
                .toList();
        return RankingConverter.toGetCourseDetail(course, isLike, isBookmark, points, commentCount, likeCount, bookmarkCount);
    }
}
