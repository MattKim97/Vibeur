import React from 'react'
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import AWS from "aws-sdk";
import anonymous from '../images/anonymous.png';
import { useEffect } from 'react';
import { useRef } from 'react';
import { gsap } from 'gsap';



const EditProfilePic = ({ loggedUser, setLoggedUser }) => {


  const formRef = useRef(null);


  const S3_BUCKET_IMAGE = import.meta.env.VITE_S3_BUCKET_IMAGE;
	const REGION = import.meta.env.VITE_REGION;
	const S3_KEY = import.meta.env.VITE_S3_KEY;
	const S3_SECRET = import.meta.env.VITE_S3_SECRET;

  useEffect(() => {
    gsap.fromTo(formRef.current, 
      { opacity: 0 }, 
      { opacity: 1, duration: 1.5, ease: "power2.inOut" } 
    );
}, []);
  
	const getFileUrl = (fileName) => {
		return `https://${S3_BUCKET_IMAGE}.s3.${REGION}.amazonaws.com/${fileName}`;
	  };

      const [file, setFile] = useState(null);
      const [errors, setErrors] = useState([]);
      const navigate = useNavigate();

          const uploadFile = async () => {
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
              await s3.putObject(params)
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
            setFile(file);
            };

  const handleEdit = async (e) => {
    e.preventDefault();

    setErrors([]);

		let imageUrl = '';

    if (file) {
			imageUrl = await uploadFile(); 
		}

    console.log(imageUrl)

    const updatedUser = {
			...loggedUser,
			userImageUrl: imageUrl,
		};

    console.log(loggedUser)
    console.log(updatedUser)

    fetch(`http://localhost:8080/api/user/${loggedUser.userId}`, {
      method: "PUT",
      headers: {
          "Content-Type": "application/json",
          Authorization: updatedUser.jwt,
      },
      body: JSON.stringify(updatedUser)
  })
  .then(response => {
      if (response.status === 204) {
          setLoggedUser(updatedUser); 
          localStorage.setItem("loggedInUser", JSON.stringify(updatedUser));
          navigate("/");
      }
  });
  }

  const handleCancel = (e) => {
    e.preventDefault();
    navigate("/");
  }
        
	  

  return (
    <div className='mt-5 container d-flex flex-column align-items-center justify-content-center'>
      <div className="edit_profile_border bg-light" ref={formRef}>
      <div className="d-flex flex-column align-items-center">
      {errors.length > 0 && <ul id="errors" className='error'>
                            {errors.map(error => <li className="me-4" key={error}>{error}</li>)}
                        </ul>}
      <h1>Edit your profile picture!</h1>
      <h4>Current profile picture: </h4>
      <img src={loggedUser != null && loggedUser.userImageUrl != "" ? loggedUser.userImageUrl : anonymous} className='edit_profile_image' alt="profile pic" />

      <h4 className='mt-2'>Upload a New Profile Picture: </h4>
                        <input
                            className="file-upload-label mt-1"
                            type="file"
                            onChange={handleFileChange}
                            />
                            <div className="d-flex flex-row justify-content-center">
                            <button className="button_edit_profile" onClick={handleEdit}>Confirm</button>  
                            <button className="button_edit_profile" onClick={handleCancel}>Cancel</button> 
                            </div>                                   
      </div>
    </div>
    </div>
  )
}

export default EditProfilePic
