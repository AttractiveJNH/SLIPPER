package com.example.Slipper.service.securityService;

import com.example.Slipper.entity.UserEntity;
import com.example.Slipper.repository.EntreRepository;
import com.example.Slipper.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepo;

    @Autowired
    EntreRepository entreRepository;

    // 로그인 할때 사용자 정보를 가져오는 코드.
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        UserEntity user = userRepo.findByUserId(userId)
                .orElseThrow(() -> {
                    return new UsernameNotFoundException("해당 유저를 찾을 수 없습니다.");
                });
        return new PrincipalDetails(user);
    }
}
