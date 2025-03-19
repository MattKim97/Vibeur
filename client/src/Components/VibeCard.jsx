import React from 'react'
import '../VibeCard.css'
import anonymous from '../images/anonymous.png';
import { useState } from 'react'
import { useEffect } from 'react'




const VibeCard = ({vibe}) => {



useEffect(() => {
            fetch(`http://localhost:8080/api/vibe/${vibe.vibeId}/likes`)
            .then(res => res.json()
            .then(fetchedLikes => {
                setLoaded(true)
                setLikes(fetchedLikes)
            }))
        }, []) 

const [likes, setLikes] = useState([])
    const [loaded, setLoaded] = useState(false)

    if (!loaded){
        return null
    }

if(vibe.title .length > 16){
    vibe.title = vibe.title.substring
    (0, 14) + '...'
}

if(vibe.description.length > 65){
    vibe.description = vibe.description.substring
    (0, 65) + '...'
}

function timeAgo(date) {
    const seconds = Math.floor((new Date() - new Date(date)) / 1000);

    if (seconds < 60) return `${seconds}s ago`;
    const minutes = Math.floor(seconds / 60);
    if (minutes < 60) return `${minutes} min ago`;
    const hours = Math.floor(minutes / 60);
    if (hours < 24) return `${hours} hr${hours > 1 ? 's' : ''} ago`;
    const days = Math.floor(hours / 24);
    if (days < 7) return `${days} day${days > 1 ? 's' : ''} ago`;
    const weeks = Math.floor(days / 7);
    if (weeks < 4) return `${weeks} week${weeks > 1 ? 's' : ''} ago`;
    const months = Math.floor(days / 30);
    if (months < 12) return `${months} month${months > 1 ? 's' : ''} ago`;
    const years = Math.floor(days / 365);
    return `${years} year${years > 1 ? 's' : ''} ago`;
}


  return (
    <div className="vibeCard">
    <div className='vibeCardMain'>
      <img className='vibeImage' src={vibe.imageUrl} alt="vibeCardImage" />
      <h2 className='text-white'>{vibe.title}</h2>
      <p className='description'>{vibe.description}</p>
      <div className='vibeInfo'>
        <div className="likes">
            <p> 💖 {likes.length}</p>
        </div>
        <div className="uploaded_date" >
          <p>{timeAgo(vibe.dateUploaded)}</p>
        </div>
      </div>
      <hr />
      <div className='user'>
        <div className='wrapper'>
          <img src={vibe != null && vibe.user.userImageUrl != null ? vibe.user.userImageUrl : anonymous} alt="userImg" />
        </div>
        <p className='text-white'><ins>Vibe by: </ins> {vibe.user.username}</p>
      </div>
    </div>
  </div>
  )
}

export default VibeCard
