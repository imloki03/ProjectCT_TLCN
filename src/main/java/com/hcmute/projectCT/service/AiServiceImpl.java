package com.hcmute.projectCT.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmute.projectCT.dto.AI.AssignTaskResponse;
import com.hcmute.projectCT.dto.Collaborator.CollaboratorResponse;
import com.hcmute.projectCT.dto.Task.TaskResponse;
import com.hcmute.projectCT.dto.User.TagResponse;
import com.hcmute.projectCT.model.Project;
import com.hcmute.projectCT.model.Task;
import com.hcmute.projectCT.model.User;
import com.hcmute.projectCT.repository.TaskRepository;
import com.hcmute.projectCT.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiServiceImpl implements AiService{
    private final UserRepository userRepository;
    final TaskRepository taskRepository;
    final GeminiService geminiService;
    final CollaboratorService collaboratorService;
    @Override
    public AssignTaskResponse recommendTaskAssignee(Long id) throws JsonProcessingException {
        Task task = taskRepository.findById(id).orElse(null);
        if (task == null) {
            new AssignTaskResponse();
        }
        Project project = task.getPhase() != null ? task.getPhase().getProject() : task.getBacklog().getProject();
        List<CollaboratorResponse> collaboratorList = collaboratorService.getAllCollaborators(project.getId());

        String conditionTemplate = "Thời gian hiện tại là : %s\n" +
                "Hãy chọn ra một user danh sách dưới đây để đảm nhiệm cho một task dựa vào thông tin tôi cung cấp phía dưới. User được lựa chọn phải phù hợp nhất với tất cả các tiêu chí lựa chọn như sau:\n" +
                "-Năng lực của user phù hợp với nội dung của task.\n" +
                "-Tránh phân công cho người có nhiều task quá hạn.\n" +
                "-Khối lượng công việc giữa các user không quá chênh lệch.\n" +
                "-Tại cùng một thời điểm user không cùng thực hiện quá nhiều task.\n";
        String condition = String.format(conditionTemplate, LocalDateTime.now().toString());

        String respondStruct = "Hãy trả về kết quả bằng tiếng Anh ở dạng json với cấu trúc bao gồm username là username của user mà bạn chọn, reason là tóm tắt lý do mà bạn lựa chọn user đó, kết quả trả về cho tôi bắt đầu bằng dấu { và kết thúc bằng dấu }:\n" ;

        String taskInfoTemplate = "Đây là thông tin cần thiết cho bạn:\n" +
                "-Thông tin về task cần được phân công:\n" +
                "+Tóm tắt: %s\n" +
                "+Mô tả: %s\n" +
                "+Độ ưu tiên: %s\n" +
                "+Thời gian bắt đầu: %s\n" +
                "+Thời gian kết thúc: %s\n";
        String taskInfo = String.format(
                taskInfoTemplate,
                task.getName(),
                task.getDescription(),
                task.getPriority(),
                task.getStartTime(),
                task.getEndTime());

        StringBuilder userList = new StringBuilder("Danh sách user (trong đó danh sách task mà user đó đang làm được đặt trong từng cặp dấu ngoặc tròn, tương ứng với 5 giá trị trong dấu ngoặc tròn đó là (tóm tắt, mô tả, độ ưu tiên, thời gian bắt đầu, thời gian kết thúc)):\n");
        for (CollaboratorResponse collab: collaboratorList) {
            User user = userRepository.findByUsername(collab.getUsername());
            StringBuilder userInfo = new StringBuilder("-Username: "+collab.getUsername()+"\n" +
                    "+Các từ khóa mô tả thêm về năng lực của user (nếu đó là danh sách rỗng thì giả sử user đó có năng lực tương đồng với các user còn lại): "+user.getTagList()+"\n"+
                    "+Danh sách task chưa hoàn thành: ");
            List<TaskResponse> taskList = collaboratorService.getAllCollaboratorAssignedTask(collab.getId());
            for (TaskResponse t : taskList) {
                String taskString = "("+
                        t.getName()+", "+
                        t.getDescription()+", "+
                        t.getPriority()+", "+
                        t.getStartTime().toString()+", "+
                        t.getEndTime().toString()+"), ";
                userInfo.append(taskString);
            }
            userList.append(userInfo+"\n");
        }
        log.error(condition + respondStruct + taskInfo+ userList);
        String response = geminiService.sendMessage(condition + respondStruct + taskInfo+ userList);
        log.error(response);
        int start = response.indexOf('{');
        int end = response.lastIndexOf('}');
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response.substring(start, end + 1));
        return AssignTaskResponse
                .builder()
                .username(rootNode.path("username").textValue())
                .reason(rootNode.path("reason").textValue())
                .build();
    }
}
