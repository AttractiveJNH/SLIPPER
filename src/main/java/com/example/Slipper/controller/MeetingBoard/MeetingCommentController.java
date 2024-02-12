package com.example.Slipper.controller.MeetingBoard;

import com.example.Slipper.dto.MeetingBoard.MeetingCommentForm;
import com.example.Slipper.entity.MeetingBoardEntity.MeetingBoard;
import com.example.Slipper.entity.MeetingBoardEntity.MeetingBoardComment;
import com.example.Slipper.entity.userAndEntreEntities.EntreEntity;
import com.example.Slipper.entity.userAndEntreEntities.UserEntity;
import com.example.Slipper.repository.MeetingBoardRepository;
import com.example.Slipper.repository.MeetingCommentRepository;
import com.example.Slipper.service.loginAndJoinServices.EntreService;
import com.example.Slipper.service.loginAndJoinServices.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MeetingCommentController {
    @Autowired
    private MeetingBoardRepository meetingBoardRepository;

    @Autowired
    private MeetingCommentRepository meetingCommentRepository;



    private final EntreService entreService;

    private final UserService userService;

    @Autowired
    public MeetingCommentController(UserService userService, EntreService entreService){
        this.userService = userService;
        this.entreService = entreService;
    }

    // 댓글 등록
    @PostMapping("/meeting/comment/save/{meetNum}")
    public String meetCommentSave(@PathVariable Integer meetNum, @SessionAttribute(name = "id", required = false) String id,
                          Model model, MeetingCommentForm form, RedirectAttributes redirectAttributes){
        // 로그인 정보
        EntreEntity loginEntre = entreService.getLoginEntreByLoginId(id);
        UserEntity loginUser = userService.getLoginUserById(id);

        // 로그인한 유저의 키값
        Long loginEntreNum = (loginEntre != null) ? loginEntre.getEntrepreNum() : null;
        Long loginUserNum = (loginUser != null) ? loginUser.getUserNum() : null;

        // 해당 게시글 찾기
        MeetingBoard meetInfo = meetingBoardRepository.findById(meetNum).orElse(null);

        // 로그인별 처리
        if(loginEntre != null) {
            // 사업자 유저 로그인 중일때
            String nickName = loginEntre.getEntrepreNickName();
            MeetingBoardComment entreComment = form.entrepreneurMeetComment(meetInfo, loginEntre, nickName);
            meetingCommentRepository.save(entreComment);
            redirectAttributes.addFlashAttribute("msg","댓글이 작성되었습니다.");
        } else if (loginUser != null){
            // 일반 유저 로그인 중일때
            String nickName = loginUser.getUserNickName();
            MeetingBoardComment userComment = form.userMeetComment(meetInfo, loginUser, nickName);
            meetingCommentRepository.save(userComment);
            redirectAttributes.addFlashAttribute("msg","댓글이 작성되었습니다.");
        } else {
            // 로그인 중이 아닐때
            redirectAttributes.addFlashAttribute("msg","로그인 후 댓글을 작성해 주세요.");
        }

        return "redirect:/meeting/detail/{meetNum}";
    }
}
