import React, { useState } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import { useEffect } from 'react';
import "../VibeForms.css";
import { gsap } from 'gsap';
import { ScrollTrigger } from 'gsap/ScrollTrigger';
import { useRef } from 'react';

gsap.registerPlugin(ScrollTrigger);

const EditVibeForm = ({loggedUser}) => {
    const formRef = useRef(null);



      useEffect(() => {
        console.log(formRef.current)
        gsap.set(formRef.current, { opacity: 0 }); // Ensure it starts as hidden


        setTimeout(() => {
          if (formRef.current) {
            gsap.fromTo(
              formRef.current,
              { opacity: 0 },
              { opacity: 1, duration: 2, ease: "power2.inOut" }
            );
          }
        }, 100); // Small delay
      }, []);

  const { vibeId } = useParams();

  const [vibe,setVibe] = useState({})
  const [loaded, setLoaded] = useState(false)

  const navigate = useNavigate();

    useEffect(() => {
      fetch(`http://localhost:8080/api/vibe/${vibeId}`).then((res) =>
        res.json().then((fetchedVibe) => {
          setVibe(fetchedVibe);
          setLoaded(true);
        })
      );
    }, []);


  const handleChange = (event) => {
    setVibe({
      ...vibe,
      [event.target.name]: event.target.value
    })
  }

  const handleSubmit = (event) => {
    event.preventDefault();
    fetch(`http://localhost:8080/api/vibe/${vibeId}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Authorization: loggedUser.jwt,
      },
      body: JSON.stringify(vibe)
    }).then(() => {
      navigate(`/vibe/${vibeId}`);
    })
  }

  


  if(!loaded){
    return null;
  }

  console.log(vibe);

  console.log("vibeId"+ vibe.user.userId);
  console.log("userId" + loggedUser.userId);

  if(loggedUser && loggedUser.userId != vibe.user.userId){
    navigate("/NotFound");
    }
  


  return (
    

    <div className='d-flex flex-column align-items-center justify-content-center opacityContainer' ref={formRef}>
      <form onSubmit={handleSubmit} className='vibeFormContainer' >
      <h1>Edit Vibe Form</h1>
        <label>
          Title:
          <input type="text" 
          value={vibe.title}
          name="title"
          onChange={handleChange}
          className='vibeFormInput form-control'
          />
        </label>
        <br />
        <label>
          Description:
          <input type="text" 
          value={vibe.description}
          name="description"
          onChange={handleChange}
          className='vibeFormInput form-control'
           />
        </label>
        <div className='vibeFormButtonsContainer'>
        <button type="submit" className='vibeFormButton'>Edit</button>
        <button type="button"className='vibeFormButton' onClick={() => navigate("/vibes")}>Cancel</button>
        </div>
      </form>
    </div>
  )
}

export default EditVibeForm
