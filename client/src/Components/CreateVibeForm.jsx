import React from "react";
import { useNavigate } from "react-router-dom";
import { useEffect } from "react";
import "../VibeForms.css";
import { useState } from "react";
import { gsap } from 'gsap';
import { ScrollTrigger } from 'gsap/ScrollTrigger';
import { useRef } from "react";


gsap.registerPlugin(ScrollTrigger);


const CreateVibeForm = ({loggedUser}) => {
      const formRef = useRef(null);
  

          useEffect(() => {
              gsap.fromTo(formRef.current, 
                { opacity: 0 }, 
                { opacity: 1, duration: 2, ease: "power2.inOut" } 
              );
          }, []);
  const INITIAL_VIBE = {
    title: "",
    description: "",
    imageUrl: "",
    songUrl: "",
    mood: {
      moodId: 0,
      moodName: "",
    },
  };

  const S3_BUCKET_IMAGE = import.meta.env.VITE_S3_BUCKET_IMAGE;
  const REGION = import.meta.env.VITE_REGION;
  const S3_KEY = import.meta.env.VITE_S3_KEY;
  const S3_SECRET = import.meta.env.VITE_S3_SECRET;
  const [vibe, setVibe] = useState(INITIAL_VIBE);

  const [songFile, setSongFile] = useState(null);
  const[imageFile, setImageFile] = useState(null);
  const [errors, setErrors] = useState([]);
  const navigate = useNavigate();

  const getFileUrl = (fileName) => {
    return `https://${S3_BUCKET_IMAGE}.s3.${REGION}.amazonaws.com/${fileName}`;
  };


  const uploadFile = async (file) => {
    const S3_BUCKET = S3_BUCKET_IMAGE;

    if (!file) {
      alert("Please select a file first.");
      return null;
    }

    AWS.config.update({
      accessKeyId: S3_KEY,
      secretAccessKey: S3_SECRET,
    });

    const s3 = new AWS.S3({
      params: { Bucket: S3_BUCKET },
      region: REGION,
    });

    const params = {
      Bucket: S3_BUCKET,
      Key: file.name,
      Body: file,
    };

    try {
      await s3
        .putObject(params)
        .on("httpUploadProgress", (evt) => {
          console.log(
            "Uploading " + parseInt((evt.loaded * 100) / evt.total) + "%"
          );
        })
        .promise();

      const url = getFileUrl(file.name);
      return url;
    } catch (err) {
      console.error("Error uploading file:", err);
      alert("Error uploading file.");
      return null;
    }
  };

    const handleFileChange = (e) => {
        const file = e.target.files[0];
        setImageFile(file);
      }

        const handleSongFileChange = (e) => {
            const file = e.target.files[0];
            setSongFile(file);
            }

    const handleCreate = async (e) => {
        e.preventDefault();
        setErrors([]);
        const imageUrl = await uploadFile(imageFile);
        const songUrl = await uploadFile(songFile);

        if (imageUrl) {
          vibe.imageUrl = imageUrl;
        }
        if (songUrl) {
          vibe.songUrl = songUrl;
        }

        fetch("http://localhost:8080/api/vibe", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization : loggedUser.jwt,
          },
          body: JSON.stringify(vibe),
        })
        .then( response => {
            if (response.status >= 200 && response.status < 300) {
                response.json().then((createdVibe) => {
                    navigate(`/vibe/${createdVibe.vibeId}`);
                });
            }else {
                response.json().then(fetchedErrors => setErrors(fetchedErrors));
            }
        })
        }


  switch (vibe.mood.moodId) {
    case "1":
      vibe.mood.moodName = "happy";
      break;
    case "2":
      vibe.mood.moodName = "sad";
      break;
    case "3":
      vibe.mood.moodName = "angry";
      break;
    case "4":
      vibe.mood.moodName = "relaxed";
      break;
    case "5":
      vibe.mood.moodName = "excited";
      break;
    default:
      vibe.mood.moodName = "";
  }

  return (
    <div>
      <form className="vibeFormContainer" onSubmit={handleCreate} ref={formRef}>
        <h2>Create a Vibe</h2>
        {errors && errors.length > 0 && (
          <ul id="errors" className="error">
            {errors.map((error) => (
              <li className="me-4" key={error}>
                {error}
              </li>
            ))}
          </ul>
        )}
        <label>Title:
        <input
          type="text"
          name="title"
          id="title"
          className="form-control vibeFormInput"
          placeholder="Your title here..."
          value={vibe.title}
          onChange={(e) => setVibe({ ...vibe, title: e.target.value })}
        />
        </label>

        <label>Description:
        <input
          type="text"
          name="description"
          id="description"
          className="form-control vibeFormInput"
          value={vibe.description}
          placeholder="Your description here..."
          onChange={(e) => setVibe({ ...vibe, description: e.target.value })}
        />
         </label>

        <label>Image URL: 
        <input
          type="file"
          name="imageUrl"
          id="imageUrl"
          className="form-control vibeFormInput"
        onChange={handleFileChange}
        />
        </label>

        <label>Song URL: 
        <input
          type="file"
          name="songUrl"
          id="songUrl"
          className="form-control vibeFormInput"
        onChange={handleSongFileChange}
        />
        </label>

        <label>Mood: 
        <select
          name="mood"
          id="mood"
          value={vibe.mood.moodId}
          className="form-control vibeFormInput"
          onChange={(e) =>
            setVibe({ ...vibe, mood: { moodId: e.target.value } })
          }
        >
          <option value={0}>Select a mood</option>
          <option value={1}>Happy</option>
          <option value={2}>Sad</option>
          <option value={3}>Angry</option>
          <option value={4}>Relaxed</option>
          <option value={5}>Excited</option>
        </select>
        </label>

<div className="vibeFormButtonsContainer">
        <button type="submit" className="vibeFormButton mt-3">Create</button>
        <button type="button" className="vibeFormButton mt-3" onClick={() => navigate("/vibes")}>Cancel</button>

</div>
      </form>
    </div>
  );
};

export default CreateVibeForm;
