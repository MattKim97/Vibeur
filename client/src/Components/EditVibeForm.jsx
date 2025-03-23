import React, { useState } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import { useEffect } from 'react';
import "../VibeForms.css";

const EditVibeForm = ({loggedUser}) => {

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

  console.log(vibeId)
  console.log(vibe);

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


  return (
    

    <div className='d-flex flex-column align-items-center justify-content-center'>
      <form onSubmit={handleSubmit} className='vibeFormContainer'>
      <h1>Edit Vibe Form</h1>
        <label>
          Title:
          <input type="text" 
          value={vibe.title}
          name="title"
          onChange={handleChange}
          className='form-control vibeFormInput'
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
        <button type="submit">Save Changes</button>
        <button type="button" onClick={() => navigate("/vibes")}>Cancel</button>
        </div>
      </form>
    </div>
  )
}

export default EditVibeForm
