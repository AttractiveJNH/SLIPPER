//package com.example.Slipper.entity.userAndEntreEntities;
//
//import com.example.Slipper.dto.MessageDto;
//import jakarta.persistence.*;
//import lombok.*;
//import org.apache.catalina.User;
//import org.hibernate.annotations.ColumnDefault;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.OnDelete;
//import org.hibernate.annotations.OnDeleteAction;
//
//import java.time.LocalDate;
//
//@Entity
//@Table(name = "message")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//@ToString
//@Builder
//public class Message {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id; // 메세지 테이블의 pk
//
//
//    @Column(nullable = false)
//    private String title; //메세지 제목
//
//    @Column(nullable = false)
//    private String content; //메세지 내용
//
//
//    @Column(nullable = false)
//    private String sender; // 발신자
//
//
//    @Column(nullable = false)
//    private String receiver; // 송신자
//
//
//
////    // 송신하는 일반유저
////    @JoinColumn(name = "user_num", nullable = false)
////    @OnDelete(action = OnDeleteAction.NO_ACTION)
////    @ManyToOne(fetch = FetchType.LAZY)
////    private UserEntity userReceiver;
////
////
////    // 송신하는 사업가 유저
////    @JoinColumn(name = "entrepreneur_num", nullable = false)
////    @OnDelete(action = OnDeleteAction.NO_ACTION)
////    @ManyToOne(fetch = FetchType.LAZY)
////    private EntreEntity entreReceiver;
//
//
//    @CreationTimestamp
//    private LocalDate sendTime; // 전송 시간
//
//    @ColumnDefault("FALSE")
//    private boolean readStatus; // 읽음 여부
//
//
//    // 보낸 사람이 삭제할 경우
//    @Column(nullable = false)
//    private boolean deletedBySender;
//
//    // 받는 사람이 삭제할 경우
//    @Column(nullable = false)
//    private boolean deletedByReceiver;
//
////    @JoinColumn(name = "user_num")
////    @ManyToOne
////    private UserEntity user;
////
////    @JoinColumn(name = "entrepreneur_num")
////    @ManyToOne
////    private EntreEntity entre;
//
//
//    public void deleteBySender() {
//        this.deletedBySender = true;
//    }
//
//    public void deleteByReceiver() {
//        this.deletedByReceiver = true;
//    }
//
//    public boolean isDeleted() {
//        return isDeletedBySender() && isDeletedByReceiver();
//    }
//
//
//
//}
//
