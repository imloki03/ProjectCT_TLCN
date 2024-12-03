package com.hcmute.projectCT.service;

import com.hcmute.projectCT.constant.MessageKey;
import com.hcmute.projectCT.dto.Message.MessageRequest;
import com.hcmute.projectCT.dto.Message.MessageResponse;
import com.hcmute.projectCT.dto.Message.PinMessageRequest;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void sendMessage(MessageRequest request) {
        try {
            Message message = toEntity(request);
            messageRepository.save(message);
            MessageResponse messageResponse = new MessageResponse(message);
            messagingTemplate.convertAndSend("/public/project/" + request.getProject(), messageResponse);
        } catch (Exception e) {
            log.error("Error occurred during sending message", e);
            throw new InternalServerException(HttpStatus.INTERNAL_SERVER_ERROR.value(), MessageKey.MESSAGE_SENT_ERROR);
        }
    }

    @Override
    public void pinMessage(PinMessageRequest request) {
        Message message = messageRepository.findById(request.getPinMessageId())
                .orElseThrow(() -> new InternalServerException(HttpStatus.NOT_FOUND.value(), MessageKey.MESSAGE_NOT_FOUND));
        try {
            message.setPinned(!message.isPinned());
            messageRepository.save(message);
            MessageResponse messageResponse = new MessageResponse(message);
            messagingTemplate.convertAndSend("/public/project/" + request.getProject(), messageResponse);
        } catch (Exception e) {
            log.error("Error occurred while pinning message", e);
            throw new InternalServerException(HttpStatus.INTERNAL_SERVER_ERROR.value(), MessageKey.MESSAGE_PIN_ERROR);
        }
    }


    @Override
    public Page<MessageResponse> getMessagesByProject(Long projectId, int page, int size) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new InternalServerException(HttpStatus.NOT_FOUND.value(), MessageKey.PROJECT_NOT_FOUND));

        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "sentTime"));
            Page<Message> messages = messageRepository.findAllByProject(project, pageable);
            return messages.map(MessageResponse::new); // Convert Page<Message> to Page<MessageResponse>
        } catch (Exception e) {
            log.error("Error occurred while fetching messages for project ID: {}", projectId, e);
            throw new InternalServerException(HttpStatus.INTERNAL_SERVER_ERROR.value(), MessageKey.SERVER_ERROR);
        }
    }

    @Override
    public List<MessageResponse> getPinnedMessagesByProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new InternalServerException(HttpStatus.NOT_FOUND.value(), MessageKey.PROJECT_NOT_FOUND));
        try {
            List<Message> messages = messageRepository.findByProjectAndIsPinned(project, true);
            return messages.stream()
                    .map(MessageResponse::new)
                    .toList();
        } catch (Exception e) {
            log.error("Error occurred while fetching messages for project ID: {}", projectId, e);
            throw new InternalServerException(HttpStatus.INTERNAL_SERVER_ERROR.value(), MessageKey.SERVER_ERROR);
        }
    }

    @Override
    public List<MessageResponse> searchMessages(Long projectId, String keyword) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new InternalServerException(HttpStatus.NOT_FOUND.value(), MessageKey.PROJECT_NOT_FOUND));
        try {
            List<Message> messages = messageRepository.findByProjectAndContentContaining(project, keyword);
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
        //use helper
        String checked = validateMessageRequest(request);
        if (checked == null) {
            log.error("checked");
        }
        log.error(request.getSender());
        User sender = userRepository.findByUsername(request.getSender());
        log.error(sender.getId().toString());
        Optional<Project> projectOpt = projectRepository.findById(request.getProject());
        if (sender == null) {
            throw new InternalServerException(HttpStatus.BAD_REQUEST.value(), MessageKey.USER_NOT_FOUND);
        }
        if (projectOpt.isEmpty()) {
            throw new InternalServerException(HttpStatus.BAD_REQUEST.value(), MessageKey.PROJECT_NOT_FOUND);
        }

        Optional<Collaborator> collaboratorOpt = collaboratorRepository.findByProjectAndUser(projectOpt.get(), sender);
        if (collaboratorOpt.isEmpty()) {
            throw new InternalServerException(HttpStatus.BAD_REQUEST.value(), MessageKey.COLLABORATOR_NOT_FOUND);
        }

        return Message.builder()
                .content(request.getContent())
                .sender(collaboratorOpt.get())
                .project(projectOpt.get())
                .sentTime(LocalDateTime.now())
                .build();
    }

    public String validateMessageRequest(MessageRequest request) {
        User sender = userRepository.findByUsername(request.getSender());
        Optional<Project> projectOpt = projectRepository.findById(request.getProject());
        Project project = projectOpt.get();
        List<Collaborator> collaborators = collaboratorRepository.findByProject_Id(project.getId());
        boolean isSenderCollaborator = false;
        for (Collaborator collaborator : collaborators) {
            if (collaborator.getUser().equals(sender)) {
                isSenderCollaborator = true;
                break;
            }
        }
        if (!isSenderCollaborator) {
            return "Collaborator not found for project ID=" + request.getProject() + " and user=" + request.getSender();
        }

        if (request.getContent() == null || request.getContent().isBlank()) {
            return "Message content is invalid: '" + request.getContent() + "'";
        }

        return "Message request is valid.";
    }
}
