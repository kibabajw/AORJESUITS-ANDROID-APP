package org.easternafricajesuits.models;

public class LikeModel {

    private boolean isLiked;

    public LikeModel(boolean isLiked) {
        this.isLiked = isLiked;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }
}
