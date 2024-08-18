package com.geist_chamber.geist_service.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class AbstractUtilController {
    public ResponseEntity<?> successResponse(String message){
        Map<String,String> res =new HashMap<>();
        res.put("msg",message);
        return new ResponseEntity<Map<String,String>>(res, HttpStatus.OK);
    }
    public ResponseEntity<?> errorResponse(String message){
        Map<String,String> res =new HashMap<>();
        res.put("msg",message);
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }
    public<T> ResponseEntity<?> listResponse(List<T> list){
        Map<String,List<T>> res=new HashMap<>();
        res.put("data",list);
        return new ResponseEntity<Map<String,List<T>>>(res,HttpStatus.OK);
    }
    public<T> ResponseEntity<?> pageResponse(Page<T> list, @org.springframework.lang.NonNull String message){
        Map<String, Page<T>> res=new HashMap<>();
        res.put(message,list);
        return new ResponseEntity<Map<String,Page<T>>>(res,HttpStatus.OK);
    }
    public<T> ResponseEntity<?> setListResponse(Set<T> list, @NonNull String message){
        Map<String,Set<T>> res=new HashMap<>();
        res.put(message,list);
        return new ResponseEntity<Map<String,Set<T>>>(res,HttpStatus.OK);
    }
    public ResponseEntity<?> singleResponse(Object data){
        Map<String,Object> res=new HashMap<>();
        res.put("data",data);
        return new ResponseEntity<Map<String,Object>>(res,HttpStatus.OK);
    }
}
