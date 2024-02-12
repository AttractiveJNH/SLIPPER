package com.example.Slipper.controller.promotion;

import com.example.Slipper.entity.promotionEntity.PromotionBoard;
import com.example.Slipper.entity.userAndEntreEntities.EntreEntity;
import com.example.Slipper.entity.userAndEntreEntities.UserEntity;
import com.example.Slipper.repository.promotionRepository.PromotionBoardRepository;
import com.example.Slipper.service.loginAndJoinServices.EntreService;
import com.example.Slipper.service.loginAndJoinServices.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.UUID;

@Controller
@Slf4j
public class PromoEventEdit {

    @Autowired
    PromotionBoardRepository promotionBoardRepository;

    @Autowired
    EntreService entreService;

    @Autowired
    UserService userService;

    // event로 등록된 홍보게시물의 Id값을 기준으로 데이터 불러와서 eventEdit페이지에 등록.
    @GetMapping("/promotion/eventEdit/{Id}")
    public String eventEdit(@PathVariable int Id, Model model, @SessionAttribute(name = "id", required = false) String id) {

        EntreEntity loginEntre = entreService.getLoginEntreByLoginId(id);
        UserEntity loginUser = userService.getLoginUserById(id);


        // 세션값 유무에 따라 헤더변동(true = LogOut / false = 헤더 없음)
        if (loginEntre != null || loginUser != null) {

            model.addAttribute("id", true);

            int promoBrdPostId = Id; // 변수명 변경.
            PromotionBoard proEventEdit = promotionBoardRepository.findByPromoBrdPostId(promoBrdPostId);
            model.addAttribute("eventEdit", proEventEdit);

            return "promotion/eventEdit";

        }

        return "redirect:/login";
    }

    // edit페이지의 필드의 값을 수정해서 target데이터에 교체해주는 컨트롤러.
    @PostMapping("/promotion/eventEditSave/{Id}")
    public String eventEditSave(@PathVariable int Id, @ModelAttribute("promotion") PromotionBoard proModi, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "입력값이 유효하지 않습니다.");
        }

        // DB의 데이터 받아와서 뷰 페이지에서 받아온 필드의 값으로 대체.
        PromotionBoard target = promotionBoardRepository.findByPromoBrdPostId(Id);
        if (target != null) {
            target.setPromoBrdTitle(proModi.getPromoBrdTitle());
            target.setPromoBrdRegion(proModi.getPromoBrdRegion());
            target.setPromoBrdCategory(proModi.getPromoBrdCategory());
            target.setPromoBrdBusinessName(proModi.getPromoBrdBusinessName());
            target.setPromoBrdExperienceDate(proModi.getPromoBrdExperienceDate());
            target.setPromoBrdExpSelect(proModi.getPromoBrdExpSelect());
            target.setPromoBrdApplyStartDate(proModi.getPromoBrdApplyStartDate());
            target.setPromoBrdApplyEndDate(proModi.getPromoBrdApplyEndDate());
            target.setPromoBrdEventStartDate(proModi.getPromoBrdEventStartDate());
            target.setPromoBrdEventEndDate(proModi.getPromoBrdEventEndDate());
            target.setPromoBrdReviewStart(proModi.getPromoBrdReviewStart());
            target.setPromoBrdReviewDeadline(proModi.getPromoBrdReviewDeadline());
            target.setPromoBrdExperienceProvided(proModi.getPromoBrdExperienceProvided());
            target.setPromoBrdContent(proModi.getPromoBrdContent());
            target.setPromoBrdMaxParticipants(proModi.getPromoBrdMaxParticipants());

            promotionBoardRepository.save(target);
        }

        return "redirect:/promotion/eventdetail/{Id}";
    }

    @RequestMapping(value = "/promotion/eventEditSave/{Id}/smarteditorMultiImageUpload")
    public void smarteditorMultiImageUpload(HttpServletRequest request, HttpServletResponse response){
        try {
            //파일정보
            String sFileInfo = "";
            //파일명을 받는다 - 일반 원본파일명
            String sFilename = request.getHeader("file-name");
            //파일 확장자
            String sFilenameExt = sFilename.substring(sFilename.lastIndexOf(".")+1);
            //확장자를소문자로 변경
            sFilenameExt = sFilenameExt.toLowerCase();

            //이미지 검증 배열변수
            String[] allowFileArr = {"jpg","png","bmp","gif"};

            //확장자 체크
            int nCnt = 0;
            for(int i=0; i<allowFileArr.length; i++) {
                if(sFilenameExt.equals(allowFileArr[i])){
                    nCnt++;
                }
            }

            //이미지가 아니라면
            if(nCnt == 0) {
                PrintWriter print = response.getWriter();
                print.print("NOTALLOW_"+sFilename);
                print.flush();
                print.close();
            } else {
                //디렉토리 설정 및 업로드

                //파일경로
                String filePath = "//192.168.2.3/images/d/";

                File file = new File(filePath);

                if(!file.exists()) {
                    file.mkdirs();
                }

                String sRealFileNm = "";
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
                String today= formatter.format(new java.util.Date());
                sRealFileNm = today+ UUID.randomUUID().toString() + sFilename.substring(sFilename.lastIndexOf("."));
                String rlFileNm = filePath + sRealFileNm;

                ///////////////// 서버에 파일쓰기 /////////////////
                InputStream inputStream = request.getInputStream();
                OutputStream outputStream=new FileOutputStream(rlFileNm);
                int numRead;
                byte bytes[] = new byte[Integer.parseInt(request.getHeader("file-size"))];
                while((numRead = inputStream.read(bytes,0,bytes.length)) != -1){
                    outputStream.write(bytes,0,numRead);
                }
                if(inputStream != null) {
                    inputStream.close();
                }
                outputStream.flush();
                outputStream.close();

                ///////////////// 이미지 /////////////////
                // 정보 출력
                sFileInfo += "&bNewLine=true";
                // img 태그의 title 속성을 원본파일명으로 적용시켜주기 위함
                sFileInfo += "&sFileName="+ sFilename;
                sFileInfo += "&sFileURL="+"//192.168.2.3/images/d/"+sRealFileNm;
                PrintWriter printWriter = response.getWriter();
                printWriter.print(sFileInfo);
                printWriter.flush();
                printWriter.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}