package com.hcmute.projectCT.service;

import com.hcmute.projectCT.constant.MessageKey;
import com.hcmute.projectCT.dto.User.TagResponse;
import com.hcmute.projectCT.exception.InternalServerException;
import com.hcmute.projectCT.model.Tag;
import com.hcmute.projectCT.repository.TagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Slf4j
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    @Override
    public List<TagResponse> getAllTag() {
        try {
            List<Tag> tags = tagRepository.findAll();
            return tags.stream().map(TagResponse::new).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error occurred while fetching tags ", e);
            throw new InternalServerException(HttpStatus.INTERNAL_SERVER_ERROR.value(), MessageKey.SERVER_ERROR);
        }
    }
}
