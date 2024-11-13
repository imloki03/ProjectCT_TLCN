package com.hcmute.projectCT.controller;

import com.hcmute.projectCT.constant.MessageKey;
import com.hcmute.projectCT.dto.RespondData;
import com.hcmute.projectCT.dto.User.TagResponse;
import com.hcmute.projectCT.dto.User.UserResponse;
import com.hcmute.projectCT.exception.InternalServerException;
import com.hcmute.projectCT.exception.RegistrationException;
import com.hcmute.projectCT.service.TagService;
import com.hcmute.projectCT.util.MessageUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/tag")
@RequiredArgsConstructor
@Tag(name = "Tag Controller", description = "APIs for tag-related operations.")
public class TagController {
    private final TagService tagService;
    private final MessageUtil messageUtil;

    @Operation(
            summary = "Get tag information",
            description = "This API retrieves all tag.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tag information retrieved successfully."),
                    @ApiResponse(responseCode = "500", description = "Internal server error.")
            }
    )
    @GetMapping("")
    public ResponseEntity<?> getAllTag() {
        try {
            List<TagResponse> tagResponse = tagService.getAllTag();
            var respondData = RespondData.builder()
                    .status(HttpStatus.OK.value())
                    .desc(messageUtil.getMessage(MessageKey.TAG_FETCH_SUCCESS))
                    .data(tagResponse)
                    .build();

            return new ResponseEntity<>(respondData, HttpStatus.OK);
        }
        catch (InternalServerException e) {
            var respondData = RespondData.builder()
                    .status(e.getErrorCode())
                    .desc(messageUtil.getMessage(e.getMessage()))
                    .build();
            return new ResponseEntity<>(respondData, HttpStatus.OK);
        }
    }
}
