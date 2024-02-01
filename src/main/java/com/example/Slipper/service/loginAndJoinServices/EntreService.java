package com.example.Slipper.service.loginAndJoinServices;


import com.example.Slipper.dto.LoginRequest;
import com.example.Slipper.entity.userAndEntreEntities.EntreEntity;
import com.example.Slipper.repository.userAndEntreRepositories.EntreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class EntreService {



    private final EntreRepository entreRepository;




    //아이디 중복체크
    /**
     * loginId 중복 체크
     * 회원가입 기능 구현 시 사용
     * 중복되면 true return
     */
    public boolean checkLoginIdDuplicate(String id){

        return entreRepository.existsById(id);

    }

    /**
     * nickname 중복 체크
     * 회원가입 기능 구현 시 사용
     * 중복되면 true return
     */
    public boolean checkNicknameDuplicate(String entrepreNickName) {

        return entreRepository.existsByEntrepreNickName(entrepreNickName);

    }


    public EntreEntity getLoginEntreByLoginId(String id) {

        if(id == null) return null;

        Optional<EntreEntity> optionalUser = entreRepository.findById(id);
        if(optionalUser.isEmpty()) return null;

        return optionalUser.get();
    }

    public EntreEntity login(LoginRequest req) {
        Optional<EntreEntity> optionalUser = entreRepository.findById(req.getLoginId());

        // loginId와 일치하는 User가 없으면 null return
        if(optionalUser.isEmpty()) {
            return null;
        }

        EntreEntity entre = optionalUser.get();

        // 찾아온 User의 password와 입력된 password가 다르면 null return
        if(!entre.getPassword().equals(req.getPassword())) {
            return null;
        }

        return entre;
    }

}
