package com.micro.discussions.pojos;

import com.micro.discussions.entities.Comments;
import com.micro.discussions.entities.Discussion;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DiscussionPojo {
    private long id;
    private long advertisement;
    private String name;
    private String text;
    private String author;
    private List<CommentPojo> comments;


    public static DiscussionPojo fromEntity(Discussion discussion) {
        DiscussionPojo pojo = new DiscussionPojo();
        pojo.setId(discussion.getId());
        pojo.setName(discussion.getName());
        pojo.setAdvertisement(discussion.getAdvertisement());
        pojo.setText(discussion.getText());
        pojo.setAuthor(discussion.getAuthor());

        if (discussion.getComments() != null) {
            List<CommentPojo> comments = new ArrayList<>();
            pojo.setComments(comments);
            for (Comments c : discussion.getComments()) {
                comments.add(CommentPojo.fromEntity(c));
            }
        }
        return pojo;
    }

    public static Discussion toEntity(DiscussionPojo pojo, String author) {
        Discussion discussion = new Discussion();
        discussion.setId(pojo.getId());
        discussion.setName(pojo.getName());
        discussion.setAdvertisement(pojo.getAdvertisement());
        discussion.setText(pojo.getText());
        discussion.setAuthor(author);
        if (pojo.getComments() != null) {
            List<Comments> comments = new ArrayList<>();
            discussion.setComments(comments);
            for (CommentPojo commentPojo : pojo.getComments()) {
                Comments c = CommentPojo.toEntity(commentPojo, author, discussion);
                comments.add(c);
            }
        }
        return discussion;
    }

    public static List<DiscussionPojo> convertDiscussionsToPojo(List<Discussion> discussions) {
        List<DiscussionPojo> pojoList = new ArrayList<>();
        for (Discussion discussion : discussions) {
            pojoList.add(DiscussionPojo.fromEntity(discussion));
        }
        return pojoList;
    }
}
