import React, { use } from "react";
import { useParams } from "react-router-dom";
import { useState } from "react";
import { useEffect } from "react";
import { Heart } from "lucide-react";
import "../Vibe.css";
import anonymous from "../images/anonymous.png";
import CommentsContainer from "./CommentsContainer";
import MusicPlayer from "./MusicPlayer";
import { Smile } from "lucide-react";
import { Laugh } from "lucide-react";
import { Frown } from "lucide-react";
import { Angry } from "lucide-react";
import { Headphones } from "lucide-react";
import { useNavigate } from "react-router-dom";
import { gsap } from 'gsap';
import { ScrollTrigger } from 'gsap/ScrollTrigger';
import { useRef } from "react";

gsap.registerPlugin(ScrollTrigger);


const Vibe = ({ loggedUser }) => {
  const INITIAL_COMMENT = {
    comment: "",
  };

      const vibeRef = useRef(null);
      const commentModalRef = useRef(null);

  

      useEffect(() => {

        gsap.set(vibeRef.current, { opacity: 0 }); // Ensure it starts as hidden


        setTimeout(() => {
          if (vibeRef.current) {
            gsap.fromTo(
              vibeRef.current,
              { opacity: 0 },
              { opacity: 1, duration: 2, ease: "power2.inOut" }
            );
          }
        }, 100); // Small delay
      }, []);

  const [vibe, setVibe] = useState({});
  const [likes, setLikes] = useState([]);
  const [comments, setComments] = useState([]);
  const [loaded, setLoaded] = useState(false);
  const [hasLiked, setHasLiked] = useState(false);
  const [modalState, setModalState] = useState(false);
  const [newComment, setNewComment] = useState(INITIAL_COMMENT);
  const [editingCommentId, setEditingCommentId] = useState(null);

  const { vibeId } = useParams();
  const navigate = useNavigate();

  const openModal = () => setModalState(true);
  const closeModal = () => setModalState(false);

  const openModalAddComment = () => {
    setEditingCommentId(null);
    setNewComment(INITIAL_COMMENT);
    setModalState(true);
  };

  useEffect(() => {
    if (modalState && commentModalRef.current) {
      gsap.fromTo(
        commentModalRef.current,
        { opacity: 0, scale: 0.8 },
        { opacity: 1, scale: 1, duration: 0.5, ease: "power2.out" }
      );
    }
  }, [modalState]);

  useEffect(() => {
    fetch(`http://localhost:8080/api/vibe/${vibeId}`).then((res) =>
      res.json().then((fetchedVibe) => {
        setVibe(fetchedVibe);
        setLoaded(true);
      })
    );
  }, []);

  useEffect(() => {
    fetch(`http://localhost:8080/api/vibe/${vibeId}/likes`).then((res) =>
      res.json().then((fetchedLikes) => {
        setLikes(fetchedLikes);
      })
    );
  }, [comments]);

  useEffect(() => {
    fetch(`http://localhost:8080/api/vibe/${vibeId}/comments`).then((res) =>
      res.json().then((fetchedComments) => {
        setComments(fetchedComments);
      })
    );
  }, []);

  useEffect(() => {
    if (loggedUser && likes.some((like) => like.userId === loggedUser.userId)) {
      setHasLiked(true);
    } else {
      setHasLiked(false);
    }
  }, [likes, loggedUser]);

  if (!loaded) {
    return null;
  }


  function timeAgo(date) {
    const seconds = Math.floor((new Date() - new Date(date)) / 1000);

    if (seconds < 60) return `${seconds}s ago`;
    const minutes = Math.floor(seconds / 60);
    if (minutes < 60) return `${minutes} min ago`;
    const hours = Math.floor(minutes / 60);
    if (hours < 24) return `${hours} hr${hours > 1 ? "s" : ""} ago`;
    const days = Math.floor(hours / 24);
    if (days < 7) return `${days} day${days > 1 ? "s" : ""} ago`;
    const weeks = Math.floor(days / 7);
    if (weeks < 4) return `${weeks} week${weeks > 1 ? "s" : ""} ago`;
    const months = Math.floor(days / 30);
    if (months < 12) return `${months} month${months > 1 ? "s" : ""} ago`;
    const years = Math.floor(days / 365);
    return `${years} year${years > 1 ? "s" : ""} ago`;
  }

  const likeClick = () => {
    if (!hasLiked) {
      fetch(`http://localhost:8080/api/vibe/${vibeId}/likes`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: loggedUser.jwt,
        },
        body: JSON.stringify({ loggedUser }),
      })
        .then((res) => res.json())
        .then((data) => {
          setLikes((prevLikes) => [...prevLikes, data]);
        });
    } else {
      fetch(`http://localhost:8080/api/vibe/${vibeId}/likes`, {
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
          Authorization: loggedUser.jwt,
        },
        body: JSON.stringify({ loggedUser }),
      }).then(() => {
        setLikes((prevLikes) =>
          prevLikes.filter((like) => like.userId !== loggedUser.userId)
        );
      });
    }
  };

  const addComment = () => {
    if (!newComment.comment.trim()) return; // Prevent empty comments

    fetch(`http://localhost:8080/api/vibe/${vibeId}/comments`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: loggedUser.jwt,
      },
      body: JSON.stringify({ comment: newComment.comment }), // Only send the comment text
    })
      .then((res) => res.json())
      .then((savedComment) => {
        setComments([...comments, savedComment]);
        setNewComment(INITIAL_COMMENT); // Clear input after submitting
        closeModal(); // Close the modal after submitting
      });
  };

  const deleteComment = (commentId) => {
    fetch(`http://localhost:8080/api/comment/${commentId}`, {
      method: "DELETE",
    }).then(() => {
      setComments((prevComments) =>
        prevComments.filter((comment) => comment.commentId !== commentId)
      );
      closeModal();
      setNewComment(INITIAL_COMMENT);
      setEditingCommentId(null);
    });
  };

  const editComment = (commentId) => {
    const commentToEdit = comments.find(comment => comment.commentId === commentId);
    if (commentToEdit) {
      setNewComment({ comment: commentToEdit.comment });
      openModal();
  
      setEditingCommentId(commentId);
    }
  };
  
  const submitEditedComment = () => {
    if (!newComment.comment.trim() || !editingCommentId) return;
  
    fetch(`http://localhost:8080/api/comment/${editingCommentId}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Authorization: loggedUser.jwt,
      },
      body: JSON.stringify({ comment: newComment.comment }),
    }).then(() => {
      setComments((prevComments) =>
        prevComments.map((comment) =>
          comment.commentId === editingCommentId
            ? { ...comment, comment: newComment.comment }
            : comment
        )
      );
      closeModal();
      setNewComment(INITIAL_COMMENT);
      setEditingCommentId(null);
    });
  };

  const handleChange = (event) => {
    setNewComment({
      ...newComment,
      [event.target.name]: event.target.value,
    });
  };

  const handleDeleteVibe = () => {
    fetch(`http://localhost:8080/api/vibe/${vibeId}`, {
      method: "DELETE",
      headers: {
        Authorization: loggedUser.jwt,
      },
    }).then(() => {
      navigate("/vibes");
    });
  }

  let icon;

  switch (vibe.mood.moodName) {
    case "happy":
      icon = <Smile size={24} color="yellow" />;
      break;
    case "sad":
      icon = <Frown size={24} color="blue" />;
      break;
    case "angry":
      icon = <Angry size={24} color="red" />;
      break;
    case "excited":
      icon = <Laugh size={24} color="green" />;
      break;
    case "relaxed":
      icon = <Headphones size={24} color="cyan" />;
      break;
  }

  console.log(newComment);

  return (
    <div className="vibeContainer d-flex flex-row align-items-center justify-content-center">
      <div className="vibeContainerMain d-flex flex-column align-items-center justify-content-center " ref={vibeRef}>
        <div className="musicPlayerBackground ">
          <MusicPlayer audioUrl={vibe.songUrl} />
        </div>
        <h2 className="text-white display-4 gradient_text">{vibe.title}</h2>
        <div className="descriptionLikesContainer d-flex flex-column align-items-center justify-content-center">
          <div className="d-flex flex-row align-items-center justify-content-center">
            <p className="">{vibe.description}</p>
            <div className="vibeInfo d-flex flex-row gap-1 align-items-center justify-content-between">
              <div className="likes">
                <p className=" d-flex flex-row gap-1 align-items-center textSizePVibe ms-2 ">
                  {loggedUser ? (
                    vibe && hasLiked ? (
                      <Heart
                        size={24}
                        color="pink"
                        fill="pink"
                        onClick={likeClick}
                        className="changeToPointer"
                      />
                    ) : (
                      <Heart
                        size={24}
                        color="pink"
                        onClick={likeClick}
                        className="changeToPointer"
                      />
                    )
                  ) : (
                    <Heart size={24} color="pink" />
                  )}
                  {likes.length}
                </p>
              </div>
              <div className="uploaded_date pxFont24 ms-2">
                <p>{timeAgo(vibe.dateUploaded)}</p>
              </div>
            </div>
          </div>
        </div>

        <div className="vibeContainerMainImgCom">
          <div className={modalState ? "commentsModal" : "commentsModalclosed"}   ref={commentModalRef}
          >
            <div className="modalHeader d-flex flex-row align-items-center justify-content-center">
              <h2 className="text-center"> {editingCommentId ? "Edit a comment" : "Add a comment"}</h2>
            </div>
            <input
              className="form-control w-50"
              type="text"
              placeholder="Add a comment..."
              name="comment"
              value={newComment.comment}
              onChange={handleChange}
            />
            <div className="commentsModalButtonsContainer">
            <button
              className="commentsButtonModal"
              onClick={editingCommentId ? submitEditedComment : addComment}
            >
              {editingCommentId ? "Edit" : "Add"}
            </button>
            <button className="commentsButtonModal" onClick={closeModal}>
              Close
            </button>
            </div>
          </div>
          <img className="vibe_image" src={vibe.imageUrl} alt="vibeCardImage" />
          <div className="commentsContainer">
            <div className="card">
              <h3 className="text-center">Comments</h3>
              <div className="comment-widgets">
                {loggedUser ?     <button className="commentsButton mb-2" onClick={openModalAddComment}>
                  Comment
                </button> : null}
                {comments && comments.length > 0 ? (
                  comments.map((comment) => (
                    <div>
                      <CommentsContainer
                        key={comment.comm}
                        comment={comment}
                        loggedUser={loggedUser}
                      />
                      {comment.user.userId === loggedUser?.userId && (
                        <div className="d-flex flex-row gap-2 align-items-center justify-content-end">
                          <button
                            className="userButton"
                            onClick={() => editComment(comment.commentId)}
                          >
                            Edit
                          </button>
                          <button
                            className="userButton"
                            onClick={() => deleteComment(comment.commentId)}
                          >
                            Delete
                          </button>
                        </div>
                      )}
                    </div>
                  ))
                ) : (
                  <p className="text-center">No comments yet!</p>
                )}
              </div>
            </div>
          </div>
        </div>
        <div className="user d-flex flex-row align-items-center justify-content-center gap-2 mt-5">
          <div className="wrapper">
            <img
              className="vibe_user_image"
              src={
                vibe && vibe.user && vibe.user.userImageUrl
                  ? vibe.user.userImageUrl
                  : anonymous
              }
              alt="userImg"
            />
          </div>
          <p className="text-white">
            <ins>Vibe by</ins>
            <span>: </span>
            {vibe && vibe.user ? vibe.user.username : "Unknown"}
          </p>
          {vibe?.user?.userId === loggedUser?.userId ? (
            <div className="text-white d-flex flex-row gap-2 align-items-center justify-content-center">
              <button className="userButton" onClick={() => navigate(`/vibe/${vibeId}/edit`)}>Edit</button>
              <button className="userButton" onClick={handleDeleteVibe}>Delete</button>
            </div>
          ) : null}
        </div>
      </div>
    </div>
  );
};

export default Vibe;
