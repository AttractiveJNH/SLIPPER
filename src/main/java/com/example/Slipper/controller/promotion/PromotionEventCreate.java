package com.example.Slipper.controller.promotion;

import com.example.Slipper.dto.promotionDto.PromoCreateDto;
import com.example.Slipper.entity.userAndEntreEntities.EntreEntity;
import com.example.Slipper.entity.promotionEntity.PromotionBoard;
import com.example.Slipper.entity.userAndEntreEntities.UserEntity;
import com.example.Slipper.repository.promotionRepository.PromotionBoardRepository;
import com.example.Slipper.repository.userAndEntreRepositories.EntreRepository;
import com.example.Slipper.service.loginAndJoinServices.EntreService;
import com.example.Slipper.service.loginAndJoinServices.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.UUID;

@Controller
@Slf4j
public class PromotionEventCreate {

    @Autowired
    PromotionBoardRepository promotionBoardRepository;

    @Autowired
    EntreRepository entreRepository;

    @Autowired
    EntreService entreService;

    @Autowired
    UserService userService;

    @GetMapping("/promotion/eventCreate")
    public String proEvent(@SessionAttribute(name = "id", required = false) String id, Model model){

        EntreEntity loginEntre = entreService.getLoginEntreByLoginId(id);
        UserEntity loginUser = userService.getLoginUserById(id);

        EntreEntity entre = entreRepository.findById(id).orElse(null);

        if (loginEntre != null || loginUser != null) {
            model.addAttribute("id", true);
            model.addAttribute("entre", entre);

            return "/promotion/proEventCreate";
        }
        return "redirect:/login";

    }

    @PostMapping("/promotion/eventCreate/save")
    public String proEventCreate(PromoCreateDto promotionForm, @SessionAttribute(name = "id", required = false) String id, Model model){

        log.info("Controller On");
        EntreEntity loginEntre = entreService.getLoginEntreByLoginId(id);

        log.info("ID : " + id);

        UserEntity loginUser = userService.getLoginUserById(id);

        log.info("ID : " + id);

        // id에 해당하는 값이 null인 경우 로그인 화면으로 전환.
        if (loginEntre != null || loginUser != null) {

            log.info("If On");
            model.addAttribute("id", true);
            PromotionBoard promotionBoard = promotionForm.toEntity(id);

            log.info("If On");

            log.info(promotionBoard.toString());
            promotionBoardRepository.save(promotionBoard);

            return "redirect:/promotion";
        }
        return "redirect:/login";
    }


    //    @RequestMapping(value = "/promotion/smarteditorMultiImageUpload", method = RequestMethod.POST)
    @RequestMapping(value = "/promotion/eventCreate/save/smarteditorMultiImageUpload")
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