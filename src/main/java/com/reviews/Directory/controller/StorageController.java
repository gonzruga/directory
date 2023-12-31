//package com.reviews.Directory.controller;
//
//import com.reviews.Directory.service.StorageService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.ByteArrayResource;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//@RestController // Use with Postman
//@RequestMapping("/file")
//public class StorageController {
//
//    @Autowired
//    private StorageService service;
//
//    @PostMapping("/upload")
//    public ResponseEntity<String> uploadFile(@RequestParam(value = "file") MultipartFile file) {
//        return new ResponseEntity<>(service.uploadFile(file), HttpStatus.OK);
//    }
//
////    Not implemented in UI
//    @GetMapping("/download/{fileName}")
//    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) {
//        byte[] data = service.downloadFile(fileName);
//        ByteArrayResource resource = new ByteArrayResource(data);
//        return ResponseEntity
//                .ok()
//                .contentLength(data.length)
//                .header("Content-type", "application/octet-stream")
//                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
//                .body(resource);
//    }
//
////    Not implemented in UI
//    @DeleteMapping("/delete/{fileName}")
//    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
//        return new ResponseEntity<>(service.deleteFile(fileName), HttpStatus.OK);
//    }
//
//
//}
