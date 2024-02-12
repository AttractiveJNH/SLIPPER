package com.example.Slipper.controller.generalBoard;

import com.example.Slipper.dto.MeetingBoard.MeetingCommentForm;
import com.example.Slipper.dto.generalDto.GeneralCommentForm;
import com.example.Slipper.entity.MeetingBoardEntity.MeetingBoard;
import com.example.Slipper.entity.MeetingBoardEntity.MeetingBoardComment;
import com.example.Slipper.entity.generalEntity.GeneralBoard;
import com.example.Slipper.entity.generalEntity.GeneralBoardComment;
import com.example.Slipper.entity.userAndEntreEntities.EntreEntity;
import com.example.Slipper.entity.userAndEntreEntities.UserEntity;
import com.example.Slipper.repository.generalRepository.GeneralBoardRepository;
import com.example.Slipper.repository.generalRepository.GeneralCommentRepository;
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
public class GeneralCommentController {

    @Autowired
    private GeneralBoardRepository generalBoardRepository;

    @Autowired
    private GeneralCommentRepository generalCommentRepository;

    private final EntreService entreService;

    private final UserService userService;

    @Autowired
    public GeneralCommentController(UserService userService, EntreService entreService){
        this.userService = userService;
        this.entreService = entreService;
    }

    // 댓글 등록
    @PostMapping("/general/comment/save/{generalNum}")
    public String generalCommentSave(@PathVariable Integer generalNum, @SessionAttribute(name = "id", required = false) String id,
                          Model model, GeneralCommentForm form, RedirectAttributes redirectAttributes){
        // 로그인 정보
        EntreEntity loginEntre = entreService.getLoginEntreByLoginId(id);
        UserEntity loginUser = userService.getLoginUserById(id);

        // 해당 게시글 찾기
        GeneralBoard generalInfo = generalBoardRepository.findById(generalNum).orElse(null);

        // 로그인별 처리
        if(loginEntre != null) {
            // 사업자 유저 로그인 중일때
            String nickName = loginEntre.getEntrepreNickName();
            GeneralBoardComment entreComment = form.entrepreneurGeneralComment(generalInfo, loginEntre, nickName);
            generalCommentRepository.save(entreComment);
            redirectAttributes.addFlashAttribute("msg","댓글이 작성되었습니다.");
        } else if (loginUser != null){
            // 일반 유저 로그인 중일때
            String nickName = loginUser.getUserNickName();
            GeneralBoardComment userComment = form.userGeneralComment(generalInfo, loginUser, nickName);
            generalCommentRepository.save(userComment);
            redirectAttributes.addFlashAttribute("msg","댓글이 작성되었습니다.");
        } else {
            // 로그인 중이 아닐때
            redirectAttributes.addFlashAttribute("msg","로그인 후 댓글을 작성해 주세요.");
        }

        return "redirect:/general/detail/{generalNum}";
    }
}
