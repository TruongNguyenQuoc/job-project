package com.project.tutoronline.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Setter
@Getter
public class PostTimeTeachingId  implements Serializable {

    Long postId;

    Long timeTeachingId;

}
