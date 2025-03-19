import { useState } from 'react'
import './App.css'
import Landing from './Components/Landing'
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import NavBar from './Components/NavBar';
import LoginForm from './Components/LoginForm';
import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import EditProfilePic from './Components/EditProfilePic';
import VibeCard from './Components/VibeCard';
import VibesList from './Components/VibesList';
import MyVibesList from './Components/MyVibesList';

function App() {


  const [loggedUser,setLoggedUser] = useState(null)
	const [loaded, setLoaded] = useState(false)
  
  console.log(loggedUser)

	useEffect(() => {
		if(localStorage.getItem("loggedInUser")){
			setLoggedUser(JSON.parse(localStorage.getItem("loggedInUser")))
		}
		setLoaded(true)
	},[])

	if(!loaded){
		return null;
	}

  return (
    <Router>
      <NavBar loggedUser={loggedUser} setLoggedUser={setLoggedUser}/>
      <Routes>
        <Route path="/" element={<Landing />} />
        <Route path="/editProfile" element={<EditProfilePic loggedUser={loggedUser} setLoggedUser={setLoggedUser}/>} />
        <Route path="/login" element={<LoginForm setLoggedUser={setLoggedUser}/>} />
        <Route path="/vibes" element={<VibesList/> } />
        <Route path="/myVibes" element={<MyVibesList loggedUser={loggedUser}/> } />
      </Routes>
    </Router>
  )
}

export default App
