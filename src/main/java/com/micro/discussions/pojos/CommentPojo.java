package com.micro.discussions.pojos;

import com.micro.discussions.entities.Comments;
import com.micro.discussions.entities.Discussion;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CommentPojo {
    private long id;
    private String text;
    private long author;
    private long discussion_id;

    public static Comments toEntity(CommentPojo pojo, long author, Discussion discussion) {
        Comments comments = new Comments();
        comments.setId(pojo.getId());
        comments.setText(pojo.getText());
        comments.setAuthor(author);
        comments.setDiscussion(discussion);
        return comments;
    }

    public static CommentPojo fromEntity(Comments comment) {
        CommentPojo pojo = new CommentPojo();
        pojo.setId(comment.getId());
        pojo.setText(comment.getText());
        pojo.setAuthor(comment.getAuthor());
        pojo.setDiscussion_id(comment.getDiscussion().getId());
        return pojo;
    }

    public static List<CommentPojo> convertCommentsToPojo(List<Comments> comments) {
        List<CommentPojo> pojoList = new ArrayList<>();
        for (Comments comment : comments)
            pojoList.add(CommentPojo.fromEntity(comment));
        return pojoList;
    }
}