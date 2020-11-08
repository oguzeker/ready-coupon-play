//package com.bilyoner.assignment.couponapi.client;
//
//import com.betbull.cmsclient.response.UploadResponse;
//import com.bilyoner.assignment.couponapi.configuration.ClientConfiguration;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.List;
//
//@FeignClient(value = "upload",
//        url = "http://localhost:9090/",
//        configuration = ClientConfiguration.class)
//public interface BalanceApiClient {
//
////    @RequestMapping(method = RequestMethod.GET, value = "/posts")
////    List<Post> getPosts();
////
////
////    @RequestMapping(method = RequestMethod.GET, value = "/posts/{postId}", produces = "application/json")
////    Post getPostById(@PathVariable("postId") Long postId);
//
//    @PostMapping(value = "/media/upload-to-cdn", produces = "application/json", consumes = "multipart/form-data")
//    UploadResponse uploadToCdn(@RequestPart("file") MultipartFile file);
//}
