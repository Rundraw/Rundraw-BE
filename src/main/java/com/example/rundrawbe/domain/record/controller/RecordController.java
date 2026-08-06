package com.example.rundrawbe.domain.record.controller;

import com.example.rundrawbe.domain.record.dto.RecordReqDTO;
import com.example.rundrawbe.domain.record.dto.RecordResDTO;
import com.example.rundrawbe.domain.record.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/me/course")
public class RecordController {
    private final RecordService recordService;

    @PostMapping("/record")
    public RecordResDTO.StartResult start(@RequestBody RecordReqDTO.Start request) {
        Long tempMemberId = 4L; // 존재하는 member id로 맞춤
        return recordService.start(request, tempMemberId);
    }

    @PostMapping("/point")
    public void savePoint(@RequestBody RecordReqDTO.SavePoint request) {
        recordService.savePoint(request);
    }

    @PostMapping("/record/{recordId}/pause")
    public void pause(@PathVariable Long recordId, @RequestBody RecordReqDTO.Pause request) {
        recordService.pause(request);
    }

    @PatchMapping("/record/{recordId}/resume")
    public void resume(@PathVariable Long recordId) {
        recordService.resume(recordId);
    }

    @PatchMapping("/record/{recordId}/finish")
    public RecordResDTO.FinishResult finish(@PathVariable Long recordId) {
        return recordService.finish(recordId);
    }
}
