//package com.example.Slipper.controller.config;
//
//import com.example.Slipper.entity.userAndEntreEntities.UserEntity;
//import com.example.Slipper.repository.userAndEntreRepositories.EntreRepository;
//import com.example.Slipper.repository.userAndEntreRepositories.MessageRepository;
//import com.example.Slipper.repository.userAndEntreRepositories.UserRepository;
//import jdk.jshell.spi.ExecutionControl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.CloseStatus;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//import org.springframework.web.util.UriComponents;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import java.util.HashMap;
//import java.util.Objects;
//import java.util.Optional;
//
//@Component
//// 웹소켓메시지를 처리하는 클래스
//public class WebSocketMessageHandler  extends TextWebSocketHandler {
//
//    //사용자 id와 웹소켓 세션을 매핑하는 hashmap
//    HashMap<String, WebSocketSession> sessionMap = new HashMap<>();
//    private final MessageRepository messageRepository;
//    private final UserRepository userRepository;
//    private final EntreRepository entreRepository;
//
//    public WebSocketMessageHandler(MessageRepository messageRepository, UserRepository userRepository,
//                                   EntreRepository entreRepository){
//
//        this.messageRepository = messageRepository;
//        this.userRepository = userRepository;
//        this.entreRepository = entreRepository;
//
//    }
//
//    //웹소켓 연결이 설정된 후에 실행되는 메소드
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//
//        //웹소켓 세션으로부터 사용자 아이디를 찾아냄
//        String userid = searchUserName(session);
//        //세션 맵에 사용자 id와 웹소켓 세션을 추가
//        sessionMap.put(userid, session);
//
//
//        //웹소켓 세션의 URI를 파싱하여 uid를 얻어냄
//        UriComponents uriComponents =
//                UriComponentsBuilder.fromUriString(Objects.requireNonNull(session.getUri()).toString()).build();
//        String uid = uriComponents.getQueryParams().getFirst("uid");
//
//        // uid를 통해 사용자를 찾음
//        UserEntity user = userRepository.getById(uid);
//
//        //해당 사용자가 존재하는 경우
//
//        if(user != null){
//
//            //사용자의 ID를 얻어냄
//            String userId = user.getId();
//        }
//        else {
//            session.sendMessage(new TextMessage("존재하지 않는 사용자입니다."));
//        }
//    }
//
//    //웹소켓 연결이 종료된 후 실행되는 메소드
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception{
//
//        String userid = searchUserName(session);
//        sessionMap.remove(userid);
//
//    }
//
//    //특정 사용자에게 웹소켓 메세지를 전송하는 메소드
//    public void sendNotification(String userId, String message) throws Exception {
//
//        //세션 맵에서 사용자의 웹소켓 세션을 얻어냄
//        WebSocketSession session = sessionMap.get(userId);
//
//        //해당 세션이 존재하고 열려있는 경우, 메세지를 전송
//        if(session != null && session.isOpen()){
//            session.sendMessage(new TextMessage(message));
//        }
//    }
//
//    //웹소켓세션의 URI를 파ㅣㅇ하여 사용자의 id를 찾아내는 메소드
//    public String searchUserName(WebSocketSession session){
//
//        UriComponents uriComponents = UriComponentsBuilder.fromUriString(session.getUri().toString()).build();
//        return uriComponents.getQueryParams().getFirst("uid");
//    }
//
//}
