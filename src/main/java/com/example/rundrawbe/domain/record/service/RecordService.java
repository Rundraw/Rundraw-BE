package com.example.rundrawbe.domain.record.service;

import com.example.rundrawbe.domain.record.dto.RecordReqDTO;
import com.example.rundrawbe.domain.record.dto.RecordResDTO;
import com.example.rundrawbe.domain.record.entity.CourseRecord;
import com.example.rundrawbe.domain.record.entity.CourseRecordPoint;
import com.example.rundrawbe.domain.record.entity.RecordPause;
import com.example.rundrawbe.domain.record.repository.CourseRecordRepository;
import com.example.rundrawbe.domain.record.repository.RecordPauseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RecordService {

    private final CourseRecordRepository courseRecordRepository;
    private final RecordPauseRepository recordPauseRepository;

    // 1. 기록 시작
    public RecordResDTO.StartResult start(RecordReqDTO.Start request, Long memberId) {
        CourseRecord record = CourseRecord.builder()
                .startAt(LocalDateTime.now())
                .isCompleted(false)
                // .member(...), .courseDraft(...) 는 실제 조회 후 채워야 함
                .build();

        CourseRecord saved = courseRecordRepository.save(record);
        return new RecordResDTO.StartResult(saved.getId(), saved.getStartAt());
    }

    // 2. 실시간 위치 저장
    public void savePoint(RecordReqDTO.SavePoint request) {
        CourseRecord record = courseRecordRepository.findById(request.recordId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 기록입니다."));

        CourseRecordPoint point = new CourseRecordPoint();
        point.setSequence(request.sequence());
        point.setLatitude(request.latitude());
        point.setLongitude(request.longitude());
        point.setRecordedAt(LocalDateTime.now());

        record.addPoint(point);
        courseRecordRepository.save(record);
    }

    // 3. 일시정지
    public void pause(RecordReqDTO.Pause request) {
        CourseRecord record = courseRecordRepository.findById(request.recordId())
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 기록입니다."));

        RecordPause pause = new RecordPause();
        pause.setCourseRecord(record);
        pause.setStartAt(LocalDateTime.now());
        pause.setLatitude(request.latitude());
        pause.setLongitude(request.longitude());

        recordPauseRepository.save(pause);
    }

    // 4. 재개
    public void resume(Long recordId) {
        RecordPause pause = recordPauseRepository.findFirstByCourseRecordIdAndEndAtIsNull(recordId)
                .orElseThrow(() -> new IllegalArgumentException("진행 중인 일시정지가 없습니다."));

        pause.setEndAt(LocalDateTime.now());
        recordPauseRepository.save(pause);
    }

    // 5. 종료
    public RecordResDTO.FinishResult finish(Long recordId) {
        CourseRecord record = courseRecordRepository.findById(recordId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 기록입니다."));

        LocalDateTime endAt = LocalDateTime.now();
        record.setEndAt(endAt);

        long totalSec = Duration.between(record.getStartAt(), endAt).getSeconds();
        long pausedSec = record.getPauses().stream()
                .filter(p -> p.getEndAt() != null)
                .mapToLong(p -> Duration.between(p.getStartAt(), p.getEndAt()).getSeconds())
                .sum();
        long pureDurationSec = totalSec - pausedSec;

        double distanceKm = calculateTotalDistance(record.getPoints()); // 하단 헬퍼 함수
        record.setDistanceKm(distanceKm);

        boolean isCompleted = true; // TODO: 완주율 계산 로직 연결
        record.setIsCompleted(isCompleted);

        courseRecordRepository.save(record);

        List<RecordResDTO.PointDTO> pointDTOS = record.getPoints().stream()
                .map(p -> new RecordResDTO.PointDTO(p.getSequence(), p.getLatitude(), p.getLongitude(), p.getRecordedAt()))
                .collect(Collectors.toList());

        return new RecordResDTO.FinishResult(
                record.getId(), isCompleted, record.getDistanceKm(), pureDurationSec, pointDTOS
        );
    }

    // 헬퍼 함수
    private double calculateTotalDistance(List<CourseRecordPoint> points) {
        double totalMeters = 0;
        for (int i = 1; i < points.size(); i++) {
            totalMeters += haversine(
                    points.get(i - 1).getLatitude(), points.get(i - 1).getLongitude(),
                    points.get(i).getLatitude(), points.get(i).getLongitude()
            );
        }
        return totalMeters / 1000;
    }

    private double haversine(double lat1, double lng1, double lat2, double lng2) {
        double R = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c * 1000; // meter 단위
    }
}
