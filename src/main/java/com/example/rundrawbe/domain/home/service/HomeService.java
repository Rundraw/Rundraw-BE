package com.example.rundrawbe.domain.home.service;

import com.example.rundrawbe.domain.course.entity.Course;
import com.example.rundrawbe.domain.course.repository.CourseRepository;
import com.example.rundrawbe.domain.home.converter.HomeConverter;
import com.example.rundrawbe.domain.home.dto.HomeResDTO;
import com.example.rundrawbe.domain.home.exception.HomeException;
import com.example.rundrawbe.domain.home.exception.code.HomeErrorCode;
import com.example.rundrawbe.domain.member.repository.MemberRepository;
import com.example.rundrawbe.domain.ranking.entity.CourseLike;
import com.example.rundrawbe.domain.ranking.entity.CourseScrap;
import com.example.rundrawbe.domain.ranking.repository.CourseLikeRepository;
import com.example.rundrawbe.domain.ranking.repository.CourseScrapRepository;
import com.example.rundrawbe.domain.record.entity.CourseRecord;
import com.example.rundrawbe.domain.record.repository.CourseRecordRepository;
import com.example.rundrawbe.domain.restaurant.entity.CourseRestaurant;
import com.example.rundrawbe.domain.restaurant.entity.Restaurant;
import com.example.rundrawbe.domain.restaurant.repository.CourseRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 이 서비스는 리포지토리에 기본으로 있는 findAll()만 사용합니다.
 * 필터링 / 정렬 / 개수 세기는 전부 자바 스트림으로 이 안에서 처리해서,
 * 다른 도메인(course/record/ranking/restaurant)의 리포지토리 파일은 하나도 건드리지 않습니다.
 * (데이터가 많아지면 나중에 쿼리 메서드로 최적화하면 됩니다)
 */
@Service
@Transactional
@RequiredArgsConstructor
public class HomeService {

    private final MemberRepository memberRepository;
    private final CourseRepository courseRepository;
    private final CourseRecordRepository courseRecordRepository;
    private final CourseScrapRepository courseScrapRepository;
    private final CourseRestaurantRepository courseRestaurantRepository;
    private final CourseLikeRepository courseLikeRepository;

    /**
     * GET /api/user/me/course - 내 경로 상위 N개 조회
     */
    @Transactional(readOnly = true)
    public List<HomeResDTO.MyCourseDTO> getMyCourses(Long memberId, int size) {
        validateMember(memberId);

        return courseRecordRepository.findAll().stream()
                .filter(record -> record.getMember().getId().equals(memberId))
                .sorted(Comparator.comparing(CourseRecord::getCreatedAt).reversed())
                .limit(size)
                .map(HomeConverter::toMyCourseDTO)
                .toList();
    }

    /**
     * GET /api/user/me/restaurant - 저장한 맛집 상위 N개 조회
     * (스크랩한 코스에 연결된 맛집을 최신 스크랩 순으로 모아서 중복 제거 후 반환)
     */
    @Transactional(readOnly = true)
    public List<HomeResDTO.MyRestaurantDTO> getMySavedRestaurants(Long memberId, int size) {
        validateMember(memberId);

        // 1. 내가 스크랩한 코스 목록 (최신순)
        List<CourseScrap> myScraps = courseScrapRepository.findAll().stream()
                .filter(scrap -> scrap.getMember().getId().equals(memberId))
                .sorted(Comparator.comparing(CourseScrap::getId).reversed())
                .toList();

        // 2. 코스-맛집 연결 테이블 전체를 한 번만 조회해서 재사용
        List<CourseRestaurant> allCourseRestaurants = courseRestaurantRepository.findAll();

        List<HomeResDTO.MyRestaurantDTO> result = new ArrayList<>();
        Set<Long> seenRestaurantIds = new HashSet<>();

        for (CourseScrap scrap : myScraps) {
            if (result.size() >= size) {
                break;
            }
            Long scrapedCourseId = scrap.getCourse().getId();

            List<Restaurant> restaurants = allCourseRestaurants.stream()
                    .filter(cr -> cr.getCourse().getId().equals(scrapedCourseId))
                    .map(CourseRestaurant::getRestaurant)
                    .toList();

            for (Restaurant restaurant : restaurants) {
                if (result.size() >= size) {
                    break;
                }
                if (seenRestaurantIds.add(restaurant.getId())) {
                    result.add(HomeConverter.toMyRestaurantDTO(restaurant));
                }
            }
        }
        return result;
    }

    /**
     * GET /api/gpsart - 인기 GPS 아트 상위 N개 조회 (전체 공개 코스 대상, 로그인 불필요)
     */
    @Transactional(readOnly = true)
    public List<HomeResDTO.GpsArtDTO> getPopularGpsArt(int size) {
        // 좋아요 전체를 한 번만 조회해서 코스별 개수를 세는 데 재사용
        List<CourseLike> allLikes = courseLikeRepository.findAll();

        return courseRepository.findAll().stream()
                .sorted(Comparator.comparing(Course::getExperienceCount).reversed())
                .limit(size)
                .map(course -> {
                    long likeCount = allLikes.stream()
                            .filter(like -> like.getCourse().getId().equals(course.getId()))
                            .count();
                    return HomeConverter.toGpsArtDTO(course, likeCount);
                })
                .toList();
    }

    private void validateMember(Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new HomeException(HomeErrorCode.MEMBER_NOT_FOUND);
        }
    }
}