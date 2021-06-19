package com.hauxi.project.springredditclone.model;

    import javax.persistence.Entity;
    import javax.persistence.GeneratedValue;
    import javax.persistence.Id;
    import javax.persistence.JoinColumn;
    import javax.persistence.ManyToOne;
    import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

    import static javax.persistence.GenerationType.IDENTITY;
    import static javax.persistence.FetchType.LAZY;;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Entity
    @Builder
    public class Vote {
        @Id
        @GeneratedValue(strategy = IDENTITY)
        private Long voteId;
        private VoteType voteType;

        @NotNull
        @ManyToOne(fetch = LAZY)
        @JoinColumn(name="postId", referencedColumnName = "postId")
        private Post post;
        @ManyToOne(fetch = LAZY)
        @JoinColumn(name = "userId",referencedColumnName = "userId")
        private Users user;
    }
