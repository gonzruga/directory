package com.reviews.Directory.entity_model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "image_model")
public class ImageModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long imageId;
    private String imageName;
    private String type;

    @Column(length = 50000000)
    private byte[] picBytes;

    public ImageModel(String originalFilename, String contentType, byte[] bytes) {
    }


}
