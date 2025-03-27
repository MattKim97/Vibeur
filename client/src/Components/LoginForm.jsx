import AWS from 'aws-sdk';
import React, { useState, useEffect, useRef } from 'react'
import '../LoginForm.css'
import { gsap } from 'gsap';
import { ScrollTrigger } from 'gsap/ScrollTrigger';
import { Buffer } from 'buffer';
import process from 'process';
import {jwtDecode } from "jwt-decode"
window.Buffer = Buffer;
window.process = process;
import { useNavigate } from 'react-router-dom';


gsap.registerPlugin(ScrollTrigger);


const LoginForm = ({setLoggedUser}) => {

    const formRef = useRef(null);
	const navigate = useNavigate()


	const S3_BUCKET_IMAGE = import.meta.env.VITE_S3_BUCKET_IMAGE;
	const REGION = import.meta.env.VITE_REGION;
	const S3_KEY = import.meta.env.VITE_S3_KEY;
	const S3_SECRET = import.meta.env.VITE_S3_SECRET;

	const getFileUrl = (fileName) => {
		return `https://${S3_BUCKET_IMAGE}.s3.${REGION}.amazonaws.com/${fileName}`;
	  };
	  

    useEffect(() => {
        gsap.fromTo(formRef.current, 
          { opacity: 0 }, 
          { opacity: 1, duration: 2, ease: "power2.inOut" } 
        );
    }, []);

    const [user, setUser] = useState({
        username: "",
        userImageUrl: "",
        password: ""
    });

    const [errors, setErrors] = useState([]);

    const [containerClasses, setContainerClasses] = useState('container login_container');

	const [file, setFile] = useState(null);

    const signUpClicked = () => {
		setErrors([]);
        setContainerClasses('container login_container right-panel-active');
    }

    const signInClicked = () => {
		setErrors([]);
        setContainerClasses('container login_container');
    }

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

    const handleChange = (event) => {
        const { name, value } = event.target;
        setUser((prevUser) => ({
            ...prevUser,
            [name]: value
        }));
    }


	const handleFileChange = (e) => {
		const file = e.target.files[0];
		setFile(file);
	  };

    const handleSignUp =  async (event) => {
        event.preventDefault();

		setErrors([]);

		let imageUrl = '';




		if (file) {
			imageUrl = await uploadFile(); 
		}

		const updatedUser = {
			...user,
			userImageUrl: imageUrl,
		};

        fetch("http://localhost:8080/api/user", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(updatedUser)
        })
        .then(response => {
            if (response.status >= 200 && response.status < 300) {
                response.json().then((fetchedUser) => {
					const jwtUser = jwtDecode(fetchedUser.jwt)
                    jwtUser.jwt = fetchedUser.jwt
                    setLoggedUser(jwtUser);
                    localStorage.setItem("loggedInUser",JSON.stringify(jwtUser))
                    navigate("/")
                });
            } else {
                response.json().then(fetchedErrors => setErrors(fetchedErrors));
            }
        });
    }

	const handleLogIn = (event) => {
        event.preventDefault()

        fetch("http://localhost:8080/api/user/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(user)
        })
        .then(response => {
            if (response.status >= 200 && response.status < 300) {
                response.json().then((fetchedUser) => {

                    const jwtUser = jwtDecode(fetchedUser.jwt)
                    jwtUser.jwt = fetchedUser.jwt
                    setLoggedUser(jwtUser);
                    localStorage.setItem("loggedInUser",JSON.stringify(jwtUser))
                    navigate("/")
                })
            } else {
                response.json().then(fetchedErrors => setErrors(fetchedErrors))
            }
        })

    }

    return (
        <div className='mt-5'>
            <div className={containerClasses} id="container" ref={formRef}>
                <div className="form-container sign-up-container">
                    <form className="login_form" onSubmit={handleSignUp}>
					{errors.length > 0 && <ul id="errors" className='error'>
                            {errors.map(error => <li className="me-4" key={error}>{error}</li>)}
                        </ul>}
                        <h1>Create Account</h1>
                        <input
                            className="login_input"
                            type="text"
                            placeholder="Name"
                            value={user.username}
                            name="username"
                            onChange={handleChange}
                        />
                        <input
                            className="login_input"
                            type="password"
                            placeholder="Password"
                            value={user.password}
                            name="password"
                            onChange={handleChange}
                        />
                        <p className='login_p'>Upload a Profile Picture (Optional): </p>
                        <input
                            className="file-upload-label"
                            type="file"
                            onChange={handleFileChange}
                        />
                        <button className='login_button mt-3 mb-2' type='submit'>Sign Up</button>
                    </form>
                </div>
                <div className="form-container sign-in-container">
		
                    <form className="login_form" onSubmit={handleLogIn}>
					{errors.length > 0 && <ul id="errors" className='error'>
                            {errors.map(error => <li className="me-4" key={error}>{error}</li>)}
                        </ul>}
                        <h1 className='login_h1'>Sign in</h1>
						<input
                            className="login_input"
                            type="text"
                            placeholder="Name"
                            value={user.username}
                            name="username"
                            onChange={handleChange}
                        />
                        <input
                            className="login_input"
                            type="password"
                            placeholder="Password"
                            value={user.password}
                            name="password"
                            onChange={handleChange}
                        />
                        <button className='login_button_blue mt-2' type='submit'>Sign In</button>
                    </form>
                </div>
                <div className="overlay-container">
                    <div className="overlay">
                        <div className="overlay-panel overlay-left">
                            <h1 className='login_h1'>Already have an Account?</h1>
                            <p className='login_p'>Already a part of the Vibeur family?</p>
                            <button className="ghost login_button" id="signIn" onClick={signInClicked}>Log In</button>
                        </div>
                        <div className="overlay-panel overlay-right">
                            <h1 className='login_h1'>New to Vibeur?</h1>
                            <p className='login_p'>Sign up to Vibeur here to start sharing today!</p>
                            <button className="ghost login_button blue_button" id="signUp" onClick={signUpClicked}>Register</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default LoginForm;
