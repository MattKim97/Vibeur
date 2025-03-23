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
import Vibe from './Components/Vibe';
import EditVibeForm from './Components/EditVibeForm';

function App() {


  const [loggedUser,setLoggedUser] = useState(null)
	const [loaded, setLoaded] = useState(false)
  

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
        <Route path="/vibe/:vibeId" element={<Vibe loggedUser={loggedUser}/>} />
        <Route path='/vibe/:vibeId/edit' element={<EditVibeForm loggedUser={loggedUser}/>}  />
      </Routes>
    </Router>
  )
}

export default App
