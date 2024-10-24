package com.hcmute.projectCT.service;

import com.hcmute.projectCT.constant.MessageKey;
import com.hcmute.projectCT.dto.Message.MessageRequest;
import com.hcmute.projectCT.dto.Message.MessageResponse;
import com.hcmute.projectCT.exception.InternalServerException;
import com.hcmute.projectCT.model.Collaborator;
import com.hcmute.projectCT.model.Message;
import com.hcmute.projectCT.model.Project;
import com.hcmute.projectCT.model.User;
import com.hcmute.projectCT.repository.CollaboratorRepository;
import com.hcmute.projectCT.repository.MessageRepository;
import com.hcmute.projectCT.repository.ProjectRepository;
import com.hcmute.projectCT.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional
@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final CollaboratorRepository collaboratorRepository;

    @Override
    public Message sendMessage(MessageRequest request) {
        try {
            Message message = toEntity(request);
            messageRepository.save(message);
            return message;
        } catch (Exception e) {
            log.error("Error occurred during sending message", e);
            throw new InternalServerException(HttpStatus.INTERNAL_SERVER_ERROR.value(), MessageKey.MESSAGE_SENT_ERROR);
        }
    }

    @Override
    public void pinMessage(Long id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new InternalServerException(HttpStatus.NOT_FOUND.value(), MessageKey.MESSAGE_NOT_FOUND));

        try {
            message.setPinned(true);
            messageRepository.save(message);
        } catch (Exception e) {
            log.error("Error occurred while pinning message", e);
            throw new InternalServerException(HttpStatus.INTERNAL_SERVER_ERROR.value(), MessageKey.MESSAGE_PIN_ERROR);
        }
    }

    @Override
    public List<MessageResponse> getMessagesByProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new InternalServerException(HttpStatus.NOT_FOUND.value(), MessageKey.PROJECT_NOT_FOUND));

        try {
            List<Message> messages = messageRepository.findByProject(project);
            return messages.stream()
                    .map(MessageResponse::new)
                    .toList();
        } catch (Exception e) {
            log.error("Error occurred while fetching messages for project ID: {}", projectId, e);
            throw new InternalServerException(HttpStatus.INTERNAL_SERVER_ERROR.value(), MessageKey.SERVER_ERROR);
        }
    }

    @Override
    public List<MessageResponse> searchMessages(String keyword) {
        try {
            List<Message> messages = messageRepository.findByContentContaining(keyword);
            return messages.stream()
                    .map(MessageResponse::new)
                    .toList();
        } catch (Exception e) {
            log.error("Error occurred while searching messages with keyword: {}", keyword, e);
            throw new InternalServerException(HttpStatus.INTERNAL_SERVER_ERROR.value(), MessageKey.SERVER_ERROR);
        }
    }


    @Override
    public Message toEntity(MessageRequest request) {
        User sender = userRepository.findByUsername(request.getSender());
        Optional<Project> projectOpt = projectRepository.findById(request.getProject());
        if (sender == null) {
            throw new InternalServerException(HttpStatus.BAD_REQUEST.value(), MessageKey.USER_NOT_FOUND);
        }
        if (projectOpt.isEmpty()) {
            throw new InternalServerException(HttpStatus.BAD_REQUEST.value(), MessageKey.PROJECT_NOT_FOUND);
        }

        Optional<Collaborator> collaboratorOpt = collaboratorRepository.findByProjectAndUser(projectOpt.get(), sender);
        if (collaboratorOpt.isEmpty()) {
            throw new InternalServerException(HttpStatus.BAD_REQUEST.value(), MessageKey.USER_NOT_FOUND);
        }

        return Message.builder()
                .content(request.getContent())
                .sender(collaboratorOpt.get())
                .project(projectOpt.get())
                .sentTime(LocalDateTime.now())
                .build();
    }
}
