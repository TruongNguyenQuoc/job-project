package com.project.tutoronline.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class PostTimeTeaching {

    @EmbeddedId
    private PostTimeTeachingId keyId;

    @ManyToOne
    @MapsId("postId")
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @MapsId("timeTeachingId")
    @JoinColumn(name = "time_teaching_id")
    private TimeTeaching timeTeaching;

    private boolean status;

}
