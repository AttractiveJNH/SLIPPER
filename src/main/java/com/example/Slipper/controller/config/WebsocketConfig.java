//package com.example.Slipper.controller.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.socket.WebSocketHandler;
//import org.springframework.web.socket.config.annotation.EnableWebSocket;
//import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
//import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
//import org.springframework.web.socket.messaging.WebSocketAnnotationMethodMessageHandler;
//
//import java.net.http.WebSocket;
//
////웹소켓 기능 활성화
//@EnableWebSocket
//// 이 클래스를 스프링 설정 클래스로 선언
//@Configuration
//// WebSocketConfigurer 인터페이스를 구현하여 웹 소켓 설정을 커스터마이징
//public class WebsocketConfig implements WebSocketConfigurer {
//
//    // 웹소켓 메세지를 처리하는 핸들러
//    WebSocketMessageHandler webSocketMessageHandler;
//
//    // 생성자를 통해 WebSocketMessageHandler 인스턴스를 주입 받음
//    public WebsocketConfig(WebSocketMessageHandler webSockeMessageHandler){
//
//        this.webSocketMessageHandler = webSockeMessageHandler;
//    }
//    @Override
//
//    //웹소켓 핸들러를 등록하는 메소드를 오버라이드
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//
//        registry.addHandler(webSocketMessageHandler, "/test").setAllowedOrigins("*");
//        //"test"라는 엔드포인트에 대해 WebSocketMessageHandler를 핸들러로 등록하고 setAllowedorigins("*")로 모든 CORS 요청을 허용.
//    }
//
//
//}
