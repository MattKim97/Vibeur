import React from 'react'
import anonymous from "../images/anonymous.png";
import "../Comments.css"


const CommentsContainer = ({comment}) => {
  return (
    <div className="d-flex flex-row comment-row">
    <div className="p-2 commentuserImage"><img src={comment.user && comment.user.userImageUrl ? comment.user.userImageUrl : anonymous} alt="user" className="comments_user_image"/></div>
    <div className="comment-text w-100">
        <h6 className="font-medium">{comment.user.username}</h6> <span className="m-b-15 d-block">{comment.comment} </span>
    </div>
</div> 
  )
}

export default CommentsContainer
