package com.example.Slipper.dto.generalDto;

import com.example.Slipper.entity.generalEntity.GeneralBoard;
import com.example.Slipper.entity.userAndEntreEntities.EntreEntity;
import com.example.Slipper.entity.userAndEntreEntities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class GeneralWriteForm {
    private int general_category;

    private String general_title;

    private String general_content;

    private LocalDateTime general_write_date;


    // 일반 유저 글작성
    public GeneralBoard userGeneralEntity(UserEntity user_num, String general_nick_name){
        return new GeneralBoard(null, user_num, null, general_category, general_title,
                general_content, 0, general_write_date, general_nick_name);
    }

    // 사업가 유저 글작성
    public GeneralBoard entrepreneurGeneralEntity(EntreEntity entrepre_num, String general_nick_name){
        return new GeneralBoard(null, null, entrepre_num, general_category, general_title,
                general_content, 0, general_write_date, general_nick_name);
    }
}
