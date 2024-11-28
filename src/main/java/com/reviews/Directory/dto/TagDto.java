package com.reviews.Directory.dto;

import com.reviews.Directory.entity_model.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter  //Data sometimes used for including objects
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TagDto {

    private Long id;

    private String tagTitle;
    private String tagDescription;

    private Date createdAt = new Date();
    private Date updatedAt = null;

    public Tag dtoToTag() {

        final Tag tag = new Tag();

        tag.setId(id);
        tag.setTagTitle(tagTitle);
        tag.setTagDescription(tagDescription);

        return tag;

    }

}
